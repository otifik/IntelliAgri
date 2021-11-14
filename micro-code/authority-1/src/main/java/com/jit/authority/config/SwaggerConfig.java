package com.jit.authority.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private DiscoveryClientRouteLocator discoveryClientRouteLocator;

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return new SwaggerResourcesProvider() {
            @Override
            public List<SwaggerResource> get() {
                List<SwaggerResource> resources = new ArrayList<>();
                //resources.add(createResource(route.getLocation(),route.getId(), "2.0"));
                resources.add(createResource_("authority", "/v2/api-docs", "1.0"));
                resources.add(createResource_("user-center", "/user-center/v2/api-docs", "1.0"));
                resources.add(createResource_("micro-aquaculture", "/ac-service/v2/api-docs", "1.0"));
                resources.add(createResource_("micro-vehicles", "/micro-vehicles/v2/api-docs", "1.0"));
                resources.add(createResource_("micro-sms", "/micro-sms/v2/api-docs", "1.0"));
                resources.add(createResource_("micro-iot", "/micro-iot/v2/api-docs", "1.0"));
                resources.add(createResource_("order", "/order/v2/api-docs", "1.0"));
                resources.add(createResource_("product", "/product/v2/api-docs", "1.0"));
                resources.add(createResource_("user", "/user/v2/api-docs", "1.0"));

//                for (Route route : discoveryClientRouteLocator.getRoutes()) {
//                    resources.add(createResource(route.getLocation(),route.getId(), "2.0"));
//                }
                return resources;
            }
        };
    }

    private SwaggerResource createResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/" + location + "/v2/api-docs");
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

    private SwaggerResource createResource_(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<springfox.documentation.service.Parameter> pars = new ArrayList<>();
        ticketPar.name("Authorization")
                .description("登录校验")//name表示名称，description表示描述
                .modelRef(new ModelRef("string"))
                .parameterType("header").required(false)
                .build();//required表示是否必填，defaultvalue表示默认值
        pars.add(ticketPar.build());//添加完此处一定要把下边的带***的也加上否则不生效

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jit.authority.controller"))
                .paths(PathSelectors.any())
//                .paths(PathSelectors.regex("^(?!auth).*$"))
                .build()
//                .globalOperationParameters(pars)
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts())
                .apiInfo(apiInfo());
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> contextList = new ArrayList<>();
        contextList.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build());
        return contextList;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> referenceList = new ArrayList<>();
        referenceList.add(new SecurityReference("Authorization", authorizationScopes));
        return referenceList;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("用户权限管理微服务Restful APIs")
                .description("用户权限管理微服务前后端交互接口文档")
                .termsOfServiceUrl("https://122.52.112.224:8443/")
                .version("1.0")
                .build();
    }

}