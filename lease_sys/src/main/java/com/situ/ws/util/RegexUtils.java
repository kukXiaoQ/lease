package com.situ.ws.util;

import java.util.Random;

public class RegexUtils {

    private static final String REGEX_ITEM = "^\\w{6,16}$";


    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6; // 验证码长度

    /**
     * 自定义正则表达式
     * @param regex 表达式
     * @param strs 校验参数
     * @return boolean
     */
    public static boolean matches(String regex,String... strs){
        boolean result = true;

        for (String str : strs) {
            if (!str.matches(regex)) {
                result = false;
                return result;
            }
        }

        return result;
    }


    public static void main(String[] args) {
        int generateTimes = 5; // 生成次数
        for (int i = 0; i < generateTimes; i++) {
            String code = msgCode();
            System.out.println("生成的验证码: " + code);
        }
    }

    public static String msgCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }

}
