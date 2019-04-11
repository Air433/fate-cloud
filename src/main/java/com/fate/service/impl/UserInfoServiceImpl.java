package com.fate.service.impl;

//import com.fate.entity.SysPermission;
import com.fate.domain.bizs.RoleBiz;
import com.fate.domain.bizs.UserBiz;
import com.fate.entity.Authority;
import com.fate.entity.Role;
import com.fate.entity.UserInfo;
import com.fate.service.IUserInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Air on 2018/5/13.
 */
@Transactional
@Service
public class UserInfoServiceImpl implements IUserInfoService {
    public UserInfo findByUsername(String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("air");
        userInfo.setPassword("2093ff5efdd13a3156944657d8b4c131");
        List<Role> roleList = new ArrayList<Role>();
        Role role  = new Role();
        role.setRoleKey("admin");

//        sysRole.setRole("admin");
//
//        List<SysPermission> permissions = new ArrayList<SysPermission>();
//        SysPermission sysPermission = new SysPermission();
//        sysPermission.setPermission("user:edit");
//        permissions.add(sysPermission);
//        sysRole.setPermissions(permissions);

        roleList.add(role);
        userInfo.setRoleList(roleList);
        return userInfo;
    }
    public UserBiz findByUsername(){
        UserBiz userBiz = new UserBiz();
        userBiz.setUserName("air");
        userBiz.setPassword("2093ff5efdd13a3156944657d8b4c131");
        List<Role> roleList = new ArrayList<Role>();
        Role role  = new Role();
        role.setRoleKey("admin");
        RoleBiz roleBiz = new RoleBiz();
        roleBiz.setRoleKey("admin");
        Authority authority = new Authority();
        //authority.setMenuCode();
        return userBiz;
    }
}
