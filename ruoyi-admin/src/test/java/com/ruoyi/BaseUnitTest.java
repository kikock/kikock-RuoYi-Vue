package com.ruoyi;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ThreadLocalHolder;
import com.ruoyi.framework.web.service.SysLoginService;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 单元测试基类
 *
 * @author : dy
 * @since : 2022-04-11 09:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = RuoYiApplication.class)
public class BaseUnitTest{
    /**
     * 模拟用户名
     */
    @Value("${mock.userName}")
    private String userName;
    /**
     * 模拟用户密码
     */
    @Value("${mock.passWord}")
    private String passWord;

    @Autowired
    private SysLoginService loginService;

    @BeforeClass
    public static void setup(){
        ThreadLocalHolder.begin();
        System.out.println("开始进入单元测试.......");
    }

    @Before
    public void mock(){
        System.out.println(StringUtils.format("模拟用户名:{},模拟用户密码:{}", userName, passWord));
        String s = loginService.mockUser(userName, passWord);
        System.out.println(StringUtils.format("模拟用户登录结果:{}", s));
    }

    @AfterClass
    public static void cleanup(){
        ThreadLocalHolder.begin();
        // 释放
        System.out.println("单元测试资源释放.......");
    }
}
