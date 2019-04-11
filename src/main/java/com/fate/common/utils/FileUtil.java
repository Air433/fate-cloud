/*
 * 广州理德物联网科技有限公司
 * Copyright (c) 2016.
 */

package com.fate.common.utils;

/**
 * Created by tanqimin on 2014/11/10.
 */


import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 文件工具类
 */
public class FileUtil {

    public static byte[] getBytesByFile(String filePath) {
        byte[] buffer = null;
        try {
            File                  file = new File(filePath);
            FileInputStream       fis  = new FileInputStream(file);
            ByteArrayOutputStream bos  = new ByteArrayOutputStream(1000);
            byte[]                b    = new byte[1000];
            int                   n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    /**
     * 把字节数组保存为一个文件
     *
     * @param bytes
     * @param outputFileFullName
     * @return
     */
    public static File saveFileByBytes(
            String outputFileFullName,
            byte[] bytes)
            throws IOException {
        File                 file             = null;
        FileOutputStream     fileOutputStream = null;
        BufferedOutputStream stream           = null;
        try {
            file = new File(outputFileFullName);
            String  dirPath = file.getParent();
            File    dirFile = new File(dirPath);
            if (dirFile.exists() == false) {
                dirFile.mkdirs();
            }

            fileOutputStream = new FileOutputStream(file);
//            stream = new BufferedOutputStream(fileOutputStream);
//            stream.write(bytes);
//            stream.flush();
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return file;
    }


    /**
     * 获取文件名称(不包括扩展名)
     *
     * @param file
     * @return
     */
//    public static String getFileNameWithoutExtension(File file) {
//        if (file == null) return null;
//        String fileName = file.getName();
//        return getFileNameWithoutExtension(fileName);
//    }


    /**
     * 获取文件名称(不包括扩展名)
     *
     * @param fileFullName 文件全名称
     * @return
     */
//    public static String getFileNameWithoutExtension(String fileFullName) {
//        if (ValidateUtil.isBlank(fileFullName)) return "";
//        String fileExtension = getFileExtension(fileFullName);
//        if (ValidateUtil.isNotBlank(fileExtension)) {
//            return fileFullName.replace("." + fileExtension, "");
//        }
//        return fileFullName;
//    }

    /**
     * 获取文件扩展名
     *
     * @param file
     * @return
     */
//    public static String getFileExtension(File file) {
//        if (file == null) return null;
//        String fileName = file.getName();
//        return getFileExtension(fileName);
//    }

    /**
     * 获取文件扩展名
     *
     * @param fileFullName 文件全名称
     * @return
     */
//    public static String getFileExtension(String fileFullName) {
//        if (ValidateUtil.isBlank(fileFullName)) return "";
//        if (fileFullName.lastIndexOf(".") != -1 && fileFullName.lastIndexOf(".") != 0) {
//            return fileFullName.substring(fileFullName.lastIndexOf(".") + 1);
//        } else {
//            return "";
//        }
//    }


    public static String getMd5ByFile(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest   digest   = null;
        FileInputStream in       = null;
        byte            buffer[] = new byte[1024];
        int             len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    public static void delete(String fileFullPath) {
        try {
            File file = new File(fileFullPath);
            file.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
