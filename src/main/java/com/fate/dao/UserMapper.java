package com.fate.dao;

import com.fate.domain.bizs.UserBiz;
import com.fate.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author oyg
 * @since 2018-07-01
 */
public interface UserMapper extends BaseMapper<User> {
    UserBiz selectUserBiz(Long userId);
}
