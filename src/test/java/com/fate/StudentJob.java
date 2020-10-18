package com.fate;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author: ouyanggang
 * @create: 2020-02-18 10:14
 */
public class StudentJob implements Job {

    static int i = 1;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.err.println(String.format("第%s次执行", i++));
    }
}
