package com.gaojulong.coap;

import com.gaojulong.coap.common.ContentType;
import com.gaojulong.coap.config.CoAPResourceFactory;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.OptionSet;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

/**
 * @author sulongx
 * @date 2021-10-15
 */
@SpringBootApplication
public class CoAPApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(CoAPApplication.class, args);
    }


    @Value("${coap.port:8080}")
    private int port;

    @Autowired
    private CoAPResourceFactory coAPResourceFactory;

    /**
     * 启动内部CoAO服务
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        final CoapServer server = new CoapServer();
        //默认就是ucp实现传输层
        server.addEndpoint(new CoapEndpoint(new InetSocketAddress("127.0.0.1", port)));


        CoapResource[] coapResourceList = coAPResourceFactory.getCoAPResourceListByAnnotation();

        server.add(coapResourceList);

//        server.add(new CoapResource("home"){
//            @Override
//            public void handleGET(CoapExchange exchange) {
//                System.out.println(exchange.getRequestText());
//                exchange.getRequestOptions();
//                exchange.respond(CoAP.ResponseCode.CONTENT,
//                        "{\"ok\":\"get home ok\"}",
//                        ContentType.APPLICATION_JSON);
//            }
//            @Override
//            public void handlePOST(CoapExchange exchange) { //1
//                System.out.println(""+exchange.getRequestOptions().getUriQueryString());
//                System.out.println("appKey:\t"+exchange.getQueryParameter("appKey"));
//                System.out.println("appSecret:\t"+exchange.getQueryParameter("appSecret"));
//                System.out.println("载荷内容:"+exchange.getRequestText());
//                exchange.respond("post home ok");
//            }
//        }.add(new CoapResource("dog"){
//            @Override
//            public void handleGET(CoapExchange exchange) {
//                exchange.respond("get home/dog ok");
//            }
//            @Override
//            public void handlePOST(CoapExchange exchange) {  //2
//                System.out.println(""+exchange.getRequestOptions().getUriQueryString());
//                System.out.println("appKey:\t"+exchange.getQueryParameter("appKey"));
//                System.out.println("appSecret:\t"+exchange.getQueryParameter("appSecret"));
//                System.out.println(exchange.getRequestText().length());
//                exchange.respond("post home/dog ok");
//            }
//        }));
        //启动服务
        server.start();
        //在进程退出时关闭服务
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                server.stop();
            }catch (Exception e){
                e.printStackTrace();
            }
        }));
    }
}
