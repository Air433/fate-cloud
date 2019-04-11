package com.fate.modules.sys.oauth2;

import com.google.gson.Gson;
import com.fate.common.utils.HttpContextUtils;
import com.fate.response.AirResult;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @
 */
public class OAuth2Filter extends AuthenticatingFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest)request).getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = getReuestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

            String json = new Gson().toJson(AirResult.build(HttpStatus.UNAUTHORIZED.value(), "invalid token"));

            httpResponse.getWriter().print(json);

            return false;
        }

        return executeLogin(request, response);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse servletResponse) throws Exception {
        String token = getReuestToken((HttpServletRequest) request);

        if (StringUtils.isBlank(token)){
            return null;
        }
        return new OAuth2Token(token);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        HttpServletResponse httpRespones = (HttpServletResponse) response;
        httpRespones.setContentType("application/json;charset=utf-8");
        httpRespones.setHeader("Access-Control-Allow-Credentials", "true");
        httpRespones.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

        Throwable throwable = e.getCause() == null ? e : e.getCause();
        AirResult airResult = AirResult.build(HttpStatus.UNAUTHORIZED.value(), throwable.getMessage());

        String toJson = new Gson().toJson(airResult);
        try {
            httpRespones.getWriter().print(toJson);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return false;
    }

    private String getReuestToken(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");

        if (StringUtils.isBlank(token)) {
            token = httpServletRequest.getParameter("token");
        }
        return token;
    }
}
