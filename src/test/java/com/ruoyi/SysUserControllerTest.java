package com.ruoyi;

import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysUserService;
import com.ruoyi.project.weixin.utils.JsonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 用户测试
 *
 * @author : dy
 * @since : 2022-04-11 10:14
 */
public class SysUserControllerTest extends BaseUnitTest{
    @Autowired
    private ISysUserService userService;

    @Test
    public void selectUserList() {
        SysUser user = new SysUser();
        List<SysUser> sysUsers = userService.selectUserList(user);
        System.out.println("list===="+ JsonUtils.toJson(sysUsers));
    }
}
