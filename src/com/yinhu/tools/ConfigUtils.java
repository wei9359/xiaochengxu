package com.yinhu.tools;

import java.io.IOException;
import java.util.Properties;

/**
 * @ClassName Configs
 * @auther 魏星
 * @DATE 2018/6/26
 */
public class ConfigUtils  {
    public static Properties properties = new Properties();
    //微信小程序ID
    public static String wxxcxAppId;
    //微信小程序密钥
    public static String wxxcxAppSecret;
    //微信小程序获取用户信息URL
    public static String wxxcxGetUserIfoUrl;
    //七牛ID
    public static String qiniuAccessKey;
    //七牛密钥
    public static String qiniuSecretKey;
    //容联云sid
    public static String rlyaccountsid;
    //容联云token
    public static String rlyauthtoken;
    //容联云appid
    public static String rlyappid;

    static {
        try{
            properties.load(ConfigUtils.class.getClassLoader().getResourceAsStream("config.properties"));

            wxxcxAppId = properties.getProperty("wxxcxAppId");

            wxxcxAppSecret = properties.getProperty("wxxcxAppSecret");

            wxxcxGetUserIfoUrl = properties.getProperty("wxxcxGetUserIfoUrl");

            qiniuAccessKey = properties.getProperty("qiniuAccessKey");

            qiniuSecretKey = properties.getProperty("qiniuSecretKey");

            rlyaccountsid = properties.getProperty("rlyaccountsid");

            rlyauthtoken = properties.getProperty("rlyauthtoken");

            rlyappid = properties.getProperty("rlyappid");

        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
