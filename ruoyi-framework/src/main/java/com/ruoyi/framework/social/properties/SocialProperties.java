package com.ruoyi.framework.social.properties;

/**
 * @project_name: RuoYi-Vue-master
 * @description: 登录配置类
 * @create_name: kikock
 * @create_date: 2023-12-04 16:58
 **/
import com.google.common.collect.Maps;
import lombok.Data;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthDefaultSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "social")
public class SocialProperties{
    private Boolean enabled = false;
    private String domain;
    private Map<AuthDefaultSource, AuthConfig> oauth = Maps.newHashMap();
    private Map<String, String> alias = Maps.newHashMap();
}
