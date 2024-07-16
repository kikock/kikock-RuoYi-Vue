package com.ruoyi.web.core.config;

import com.ruoyi.common.config.RuoYiConfig;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                        .contact(new Contact().name("kikock"))
                );
    }
}
