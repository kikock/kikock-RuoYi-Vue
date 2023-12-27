package com.ruoyi.common.core.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户对象 SysUserSimpleVo
 *
 * @author ck
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserSimpleVo implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String name;

}
