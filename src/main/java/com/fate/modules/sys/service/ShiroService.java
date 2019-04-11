package com.fate.modules.sys.service;

import com.fate.modules.sys.entity.SysUser;
import com.fate.modules.sys.entity.SysUserToken;

import java.util.Set;

public interface ShiroService {

    Set<String> getUserPermissions(long userId);

    SysUserToken queryByToken(String token);

    SysUser queryUser(Long userId);
}
