package com.fate.controller;

import com.fate.response.AirResult;
import javax.naming.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ouyanggang on 2018/5/22.
 */
@RestController
public abstract class BaseController {

  @ExceptionHandler({UnauthenticatedException.class, AuthenticationException.class})
  public AirResult authenticationException(){
    AirResult result = new AirResult();
    result.setStatus(-999);
    result.setMsg("未登录");
    return result;
  }

  @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
  public AirResult authorizationException(){
    AirResult result = new AirResult();
    result.setStatus(-998);
    result.setMsg("无权限");
    return result;
  }
}
