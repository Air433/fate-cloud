package com.fate;

import com.fate.common.validator.Assert;
import com.fate.modules.sys.entity.SysUser;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Author oyg
 * @Date 2018/7/25/22:04
 */
public class TeCst {

    @org.junit.Test


    public void t1() {
        Integer i = 1;
        System.err.println(i);
        con(i);
        System.err.println(i);
    }

    private void con(Integer i) {
        i = 3;
        System.err.println(i);
    }

    @org.junit.Test
    public void t2() {
        Integer i = 3;
        Consumer<Integer> consumer = x -> x = 5;
        consumer.accept(i);
        System.err.println(i);
    }

    @org.junit.Test
    public void t3() {
        SysUser userUser = new SysUser();
        userUser.setUsername("abcbbnccbvvb");
        Consumer<SysUser> consumer = x -> x.setUsername("苏杉杉");
        consumer.accept(userUser);
        System.err.println(userUser.getUsername());


    }

    @Test
    public void t5() {
        List list = new ArrayList();
        Assert.isBlank(list, "a");
    }

    @Test
    public void t6() {
        List<Integer> list = new ArrayList<>();
        long sizeOf = JUtil.sizeOf(list);
        System.err.println(sizeOf);
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        long sizeOf2 = JUtil.sizeOf(list);
        System.err.println(sizeOf2);
    }

    @Test
    public void t9(){
        String str = "y1=";

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.YEAR, 2005);
        a(str, calendar);
        System.err.println(str + calendar.get(calendar.YEAR));
    }

    public void a(String s, Calendar cal){
        s = "y2=";
        cal.set(cal.YEAR, 2000);
    }
    @Test
    public void t7(){
        int[] ts = {8,5,75,8,975,7,12,3,6};

        for (int i = 0; i < ts.length; i++) {
            System.err.print(ts[i]+ ",");
        }

        System.err.println();
        heapSort(ts);
        for (int i = 0; i < ts.length; i++) {
            System.err.print(ts[i]+ ",");
        }
    }

    @Test
    public void t8(){
        int[] ts = {8,5,75,6,97,7,12,3,1};

        for (int i = 0; i < ts.length; i++) {
            System.err.print(ts[i]+ ",");
        }

        System.err.println();
        heapSort(ts);
        for (int i = 0; i < ts.length; i++) {
            System.err.print(ts[i]+ ",");
        }
    }

    private static void heapSort(int[] table){
        int n = table.length;
        for (int j = n/2-1; j >= 0; j--) {
            sift(table, j, n-1);
        }

        System.err.println();
        for (int i = 0; i < table.length; i++) {
            System.err.print(table[i]+ ",");
        }
        System.err.println();
        for (int i = 0; i < table.length; i++) {
            System.out.print(table[i]+ ",");
        }

        for (int j = n-1; j > 0; j--) {
            int temp = table[0];
            table[0] = table[j];
            table[j] = temp;
            sift(table, 0, j-1);
        }

    }

    private static void sift(int[] table, int begin, int end) {
        int i = begin, j=2*i+1;
        int temp = table[i];
        while (j<=end){
            if (j<end && table[j] > table[j+1])
                j++;

            if (temp > table[j]){
                table[i]=table[j];
                i=j;
                j=2*i + 1;
            }else break;
        }
        table[i] = temp;
    }
}


class JUtil {
    /**
     * 对象头部的大小
     */
    private static final int OBJECT_HEADER_SIZE = 8;
    /**
     * 对象占用内存的最小值
     */
    private static final int MINIMUM_OBJECT_SIZE = 8;
    /**
     * 对象按多少字节的粒度进行对齐
     */
    private static final int OBJECT_ALIGNMENT = 8;

    public static long sizeOf(Object obj) {
        //获得Unsafe实例
        Unsafe unsafe;
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = (Unsafe) unsafeField.get(null);
        } catch (Throwable t) {
            unsafe = null;
        }

        //判断对象是否为数组
        if (obj.getClass().isArray()) {
            Class<?> klazz = obj.getClass();
            int base = unsafe.arrayBaseOffset(klazz);
            int scale = unsafe.arrayIndexScale(klazz);
            long size = base + (scale * Array.getLength(obj));
            if ((size % OBJECT_ALIGNMENT) != 0) {
                size += OBJECT_ALIGNMENT - (size % OBJECT_ALIGNMENT);
            }
            return Math.max(MINIMUM_OBJECT_SIZE, size);
        } else {
            //如果数组对象则迭代遍历该对象的父类，找到最后一个非静态字段的偏移量
            for (Class<?> klazz = obj.getClass(); klazz != null; klazz = klazz.getSuperclass()) {
                long lastFieldOffset = -1;
                for (Field f : klazz.getDeclaredFields()) {
                    if (!Modifier.isStatic(f.getModifiers())) {
                        lastFieldOffset = Math.max(lastFieldOffset, unsafe.objectFieldOffset(f));
                    }
                }
                if (lastFieldOffset > 0) {
                    lastFieldOffset += 1;
                    if ((lastFieldOffset % OBJECT_ALIGNMENT) != 0) {
                        lastFieldOffset += OBJECT_ALIGNMENT - (lastFieldOffset % OBJECT_ALIGNMENT);
                    }
                    return Math.max(MINIMUM_OBJECT_SIZE, lastFieldOffset);
                }
            }
            //该对象没有任何属性
            long size = OBJECT_HEADER_SIZE;
            if ((size % OBJECT_ALIGNMENT) != 0) {
                size += OBJECT_ALIGNMENT - (size % OBJECT_ALIGNMENT);
            }
            return Math.max(MINIMUM_OBJECT_SIZE, size);
        }
    }

}

