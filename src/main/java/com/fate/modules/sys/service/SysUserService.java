package com.fate.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fate.common.utils.PageUtils;
import com.fate.modules.sys.entity.SysUser;
import com.fate.modules.sys.form.RegiserUserReq;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.List;
import java.util.Map;

/**
 * @
 */
public interface SysUserService extends IService<SysUser> {

    SysUser queryByUserName(String username);

    /**
     * 查询用户的所有菜单ID
     * @param userId
     * @return
     */
    List<Long> queryAllMenuId(Long userId);

    void testAspect(ProceedingJoinPoint point) throws Throwable;

    void testBspect(ProceedingJoinPoint point) throws Throwable;


    PageUtils queryPage(Map<String, Object> params);

    boolean updatePassword(Long userId, String password, String newPassword);

    void update(SysUser user);

    void add(SysUser sysUser);

    void save(RegiserUserReq userReq);

    void testRedis() throws Exception;

    void testExceptionTranscation();

    void testExceptionIn();
}
