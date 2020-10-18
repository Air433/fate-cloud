package com.fate.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fate.common.annotation.TestAspect;
import com.fate.modules.sys.dao.SysLogMapper;
import com.fate.modules.sys.entity.SysLog;
import com.fate.modules.sys.service.SysLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author oyg
 * @Date 2018/7/29/18:06
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @TestAspect("a")
    @Override
    public void m1() {
        System.err.println("------------我是m1方法，我a切面后执行---------");
    }

    @TestAspect("b")
    @Override
    public void m2() {
        System.err.println("------------我是m2方法，我b切面后执行---------");
    }

    @Override
    public void m3() {
        System.err.println("------------我是m3方法，无需要切面---------");
    }

    @TestAspect
    @Override
    public void m5() throws Exception {
        System.err.println("-----执行M5");
        if (1==1){
            throw new Exception("111111111111111111111");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void testDelete() {
        this.baseMapper.deleteBatch(new String[0]);

        if (1==1){
            throw new RuntimeException();
        }
    }
}
