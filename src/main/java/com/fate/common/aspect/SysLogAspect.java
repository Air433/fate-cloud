package com.fate.common.aspect;

import com.google.gson.Gson;
import com.fate.common.annotation.RedisLock;
import com.fate.common.annotation.SysLogAn;
import com.fate.common.annotation.SysLogNotUser;
import com.fate.common.annotation.TestAspect;
import com.fate.common.utils.HttpContextUtils;
import com.fate.common.utils.IPUtils;
import com.fate.common.utils.TimeMehtodUtil;
import com.fate.modules.sys.entity.SysLog;
import com.fate.modules.sys.entity.SysUser;
import com.fate.modules.sys.service.SysLogService;
import com.fate.modules.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @Author oyg
 * @Date 2018/7/29/18:05
 */
@Aspect
@Component
public class SysLogAspect implements Ordered {
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TimeMehtodUtil timeMehtodUtil;

    @Pointcut("@annotation(com.fate.common.annotation.TestAspect)")
    public void testAspect(){

    }

    @Pointcut("@annotation(com.fate.common.annotation.SysLogAn)")
    public void logPointCut(){

    }

    @Pointcut("@annotation(com.fate.common.annotation.SysLogNotUser)")
    public void logPointCutNotUser(){

    }

    @Pointcut("@annotation(com.fate.common.annotation.RedisLock)")
    public void redisLockPointCut(){

    }

    @Pointcut("@annotation(com.fate.common.annotation.RedisLockDelay)")
    public void redisLockDelayPointCut(){

    }

    @Around("redisLockPointCut()")
    public Object aroundRedisLock(ProceedingJoinPoint point) throws Throwable {
        Callable callable = ()->{
            try {
                return point.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
        };

        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        TimeMehtodUtil.exeMethod(callable, redisLock.lockName(), redisLock.expire());

        return null;
    }

    @Around("redisLockDelayPointCut()")
    public Object aroundRedisDelayLock(ProceedingJoinPoint point) throws Throwable {

        return point.proceed();
    }

    @Around("logPointCutNotUser()")
    public Object aroundNotUser(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();

        Object result = point.proceed();

        long time = System.currentTimeMillis() - beginTime;

        saveSysLogNotUser(point, time);
        return result;
    }

    private void saveSysLogNotUser(ProceedingJoinPoint point, long time) {
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();
        SysLogNotUser sysLogAn = method.getAnnotation(SysLogNotUser.class);
        if (sysLogAn != null){
            //注解上的描述
            sysLog.setOperation(sysLogAn.value());
        }

        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        Object[] args = point.getArgs();

        try {
            String params = new Gson().toJson(args);
            sysLog.setParams(params);
        }catch (Exception e){
            e.printStackTrace();
        }

        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        sysLog.setIp(IPUtils.getIpAddr(request));

        //String username = ((SysUser) SecurityUtils.getSubject().getPrincipal()).getUsername();
        //sysLog.setUsername(username);

        sysLog.setTime(time);
        sysLog.setCreateDate(new Date());

        sysLogService.save(sysLog);
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();

        Object result = point.proceed();

        long time = System.currentTimeMillis() - beginTime;

        saveSysLog(point, time);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint point, long time) {
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();
        SysLogAn sysLogAn = method.getAnnotation(SysLogAn.class);
        if (sysLogAn != null){
            //注解上的描述
            sysLog.setOperation(sysLogAn.value());
        }

        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        Object[] args = point.getArgs();

        try {
            String params = new Gson().toJson(args);
            sysLog.setParams(params);
        }catch (Exception e){
            e.printStackTrace();
        }

        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        sysLog.setIp(IPUtils.getIpAddr(request));

        String username = ((SysUser) SecurityUtils.getSubject().getPrincipal()).getUsername();
        sysLog.setUsername(username);

        sysLog.setTime(time);
        sysLog.setCreateDate(new Date());

        sysLogService.save(sysLog);
    }

    @Around("testAspect()")
    public Object test(ProceedingJoinPoint point) throws Throwable {
        System.err.println("---------切面1");
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        //System.err.println("-------------进入切面"+this.getClass().getName()+"-------------------");
        TestAspect aspect = method.getAnnotation(TestAspect.class);
        if (aspect.value().equals("a")){
            sysUserService.testAspect(point);
        }
        if (aspect.value().equals("b")){
            sysUserService.testBspect(point);
        }

        if (aspect.value().equals("")){
            try {
                point.proceed();
            }catch (Exception e){
                System.err.println(e.getMessage());
            }

        }

        return null;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
