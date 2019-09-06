package com.fate.dao;

import com.fate.domain.bizs.AuthorityBiz;
import com.fate.entity.Authority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author oyg
 * @since 2018-07-01
 */
public interface AuthorityMapper extends BaseMapper<Authority> {

    List<AuthorityBiz> selectByRoleId(String roleKey);
}
