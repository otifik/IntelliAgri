package com.jit.authority;



import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import org.springframework.scheduling.annotation.EnableScheduling;


import java.io.File;

@SpringBootApplication
@Slf4j
//@Log4j2
@MapperScan("com.jit.authority.mapper")
@EnableScheduling
@EnableDiscoveryClient
@EnableZuulProxy
@EnableFeignClients
public class AuthorityApplication {
//
//    @Value("${https.port}")
//    private Integer port;
//
//    @Value("${https.ssl.key-store-password}")
//    private String key_store_password;
//
//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//        tomcat.addAdditionalTomcatConnectors(createSslConnector()); // 添加http
//        return tomcat;
//    }
// /* -------------------请按照自己spring boot版本选择 end---------------------- */
//
//
//    // 配置https
//    private Connector createSslConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//           // InputStream stream = getClass().getClassLoader().getResourceAsStream("www.njzhny.com.jks");
//            File file1 = new File("www.njzhny.com.jks");
//            connector.setScheme("https");
//            connector.setSecure(true);
//            connector.setPort(port);
//            protocol.setSSLEnabled(true);
//            protocol.setKeystoreFile(file1.getAbsolutePath());
//            protocol.setKeystorePass(key_store_password);
//            return connector;
//
//    }
    @Value("${http.port}")
    private Integer port;
//这是spring boot 2.0.X版本的 添加这个，上一个就不用添加了
    @Bean
    public ServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    tomcat.addAdditionalTomcatConnectors(createStandardConnector()); // 添加http
    return tomcat;
}
//配置http
    private Connector createStandardConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(port);
        return connector;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthorityApplication.class, args);
        log.info("authority application is running.........");

    }
}
