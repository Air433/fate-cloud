package com.fate.dao;

import com.fate.entity.RolePermission;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * 角色按钮权限表 Mapper 接口
 * </p>
 *
 * @author oyg
 * @since 2018-07-01
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {


  List<RolePermission> findByRoleId(Long role_id);
}
