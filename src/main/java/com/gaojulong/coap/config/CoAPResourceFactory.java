package com.gaojulong.coap.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gaojulong.coap.annotation.CoAPGetMapping;
import com.gaojulong.coap.annotation.CoAPPostMapping;
import com.gaojulong.coap.annotation.CoAPRequestMapping;
import com.gaojulong.coap.common.CoAPException;
import com.gaojulong.coap.common.CoAPRequestType;
import com.gaojulong.coap.model.HttpResultModel;
import jdk.nashorn.internal.parser.JSONParser;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.network.Exchange;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 描述:
 * CoAP协议配置
 *
 * @author xiongsulong
 * @create 2021-10-16 00:20
 */
@Component
public class CoAPResourceFactory {


    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 根据注解获取所有的CoapResource集合
     * @return
     */
    public CoapResource[] getCoAPResourceListByAnnotation(){
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(CoAPRequestMapping.class);


        //所有可执行节点
        Map<String, CoapResource> executeNodeAll = new HashMap<>();
        //所有头节点
        Map<String, CoapResource> headNodeAll = new HashMap<>();

        for (Object bean : beans.values()){
            String classUri = "";

            CoAPRequestMapping requestMapping = bean.getClass().getAnnotation(CoAPRequestMapping.class);

            classUri = classUri + requestMapping.value().replaceAll(" ", "");

            Method[] methods = bean.getClass().getMethods();

            for(Method method : methods){
                String methodUri = null;
                List<CoAPRequestType> requestTypeList = new ArrayList<>();

                Annotation[] methodAnnotations = method.getAnnotations();

                for (Annotation methodAnnotation : methodAnnotations){

                    if(methodAnnotation instanceof CoAPRequestMapping){
                        methodUri = ((CoAPRequestMapping) methodAnnotation).value();
                        CoAPRequestType[] methodTypes = ((CoAPRequestMapping) methodAnnotation).method();
                        if(methodTypes.length == 0){
                            throw new RuntimeException(bean.getClass().getName() + "." + method.getName() + " CoAPRequestMapping CoAPRequestType cannot be null");
                        }
                        requestTypeList.addAll(Arrays.asList(methodTypes));
                        break;
                    }else if(methodAnnotation.annotationType().getDeclaredAnnotation(CoAPRequestMapping.class) != null) {
                        CoAPRequestType type = methodAnnotation.annotationType().getDeclaredAnnotation(CoAPRequestMapping.class).method()[0];
                        methodUri = getMappingName(methodAnnotation, type);
                        requestTypeList.add(type);
                        break;
                    }
                }
                //生成可执行节点
                if(StringUtils.hasText(methodUri) && requestTypeList.size() > 0){
                    String uri = classUri + methodUri.replaceAll(" ", "");
                    //可执行节点重复
                    if(executeNodeAll.containsKey(uri)){
                        throw new RuntimeException(uri + " handler has exist");
                    }
                    String[] split = uri.split("/");
                    CoapResource coapResource = getCoapResource(split[split.length - 1], method, bean, requestTypeList);
                    executeNodeAll.put(uri, coapResource);
                }
            }

            //初始化头节点
            executeNodeAll.entrySet().forEach(node -> {
                String[] nodeNames = node.getKey().split("/");
                //获取头节点
                CoapResource parentNode = null;

                for (int i = 0; i < nodeNames.length; i++) {
                    //头结点
                    if(parentNode == null){
                        parentNode = headNodeAll.get(nodeNames[i]);
                        if(parentNode == null){
                            parentNode = new CoapResource(nodeNames[i]);
                            headNodeAll.put(nodeNames[i], parentNode);
                        }
                    }else {
                        //当前节点是否是上一个的子节点
                        Resource child = parentNode.getChild(nodeNames[i]);
                        if(child == null){
                            child = new CoapResource(nodeNames[i]);
                            parentNode.add(child);
                        }

                        if(i < nodeNames.length - 1){
                            //不是最后一个子节点,更新当前父节点
                            parentNode = (CoapResource) child;
                        }
                    }
                    //最后一个节点存入可执行节点
                    if(i == nodeNames.length - 1){
                        parentNode.add(executeNodeAll.get(node.getKey()));
                    }
                }
            });

            return headNodeAll.values().toArray(new CoapResource[0]);
        }
        return null;
    }




    private CoapResource getCoapResource(String name, Method method, Object target, List<CoAPRequestType> types){
        return new CoapResource(name){
            @Override
            public void handleGET(CoapExchange exchange) {
                if(!types.contains(CoAPRequestType.GET)){
                    super.handleGET(exchange);
                    return;
                }
                handler(exchange);
            }

            @Override
            public void handlePOST(CoapExchange exchange) {
                if(!types.contains(CoAPRequestType.POST)){
                    super.handlePOST(exchange);
                    return;
                }
                handler(exchange);
            }

            @Override
            public void handlePUT(CoapExchange exchange) {
                if(!types.contains(CoAPRequestType.PUT)){
                    super.handlePUT(exchange);
                    return;
                }
                handler(exchange);
            }

            @Override
            public void handleDELETE(CoapExchange exchange) {
                if(!types.contains(CoAPRequestType.DEL)){
                    super.handleDELETE(exchange);
                    return;
                }
                handler(exchange);
            }

            private void handler(CoapExchange exchange){
                try {
                    HttpResultModel resultModel = (HttpResultModel) method.invoke(target, exchange);
                    exchange.respond(CoAP.ResponseCode.CONTENT, JSONObject.toJSONString(resultModel));

                } catch (Exception e) {
                    e.printStackTrace();
                    if(e instanceof CoAPException){

                    }
                    exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
                }
            }
        };
    }





    private String getMappingName(Annotation annotation, CoAPRequestType type){
        String mappingName = null;
        switch (type){
            case GET:
                if(annotation instanceof CoAPGetMapping){
                    mappingName = ((CoAPGetMapping) annotation).value();
                }
                break;
            case POST:
                if(annotation instanceof CoAPPostMapping){
                    mappingName = ((CoAPPostMapping) annotation).value();
                }
                break;
            default:
                break;
        }
        return mappingName;
    }


}
