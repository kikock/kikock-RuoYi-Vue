package com.ruoyi.web.core.config;

import com.ruoyi.common.config.RuoYiConfig;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Data;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Swagger2的接口配置
 *
 * @author ruoyi
 */
@Configuration
public class Knife4jConfig{
    /**
     * 系统基础配置
     */
    @Autowired
    private RuoYiConfig ruoyiConfig;


    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        // 设置标题
                        .title("Knife4j - 接口文档")
                        // 描述
                        .description("项目简介，支持Markdown格式：`这里是代码标签哦`，**这里是强调哦**")
                        //版本
                        .version("版本号:" + ruoyiConfig.getVersion())
                        .contact(new Contact().name("曹奎"))
                );
    }
}
