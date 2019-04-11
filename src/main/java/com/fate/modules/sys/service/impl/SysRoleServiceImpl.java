package com.fate.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fate.common.exception.RRException;
import com.fate.common.utils.Constant;
import com.fate.common.utils.PageUtils;
import com.fate.common.utils.Query;
import com.fate.modules.sys.dao.SysRoleMapper;
import com.fate.modules.sys.entity.SysRole;
import com.fate.modules.sys.service.SysRoleMenuService;
import com.fate.modules.sys.service.SysRoleService;
import com.fate.modules.sys.service.SysUserRoleService;
import com.fate.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author oyg
 * @Date 2018/8/18/18:20
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String)params.get("roleName");
        Long createUserId = (Long) params.get("createUserId");

        Page<SysRole> page =  this.selectPage(
                new Query<SysRole>(params).getPage(),
                new EntityWrapper<SysRole>()
                .like(StringUtils.isNotBlank(roleName), "role_name", roleName)
                .eq(createUserId !=null, "create_user_id", createUserId)
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRole role) {
        role.setCreateTime(new Date());
        this.insert(role);

        //检查权限是否越权
        checkPerms(role);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.deleteBatchIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
    }

    private void checkPerms(SysRole role) {
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (role.getCreateUserId() == Constant.SUPER_ADMIN){
            return;
        }

        //查询用户所拥有的菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());

        //判断是否越权
        if (!menuIdList.containsAll(role.getMenuIdList())){
            throw new RRException("新增角色的权限，已超出你的权限范围");
        }
    }
}
