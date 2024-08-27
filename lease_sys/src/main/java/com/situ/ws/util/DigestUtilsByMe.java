package com.situ.ws.util;

import org.apache.commons.codec.digest.DigestUtils;

public class DigestUtilsByMe {

    /**
     * 加密次数的位置
     */
    private final static Integer countIndex = 27;
    /**
     * 加密盐值的位置,索引
     */
    private final static int saltIndex = 7;

    /**
     * 密文
     */
    private final static char[] items = {
            '0','1','2','3',
            '4','5','6','7',
            '8','9','a','b',
            'c','d','e','f'};

    public static String randomDigest(String password) {



        int randomCountIndex = (int) (Math.random() * items.length);
        int randomSaltIndex = (int) (Math.random() * items.length);
        // 加密次数
        char count = items[randomCountIndex];
        // 盐值
        char salt = items[randomSaltIndex];

        String result = password + salt;

        for (int i = 0; i < count; i++) {
            result = DigestUtils.md5Hex(result);
        }

        result = result.substring(0,saltIndex) + salt + result.substring(saltIndex);
        result = result.substring(0,countIndex) + count + result.substring(countIndex);

        System.out.println(count);
        System.out.println(salt);
        return result;
    }

    public static void main(String[] args) {

        String pass = "123456";
        String password = randomDigest(pass);

        System.out.println(password);
        boolean flag = decode(pass, password);
        System.out.println(flag);
    }

    /**
     * 比对解析
     * @param password 原文
     * @param digest 密文
     * @return 布尔
     */
    public static boolean decode(String password, String digest) {

        // 获取密文的salt
        char salt = digest.charAt(saltIndex);
        char count = digest.charAt(countIndex);

        // 获取密文的count
        // 开始加密
        String result = password + salt;
        for (int i = 0; i < count; i++) {
            result = DigestUtils.md5Hex(result);
        }

        result = result.substring(0,saltIndex) + salt + result.substring(saltIndex);
        result = result.substring(0,countIndex) + count + result.substring(countIndex);
        return result.equals(digest);
    }
}
