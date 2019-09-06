package com.fate.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fate.modules.sys.dao.SysUserTokenMapper;
import com.fate.modules.sys.entity.SysUserToken;
import com.fate.modules.sys.oauth2.TokenGenerator;
import com.fate.modules.sys.service.SysUserTokenService;
import com.fate.response.AirResult;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author oyg
 * @Date 2018/7/21/16:47
 */
@Service
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserToken> implements SysUserTokenService {

    //12小时过期
    private final static int EXPIRE = 3600 * 12;
    @Override
    public AirResult createToekn(long userId) {

        String token = TokenGenerator.generateValue();

        Date now = new Date();

        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        SysUserToken userToken = this.getById(userId);

        if (userToken == null){
            userToken = new SysUserToken();
            userToken.setUserId(userId);
            userToken.setToken(token);
            userToken.setUpdateTime(now);
            userToken.setExpireTime(expireTime);

            this.save(userToken);
        }else {
            userToken.setToken(token);
            userToken.setUpdateTime(now);
            userToken.setExpireTime(expireTime);

            this.updateById(userToken);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("token", token);
        res.put("expire", EXPIRE);

        return AirResult.success("登陆成功", res);
    }

    @Override
    public void logout(Long userId) {
        String token = TokenGenerator.generateValue();

        SysUserToken userToken = new SysUserToken();
        userToken.setUserId(userId);
        userToken.setToken(token);
        this.updateById(userToken);
    }
}
