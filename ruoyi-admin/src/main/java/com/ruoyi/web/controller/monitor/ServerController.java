package com.ruoyi.web.controller.monitor;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.web.domain.Server;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/server")
@Tag(name = "服务器监控")
public class ServerController{
    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping()
    @Operation(summary = "服务器数据")
    public AjaxResult getInfo() throws Exception{
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }
}
