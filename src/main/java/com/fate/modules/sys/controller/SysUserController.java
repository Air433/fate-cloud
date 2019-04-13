package com.fate.modules.sys.controller;

import com.fate.common.annotation.SysLogAn;
import com.fate.common.utils.Constant;
import com.fate.common.utils.PageUtils;
import com.fate.common.validator.Assert;
import com.fate.common.validator.ValidatorUtils;
import com.fate.common.validator.group.UpdateGroup;
import com.fate.modules.sys.entity.SysUser;
import com.fate.modules.sys.form.PasswordForm;
import com.fate.modules.sys.service.SysUserRoleService;
import com.fate.modules.sys.service.SysUserService;
import com.fate.response.AirResult;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author oyg
 * @Date 2018/7/29/21:17
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController{

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public AirResult list(@RequestParam Map<String, Object> params){
        //只有超级管理员可以查询所有管理员列表
        if (getUserId() != Constant.SUPER_ADMIN){
            params.put("createdUserId", getUser());
        }
        PageUtils page = sysUserService.queryPage(params);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        return AirResult.success(map);
    }

    @GetMapping("/info")
    public AirResult info(){
        Map<String,Object> map = new HashMap<>();
        map.put("user", getUser());
        return AirResult.success(map);
    }

    @SysLogAn("修改密码")
    @PutMapping("/password")
    public AirResult password(@RequestBody PasswordForm form){

        Assert.isBlank(form.getNewPassword(), "新密码不能为空");

        String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();

        String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag){
            return AirResult.error("原密码不正确");
        }
        return AirResult.success();
    }

    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public AirResult info(@PathVariable("userId") Long userId){
        SysUser user = sysUserService.selectById(userId);

        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        return AirResult.success(map);
    }

    @SysLogAn("修改用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    public AirResult save(@RequestBody SysUser sysUser){
        ValidatorUtils.validateEntity(sysUser, UpdateGroup.class);

        sysUser.setCreateUserId(getUserId());
        sysUserService.update(sysUser);
        return AirResult.success();
    }

    /**
     * 删除用户
     * @param userIds
     * @return
     */
    @SysLogAn("删除用户")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public AirResult delete(@RequestParam Long[] userIds){
        if (ArrayUtils.contains(userIds, 1L)){
            return AirResult.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())){
            return AirResult.error("当前用户不能删除");
        }

        sysUserService.deleteBatchIds(Arrays.asList(userIds));

        return AirResult.success();
    }
}
