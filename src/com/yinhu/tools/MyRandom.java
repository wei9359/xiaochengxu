package com.yinhu.tools;

import java.util.Random;

/**
 * @ClassName MyRandom
 * @auther 魏星
 * @DATE 2018/7/20
 */
public class MyRandom {
    public static String getRandomString(int num){
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0;i<num;i++){
            stringBuffer.append(random.nextInt(10));
        }
        return stringBuffer.toString();
    }
}
