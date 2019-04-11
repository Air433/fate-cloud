package com.fate.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ouyanggang on 2018/5/18.
 */
public class MyExceptionHandler implements HandlerExceptionResolver {

  public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                                       Exception e) {
    return null;
  }
}
