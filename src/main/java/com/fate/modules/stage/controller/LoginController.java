package com.fate.modules.stage.controller;

import com.fate.common.annotation.SysLogNotUser;
import com.fate.common.lock.RedisLock;
import com.fate.common.validator.ValidatorUtils;
import com.fate.modules.sys.controller.AbstractController;
import com.fate.modules.sys.entity.SysUser;
import com.fate.modules.sys.form.RegiserUserReq;
import com.fate.modules.sys.request.SysLoginRequest;
import com.fate.modules.sys.service.SysUserService;
import com.fate.modules.sys.service.SysUserTokenService;
import com.fate.response.AirResult;
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
@RestController
@Scope(value = "request")
public class LoginController extends AbstractController{

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private RedisLock redisLock;

    @PostMapping("/login")
    public AirResult login(@RequestBody SysLoginRequest request) throws Exception {

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

    @PostMapping("/logout")
    public AirResult logout(){
        sysUserTokenService.logout(getUserId());
        return AirResult.success("退出成功");
    }


    @SysLogNotUser("注册用户")
    @PostMapping("/regier")
    public AirResult regiser(@RequestBody RegiserUserReq userReq){
        ValidatorUtils.validateEntity(userReq);

        sysUserService.save(userReq);

        return AirResult.success();
    }


}
