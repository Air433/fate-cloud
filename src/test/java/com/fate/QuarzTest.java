package com.fate;

import org.junit.Test;

import org.quartz.*;
import org.quartz.impl.DirectSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: ouyanggang
 * @create: 2020-02-17 14:51
 */
public class QuarzTest {

    @Test
    public void t1() throws SchedulerException, InterruptedException {
        DirectSchedulerFactory directSchedulerFactory = DirectSchedulerFactory.getInstance();
        directSchedulerFactory.createVolatileScheduler(5);
        Scheduler scheduler = directSchedulerFactory.getScheduler();

        scheduler.start();

        JobDetail jobDetail = JobBuilder.newJob(StudentJob.class)
                .withIdentity("myjob", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("mytrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(500).repeatForever())
                .build();
        TriggerBuilder.newTrigger();

        scheduler.scheduleJob(jobDetail, trigger);

        Thread.sleep(10000);

        scheduler.pauseJob(jobDetail.getKey());

        Thread.sleep(10000);


        scheduler.resumeJob(jobDetail.getKey());

        Thread.sleep(100000);
    }

    @Test
    public void t2(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss" );


        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        Date date = calendar.getTime();
        String format = sdf.format(date);
        System.err.println(format);
    }

    @Test
    public void t3(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss" );


        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        Date date = calendar.getTime();
        String format = sdf.format(date);
        System.err.println(format);
    }
    @Test
    public void t5(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss" );


        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        Date startDate = new Date(calendar.getTimeInMillis());
        String format = sdf.format(startDate);
        System.err.println(format);

        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        Date endDate = new Date(calendar.getTimeInMillis());
        String dformat = sdf.format(endDate);
        System.err.println(dformat);
    }

    @Test
    public void t6(){
        Calendar calendar = Calendar.getInstance();

//        SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss" );

        calendar.set(2019,12,7);

//        Date endDate = new Date(calendar.getTimeInMillis());
//        String dformat = sdf.format(calendar.getTime());
        System.err.println(calendar.getTime());
    }

    @Test
    public void t7(){
        // create a calendar
        Calendar cal = Calendar.getInstance();

        // print current time
//        System.out.println("Current year is :" + cal.getTime());

        // set the year,month and day to something else
        cal.set(2018, 11, 25);

        // print the result
        System.out.println("Altered year is :" + cal.getTime());//原文出自【易百教程】，商业转载请联系作者获得授权，非商业请保留原文链接：https://www.yiibai.com/java/util/calendar_setfield2.html
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss" );
        System.err.println(sdf.format(cal.getTime()));

    }

}

