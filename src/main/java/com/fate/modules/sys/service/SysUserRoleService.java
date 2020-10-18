package com.fate.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fate.modules.sys.entity.SysUserRole;

import java.util.List;

/**
 * @Author oyg
 * @Date 2018/8/5/21:25
 */
public interface SysUserRoleService extends IService<SysUserRole> {
    List<Long> queryRoleIdList(Long userId);

    int deleteBatch(Long[] roleIds);

    void saveOrUpdate(Long userId, List<Long> roleIdList);

    public void testExceptionIn();
}
