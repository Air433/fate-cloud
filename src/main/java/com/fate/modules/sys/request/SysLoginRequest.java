package com.fate.modules.sys.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author oyg
 * @Date 2018/7/21/12:59
 */
@ApiModel(value="SysLoginRequest对象",description="用户登陆请求对象")
public class SysLoginRequest {
    @ApiModelProperty(value="用户名",name="username")
    private String username;
    @ApiModelProperty(value="密码",name="password")
    private String password;
//    @ApiModelProperty(value="用户名",name="captcha")
//    private String captcha;
//    @ApiModelProperty(value="用户名",name="uuid")
//    private String uuid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getCaptcha() {
//        return captcha;
//    }
//
//    public void setCaptcha(String captcha) {
//        this.captcha = captcha;
//    }
//
//    public String getUuid() {
//        return uuid;
//    }
//
//    public void setUuid(String uuid) {
//        this.uuid = uuid;
//    }
}
