package com.ruoyi;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import org.junit.Test;

import java.util.List;

/**
 * 用户测试
 *
 * @author : dy
 * @since : 2022-04-11 10:14
 */
public class SysUserControllerTest extends BaseUnitTest{
    /**
     *  获取用户信息
     *
     **/
    @Test
    public void getUserInfo() {

        LoginUser loginUser = SecurityUtils.getLoginUser();
        System.out.println(loginUser);

    }
}
