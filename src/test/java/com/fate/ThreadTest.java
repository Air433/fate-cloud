package com.fate;

import com.fate.entity.User;
import com.fate.modules.sys.entity.SysMenu;
import com.fate.modules.sys.entity.SysRole;
import com.fate.modules.sys.entity.SysUser;
import com.google.common.collect.ImmutableCollection;
import org.junit.Test;

import java.text.CollationKey;
import java.text.Collator;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/**
 * @Author oyg
 * @Date 2018/8/4/09:23
 */
public class ThreadTest {

    public static final ThreadLocal threadLocal = new ThreadLocal(){
        @Override
        public Object initialValue(){
            return new User();
        };
    };

    public static final ThreadLocal threadLocal2 = new ThreadLocal();

    public static final ThreadLocal threadLocal3 = ThreadLocal.withInitial(()->{
        return new User();
    });

    Thread thread = new Thread(){
        @Override
        public void run(){
            System.err.println(1);
        }
    };



    @org.junit.Test
    public void t99() throws ExecutionException, InterruptedException {
        List<Function> functions = getFunctions();

        List<Callable<String>> callableList = new ArrayList<>();

        for (Function function : functions) {
            Callable callable = ()->{
                return function.apply(null);
            };
            callableList.add(callable);
        }
        List<?> strings = poolExeuter(callableList);
        System.err.println(strings);
    }

