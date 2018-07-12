package com.yinhu.tools;

import com.yinhu.tools.sms.CCPRestSmsSDK;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName sendSMS
 * @auther 魏星
 * @DATE 2018/7/11
 */
public class sendSMS {
    Map<String,Object> result = new HashMap<String, Object>();
    CCPRestSmsSDK ccpRestSmsSDK = new CCPRestSmsSDK();
    public Map<String,Object> sendYZM(String phoneNum){
        ccpRestSmsSDK.init("https://app.cloopen.com","8883");
        ccpRestSmsSDK.setAccount(ConfigUtils.rlyaccountsid,ConfigUtils.rlyauthtoken);
        return result;
    }
}
