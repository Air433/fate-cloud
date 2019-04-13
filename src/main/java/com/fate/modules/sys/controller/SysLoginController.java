package com.fate.modules.sys.controller;

import com.fate.common.annotation.SysLogNotUser;
import com.fate.common.lock.RedisLock;
import com.fate.common.validator.ValidatorUtils;
import com.fate.modules.sys.entity.SysUser;
import com.fate.modules.sys.form.RegiserUserReq;
import com.fate.modules.sys.request.SysLoginRequest;
import com.fate.modules.sys.service.SysUserService;
import com.fate.modules.sys.service.SysUserTokenService;
import com.fate.response.AirResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author oyg
 * @Date 2018/7/21/12:52
 */
@Api(value="SysLoginController",tags={"后台用户操作接口"})
@RestController
@Scope(value = "request")
public class SysLoginController extends AbstractController{

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private RedisLock redisLock;

    @ApiOperation(value="登录", notes="")
    @PostMapping("/sys/login")
    public AirResult login(@RequestBody SysLoginRequest request){
        if (request == null){
            return AirResult.error("请求信息为空，请检查");
        }

        if (request.getUsername() == null || request.getUsername().trim().equals("")){
            return AirResult.error("用户名不能为空");
        }
        SysUser user = sysUserService.queryByUserName(request.getUsername());

        if (user == null || !user.getPassword().equals(new Sha256Hash(request.getPassword(), user.getSalt()).toHex())){
            return AirResult.error("账号或密码不正确");
        }

        if (user.getStatus() == 0){
            return AirResult.error("账号已被锁定，请联系管理员");
        }

        AirResult result = sysUserTokenService.createToekn(user.getUserId());

        return result;
    }

    @PostMapping("/sys/logout")
    public AirResult logout(){
        sysUserTokenService.logout(getUserId());
        return AirResult.success("退出成功");
    }


    @SysLogNotUser("注册用户")
    @PostMapping("/sys/regier")
    public AirResult regiser(@RequestBody RegiserUserReq userReq){
        ValidatorUtils.validateEntity(userReq);

        sysUserService.save(userReq);

        return AirResult.success();
    }

    @GetMapping("/test-redis")
    public AirResult testRedis(){
        System.err.println(redisLock);
        redisLock.setLockKey("test-redis");
        redisLock.setLockRequestId(UUID.randomUUID().toString()+Thread.currentThread().getId());
        redisLock.lock();
        redisLock.sleepBySecond(60);
        redisLock.unlock();
        return null;
    }
}