    private  List<Function> getFunctions(){
        List<Function> functions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Function<?,String> function = (x)-> {
                System.out.println(">>>" + finalI + "任务启动");
                Date dateTmp1 = new Date();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Date dateTmp2 = new Date();
                long time = dateTmp2.getTime() - dateTmp1.getTime();
                System.out.println(">>>" + finalI + "任务终止");
                return finalI + "任务返回运行结果,当前任务时间【" + time + "毫秒】";
            };
            functions.add(function);
        }
        return functions;
    }

    @org.junit.Test
    public void t10() throws ExecutionException, InterruptedException {
        List<Function<?,String>> functions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Function<?,String> function = (x)-> {
                System.out.println(">>>" + finalI + "任务启动");
                Date dateTmp1 = new Date();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Date dateTmp2 = new Date();
                long time = dateTmp2.getTime() - dateTmp1.getTime();
                System.out.println(">>>" + finalI + "任务终止");
                return finalI + "任务返回运行结果,当前任务时间【" + time + "毫秒】";
            };
            functions.add(function);
        }

        for (Function<?, String> function : functions) {
            Callable<String> callable = ()->{

                return function.apply(null);
            };
        }


        List<Callable<String>> callableList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final int k = i;
            callableList.add(new Callable() {
                @Override
                public Object call() throws Exception {
                    System.out.println(">>>" + k + "任务启动");
                    Date dateTmp1 = new Date();
                    Thread.sleep(5000);
                    Date dateTmp2 = new Date();
                    long time = dateTmp2.getTime() - dateTmp1.getTime();
                    System.out.println(">>>" + k + "任务终止");
                    return k + "任务返回运行结果,当前任务时间【" + time + "毫秒】";
                }
            });
        }
        List<String> stringList = poolExeuter(callableList);
        System.err.println(stringList);
    }


    @Test
    public void t9() throws ExecutionException, InterruptedException {

        List<Callable<String>> callableList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final int k = i;
            callableList.add(new Callable() {
                @Override
                public Object call() throws Exception {
                    System.out.println(">>>" + k + "任务启动");
                    Date dateTmp1 = new Date();
                    Thread.sleep(5000);
                    Date dateTmp2 = new Date();
                    long time = dateTmp2.getTime() - dateTmp1.getTime();
                    System.out.println(">>>" + k + "任务终止");
                    return k + "任务返回运行结果,当前任务时间【" + time + "毫秒】";
                }
            });
        }
        List<String> stringList = poolExeuter(callableList);
        System.err.println(stringList);
    }

    private <T> List<T> poolExeuter(List<Callable<T>> callableList) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(callableList.size());
        List<Future> list = new ArrayList<>();
        callableList.forEach(x-> list.add(pool.submit(x)));
        pool.shutdown();
        List<T> tList = new ArrayList<>();
        for (Future f : list) {
            System.err.println(">>>" + f.get().toString());
            tList.add((T) f.get());
        }
        return tList;
    }

    @org.junit.Test
    public void t8 () throws ExecutionException, InterruptedException {
        System.out.println("----程序开始运行----");
        Date date1 = new Date();

        int taskSize = 10;
        // 创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<Future>();
        for (int i = 0; i < taskSize; i++) {
            Callable<String> c = new MyCallable(i + " ");
            // 执行任务并获取Future对象
//            try {

            Future<String> f = pool.submit(c);
            list.add(f);
//            }catch (Exception e){
//                System.err.println("进入 pool.submit(c)异常");
//                e.printStackTrace();
//            }

            // System.out.println(">>>" + f.get().toString());

        }
        // 关闭线程池
        pool.shutdown();

        List<String> result = new ArrayList<>();
        // 获取所有并发任务的运行结果
        for (Future<String> f : list) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            try {
                System.out.println(">>>" + f.get().toString());
                result.add(f.get());
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        Date date2 = new Date();
        System.out.println("----程序结束运行----，程序运行时间【"
                + (date2.getTime() - date1.getTime()) + "毫秒】");

    }

    class MyCallable<T> implements Callable {
        private String taskNum;

        MyCallable(String taskNum) {
            this.taskNum = taskNum;
        }
        @Override
        public T call() throws Exception {
            System.out.println(">>>" + taskNum + "任务启动");
            Date dateTmp1 = new Date();
            if (taskNum.equals("3 ")){
                throw new Exception();
            }
            Thread.sleep(10000);
            Date dateTmp2 = new Date();
            long time = dateTmp2.getTime() - dateTmp1.getTime();
            System.out.println(">>>" + taskNum + "任务终止");
            return (T)(taskNum + "任务返回运行结果,当前任务时间【" + time + "毫秒】");
        }
    }

    @Test
    public void t2(){

        SysUser sysUser= new SysUser();
        SysRole sysRole = new SysRole();
        Object coc = coc(sysRole, sysUser);

        System.err.println(coc);
    }

    private <T, K> T coc(T t, K c){
        return (T)c;
    }

    @Test
    public void t3(){
        SysUser sysUser = new SysUser();
        sysUser.setUsername("苏杉杉");
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("管理者");

        List<SysMenu> sysMenus = new ArrayList<>();

        TestResponse<SysMenu, SysUser, SysRole> testResponse = add(sysMenus, sysUser, sysRole);

        List<SysMenu> menuList = testResponse.gettList();

        SysUser k = testResponse.getK();

        SysRole m = testResponse.getM();

        TestResponse<SysMenu, Object, SysRole> add = add(sysMenus, null, sysRole);

    }

    private <T,K,M> TestResponse<T,K,M> add(List<T> ts, K k, M m){

        TestResponse testResponse = new TestResponse();

        testResponse.settList(ts);
        testResponse.setK(k);
        testResponse.setM(m);
        return testResponse;
    }

    private Lock lock = new ReentrantLock();

    int y = 0;
    int p = 0;
    @Test
    public void testLock() throws InterruptedException {
        new Thread(()->{
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            lock.lock();
//            try{
            synchronized (lock){

                int k = 0;
                while (k<1000){
                    y +=1;
                    p +=1;
                    System.err.println(Thread.currentThread()+"----"+y+"+"+p+"="+(y+p)+"----");
                    k++;
                }
            }
//            }finally {
//                lock.unlock();
//            }


        }).start();
        new Thread(()->{
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            lock.lock();
//            try{
            synchronized (lock){

                int k = 0;
                while (k<1000){
                    y +=1;
                    p +=1;
                    System.err.println(Thread.currentThread()+"----"+y+"+"+p+"="+(y+p)+"----");
                    k++;
                }
            }
//            }
//            }finally {
//                lock.unlock();
//            }


        }).start();
        Thread.sleep(100000);
    }
    @Test
    public void test5() throws InterruptedException {
        //User user = new User();
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                //threadLocal.set(user);
//                User user1 = (User)threadLocal2.get();
                User user2 = new User();
                user2.setUserName("AAAAA");
                threadLocal2.set(user2);
//                user1.setUserName("苏杉杉");
                System.err.println(threadLocal2.get().hashCode());
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(2000);
        System.err.println(((User)threadLocal2.get()));
    }

    @Test
    public void test7() throws InterruptedException {
        //User user = new User();
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                //threadLocal.set(user);
//                User user1 = (User)threadLocal2.get();
//                User user2 = new User();
//                user2.setUserName("AAAAA");
//                threadLocal.set(user2);
//                user1.setUserName("苏杉杉");
                System.err.println(threadLocal.get().hashCode());

            }).start();
        }
        Thread.sleep(2000);
        System.err.println(((User)threadLocal.get()).hashCode());
    }
    @Test
    public void t6(){
        Thread thread = new Thread(ThreadTest::k);
        thread.start();
        System.err.println();
        threadLocal2.set(33.434);
    }

    public static void k(){
        System.err.println(1);
    }

    @Test
    public void t11(){
        List<String> data = new ArrayList<String>();
        data.add(null);
        data.add("77");
        data.add("22");
        data.add("zz");
        data.add("tw");
        data.add("hs");
        data.add("ac");
        data.add("上海");
        data.add("天津");
        data.add(null);
        data.add("北京");
        data.add("深圳");
        data.add("广州");
        data.add("成都");
        data.add("西安");
        data.add("武汉");
        data.add(null);
        data.add("郑州");
        data.add("邯郸");

        Collections.sort(data, new Comparator<String>() {
            Collator collator = Collator.getInstance(Locale.CHINA);

            @Override
            public int compare(String o1, String o2) {

                if (o1 == null){
                    return 1;
                }

                if (o2 == null){
                    return -1;
                }

                CollationKey key1 = collator.getCollationKey(o1);
                CollationKey key2 = collator.getCollationKey(o2);
                return key1.compareTo(key2);

            }
        });

        for (String str : data) {
            System.out.print(str+"--");
        }


    }

    @Test
    public void t12(){
        List<String> data = new ArrayList<String>();
        data.add(null);
        data.add("77");
        data.add("22");
        data.add("zz");
        data.add("tw");
        data.add("hs");
        data.add("ac");
        data.add("上海");
        data.add("天津");
        data.add(null);
        data.add("北京");
        data.add("深圳");
        data.add("广州");
        data.add("成都");
        data.add("西安");
        data.add("武汉");
        data.add(null);
        data.add("郑州");
        data.add("邯郸");

        Collections.sort(data, new Comparator<String>() {
            Collator collator = Collator.getInstance(Locale.CHINA);

            @Override
            public int compare(String o1, String o2) {

                if (o1 == null){
                    return 1;
                }

                if (o2 == null){
                    return -1;
                }

                CollationKey key1 = collator.getCollationKey(o1);
                CollationKey key2 = collator.getCollationKey(o2);
                return key1.compareTo(key2);

            }
        });

        for (String str : data) {
            System.out.print(str+"--");
        }

        Double a = 1.0;
        a.compareTo(2.0);
    }

    @Test
    public void t13(){
        List<Double> data = new ArrayList<>();
        data.add(null);
        data.add(null);
        data.add(3.1);
        data.add(null);
        data.add(null);
        data.add(4.6);
        data.add(null);
        data.add(9.6);
        data.add(null);
        data.add(2.1);
        data.add(null);


        Collections.sort(data, (o1, o2) -> {

            if (o1 == null){
                return 1;
            }

            if (o2 == null){
                return -1;
            }

            return o1.compareTo(o2);

        });

        for (Double str : data) {
            System.out.print(str+"--");
        }

        Double a = 1.0;
        a.compareTo(2.0);
    }

    @Test
    public void t15(){
        List<String> data = new ArrayList<String>();
        data.add(null);
        data.add("77");
        data.add("22");
        data.add("zz");
        data.add("tw");
        data.add("hs");
        data.add("ac");
        data.add("上海");
        data.add("天津");
        data.add(null);
        data.add("北京");
        data.add("深圳");
        data.add("广州");
        data.add("成都");
        data.add("西安");
        data.add("武汉");
        data.add(null);
        data.add("郑州");
        data.add("邯郸");

        Collections.sort(data,(o1, o2)-> {
            Collator collator = Collator.getInstance(Locale.CHINA);
                if (o1 == null){
                    return 1;
                }

                if (o2 == null){
                    return -1;
                }

                CollationKey key1 = collator.getCollationKey(o1);
                CollationKey key2 = collator.getCollationKey(o2);
                return key1.compareTo(key2);
        });

        for (String str : data) {
            System.out.print(str+"--");
        }

        Double a = 1.0;
        a.compareTo(2.0);
    }

    @Test
    public void t16(){
        Comparator<String> stringComparator = (o1, o2) -> {
            Collator collator = Collator.getInstance(Locale.CHINA);
            if (o1 == null) {
                return 1;
            }

            if (o2 == null) {
                return -1;
            }

            CollationKey key1 = collator.getCollationKey(o1);
            CollationKey key2 = collator.getCollationKey(o2);
            return key1.compareTo(key2);
        };

        Comparator<Double> doubleComparator = (o1, o2) -> {

            if (o1 == null) {
                return 1;
            }

            if (o2 == null) {
                return -1;
            }

            return o1.compareTo(o2);

        };
    }

    @Test
    public void t17(){
        Map<String, Object> map = new HashMap<>();
        map.put("userid", 3);
        map.put("name", "增大神");
        map.put("age", 1888);

        StringBuilder stringBuilder = new StringBuilder();
        map.forEach((x, y)->{
            stringBuilder.append(x).append("=").append("y,");
        });

        System.err.println(stringBuilder.substring(0, stringBuilder.toString().length()-1));

    }
}
