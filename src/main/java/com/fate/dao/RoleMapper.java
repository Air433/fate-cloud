package com.fate.dao;

import com.fate.domain.bizs.RoleBiz;
import com.fate.entity.Role;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author oyg
 * @since 2018-07-01
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<RoleBiz> selectByUserId(String userId);


}
