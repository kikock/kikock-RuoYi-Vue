package com.ruoyi.framework.config;

import com.ruoyi.framework.config.properties.PermitAllUrlProperties;
import com.ruoyi.framework.security.filter.JwtAuthenticationTokenFilter;
import com.ruoyi.framework.security.handle.AuthenticationEntryPointImpl;
import com.ruoyi.framework.security.handle.LogoutSuccessHandlerImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.ArrayList;
import java.util.List;


/**
 * spring security配置
 *
 * @author ruoyi
 */
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@Configuration
public class SecurityConfig{
    /**
     * 自定义用户认证逻辑
     */
    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 认证失败处理类
     */
    @Resource
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理类
     */
    @Resource
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Resource
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 允许匿名访问的地址
     */
    @Resource
    private PermitAllUrlProperties permitAllUrl;
    /**
     * 跨域过滤器
     */
    @Resource
    private CorsFilter corsFilter;

    @Resource
    private HandlerMappingIntrospector introspector;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                // CSRF禁用，因为不使用session
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用HTTP响应标头
                .headers(conf -> conf.cacheControl(HeadersConfigurer.CacheControlConfig::disable))
                // 认证失败处理类
                .exceptionHandling(conf -> conf.authenticationEntryPoint(unauthorizedHandler))
                // 基于token，所以不需要session
                .sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 过滤请求
                .authorizeRequests(conf ->
                        // 对于登录login 注册register 验证码captchaImage 允许匿名访问
                {
                    try {

                        conf.requestMatchers(createMvcReqMatcher()).permitAll()
                                .requestMatchers(createAntPathReqMatcher()).permitAll()
                                .anyRequest().authenticated()
                                .and().headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        // 添加Logout filter
        httpSecurity.logout(conf -> conf.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler));
        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);

        return httpSecurity.build();
    }

    /**
     * mvc请求，即controller中的请求
     *
     * @return mvc请求（发送到controller的请求）
     */
    private MvcRequestMatcher[] createMvcReqMatcher() {
        List<MvcRequestMatcher> mvcRequestMatchers = new ArrayList<>();

        // 对于登录login 注册register 验证码captchaImage 允许匿名访问
        mvcRequestMatchers.add(new MvcRequestMatcher(introspector, "/login"));
        mvcRequestMatchers.add(new MvcRequestMatcher(introspector, "/register"));
        mvcRequestMatchers.add(new MvcRequestMatcher(introspector, "/captchaImage"));

        // 注解标记允许匿名访问的url
        for (String url : permitAllUrl.getUrls()) {
            mvcRequestMatchers.add(new MvcRequestMatcher(introspector, url));
        }

        return mvcRequestMatchers.toArray(MvcRequestMatcher[]::new);
    }

    /**
     * 可匿名访问的静态资源 非Spring MVC端点请求， 需要特别指明，否则springboot无法启动
     *
     * @return 非Spring MVC端点请求集合
     */
    private AntPathRequestMatcher[] createAntPathReqMatcher() {
        return new AntPathRequestMatcher[]{
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/"),
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/*.html"),
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/**/*.html"),
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/**/*.css"),
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/**/*.js"),
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/profile/**"),
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/minio/download/**"),
                AntPathRequestMatcher.antMatcher("/swagger-ui.html"),
                AntPathRequestMatcher.antMatcher("/swagger-resources/**"),
                AntPathRequestMatcher.antMatcher("/webjars/**"),
                AntPathRequestMatcher.antMatcher("/*/api-docs"),
                AntPathRequestMatcher.antMatcher("/v3/api-docs/**"),
                AntPathRequestMatcher.antMatcher("/druid/**"),
                AntPathRequestMatcher.antMatcher("/oauth/**"),
                AntPathRequestMatcher.antMatcher("/oauthBack"),
                AntPathRequestMatcher.antMatcher("/bpm/task/back"),
        };
    }
    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     */
//    @Bean
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * 将原来的ulr字符串转为AntPathRequestMatcher
     *
     * @param str url链接
     * @return AntPathRequestMatcher[]
     */
    private AntPathRequestMatcher[] toAntMatcher(String... str){
        List<AntPathRequestMatcher> list = new ArrayList<>();
        for (String item : str) {
            list.add(AntPathRequestMatcher.antMatcher(item));
        }
        return list.toArray(new AntPathRequestMatcher[0]);
    }

    /**
     * 将原来的ulr字符串转为AntPathRequestMatcher
     *
     * @param method
     * @param str    url链接
     * @return AntPathRequestMatcher[]
     */
    private AntPathRequestMatcher[] toAntMatcher(HttpMethod method, String... str){
        List<AntPathRequestMatcher> list = new ArrayList<>();
        for (String item : str) {
            list.add(AntPathRequestMatcher.antMatcher(method, item));
        }
        return list.toArray(new AntPathRequestMatcher[0]);
    }
}




