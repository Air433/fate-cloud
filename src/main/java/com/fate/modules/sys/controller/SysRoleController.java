package com.fate.modules.sys.controller;

import com.fate.common.annotation.SysLogAn;
import com.fate.common.utils.Constant;
import com.fate.common.utils.PageUtils;
import com.fate.common.validator.ValidatorUtils;
import com.fate.modules.sys.entity.SysRole;
import com.fate.modules.sys.service.SysRoleMenuService;
import com.fate.modules.sys.service.SysRoleService;
import com.fate.response.AirResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author oyg
 * @Date 2018/8/18/18:16
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController{

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    public AirResult list(@RequestParam Map<String, Object> params){
        //如果不是超级管理员，则只查询自己创建的角色列表
        if (getUserId() != Constant.SUPER_ADMIN){
            params.put("createUserId", getUserId());
        }

        PageUtils page = sysRoleService.queryPage(params);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        return AirResult.success(map);

    }

    /**
     * 角色列表
     * @return
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:role:select")
    public AirResult select(){
        Map<String, Object> map = new HashMap<>();

        if (getUserId() != Constant.SUPER_ADMIN){
            map.put("createUserId", getUserId());
        }
        List<SysRole> list = (List<SysRole>) sysRoleService.listByMap(map);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        return AirResult.success(result);
    }

    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public AirResult info(@PathVariable("roleId") Long roleId){
        SysRole role = sysRoleService.getById(roleId);

        SysRole role2 = sysRoleService.getById(roleId);
        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        Map<String, Object> result = new HashMap<>();
        result.put("role", role);
        return AirResult.success(result);
    }

    @SysLogAn("保存角色")
    @PostMapping("/save")
    @RequiresPermissions("sys:role:save")
    public AirResult save(@RequestBody SysRole role){
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        sysRoleService.save(role);

        return AirResult.success();
    }

    @SysLogAn("删除角色")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public AirResult delete(@RequestBody Long[] roleIds){
        sysRoleService.deleteBatch(roleIds);

        return AirResult.success();
    }

    @PostMapping("/test")
    public void li(@RequestParam String username, String password){

        System.err.println(username);
    }
}
