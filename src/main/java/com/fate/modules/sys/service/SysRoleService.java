package com.fate.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fate.common.utils.PageUtils;
import com.fate.modules.sys.entity.SysRole;

import java.util.Map;

/**
 * @Author oyg
 * @Date 2018/8/18/18:18
 */
public interface SysRoleService extends IService<SysRole> {
    PageUtils queryPage(Map<String, Object> params);

    void add(SysRole role);

    void deleteBatch(Long[] roleIds);
}
