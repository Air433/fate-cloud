package com.fate.modules.sys.dao;

import com.fate.modules.sys.entity.SysUserToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统用户Token Mapper 接口
 * </p>
 *
 * @author oyg
 * @since 2018-07-15
 */
public interface SysUserTokenMapper extends BaseMapper<SysUserToken> {

    SysUserToken queryByToken(String token);
}
