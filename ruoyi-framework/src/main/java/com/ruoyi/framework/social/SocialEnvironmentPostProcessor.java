package com.ruoyi.framework.social;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by kikock
 * 管理环境后处理程序
 *
 * @email kikock@qq.com
 **/
@Log4j2
@SuppressWarnings("unchecked")
public class SocialEnvironmentPostProcessor implements EnvironmentPostProcessor{

    @SneakyThrows
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application){
        log.debug("开始加载配置参数...");
        // 临时保存配置文件
        boolean isline = false;
        Properties propertiesTmp = new Properties();
        Map<String,String> dataSourceProperty = new HashMap<>();
        MutablePropertySources propertySources = environment.getPropertySources();
        for (PropertySource<?> ps : (Iterable<PropertySource<?>>) propertySources) {
            if (ps instanceof OriginTrackedMapPropertySource) {// application.yml
                OriginTrackedMapPropertySource source = (OriginTrackedMapPropertySource) ps;
                //判断是否读取第三方数据库配置
                if (source.containsProperty("social.enabled") && (Boolean) source.getProperty("social.enabled")) {
                    isline = true;
                    source.toString();
                    JSONObject defaultSourceJSONObject = JSONObject.parseObject(JSONObject.toJSONString(source.getSource()));
                    Set<Map.Entry<String,Object>> configEntrySet = defaultSourceJSONObject.entrySet();
                    for (Map.Entry<String,Object> entry : configEntrySet) {
                        if (entry.getValue() != null && propertiesTmp.get(entry.getKey()) == null) {
                            if (entry.getValue().getClass() != String.class) {
                                String value = JSONObject.parseObject(JSONObject.toJSONString(entry.getValue())).getString("value");
                                propertiesTmp.put(entry.getKey(), value);
                            } else {
                                propertiesTmp.put(entry.getKey(), entry.getValue());
                            }
                        }
                    }
                }
                if (source.containsProperty("spring.datasource.driverClassName")) {
                    dataSourceProperty.put("driverClassName", source.getProperty("spring.datasource.driverClassName").toString());
                }
                if (source.containsProperty("spring.datasource.druid.master.url")) {
                    dataSourceProperty.put("url", source.getProperty("spring.datasource.druid.master.url").toString());
                }

                if (source.containsProperty("spring.datasource.druid.master.username")) {
                    dataSourceProperty.put("userName", source.getProperty("spring.datasource.druid.master.username").toString());
                }
                if (source.containsProperty("spring.datasource.druid.master.password")) {
                    dataSourceProperty.put("password", source.getProperty("spring.datasource.druid.master.password").toString());
                }
            }
        }
        System.out.println("读取本地参数:" + propertiesTmp.toString());
        System.out.println("读取本地数据库参数:" + JSONUtil.toJsonStr(dataSourceProperty));
        //读取数据库配置
        if (isline && dataSourceProperty.size() > 0) {
            final BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setDriverClassName(dataSourceProperty.get("driverClassName"));
            basicDataSource.setUrl(dataSourceProperty.get("url"));
            basicDataSource.setUsername(dataSourceProperty.get("userName"));
            basicDataSource.setPassword(dataSourceProperty.get("password"));
            // 获取数据库连接
            try (Connection connection = basicDataSource.getConnection()) {
                // 执行数据库操作
                // 创建 Statement 对象
                Statement statement = connection.createStatement();
                // 执行查询语句
                String sql = " select type, app_key, app_secret, code,url from sys_social_app ;";
                ResultSet rs = statement.executeQuery(sql);
                // 处理查询结果
                while (rs.next()) {
//                    social.oauth.第三方标识.client-id : xxx
//                    social.oauth.第三方标识.client-secret : xxx
//                    social.oauth.第三方标识.redirect-uri : xxx
                    String type = rs.getString("type");
                    String app_key = rs.getString("app_key");
                    String app_secret = rs.getString("app_secret");
                    String url = rs.getString("url");
                    String client_id_key = String.format("social.oauth.%s.client-id", type);
                    String client_secret_key = String.format("social.oauth.%s.client-secret", type);
                    String redirect_uri_key = String.format("social.oauth.%s.redirect-uri", type);
                    propertiesTmp.put(client_id_key, app_key);
                    propertiesTmp.put(client_secret_key, app_secret);
                    propertiesTmp.put(redirect_uri_key, url);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // 关闭连接池
                basicDataSource.close();
            }
            System.out.println("加载配置参数完成:" + propertiesTmp.toString());
            // 替换以前的配置
            PropertiesPropertySource propertySource = new   PropertiesPropertySource("environmentPostProcessor", propertiesTmp);
            environment.getPropertySources().addLast(propertySource);
        }
    }
}
