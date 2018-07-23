package com.yinhu.tools.sms;

import com.yinhu.tools.ConfigUtils;
import com.yinhu.tools.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Set;

/**
 * @ClassName SendSMS
 * @auther 魏星
 * @DATE 2018/7/18
 */
//发送短信
public class SendSMS {

    @RequestMapping(value = "sendSMS")
    public static boolean sendSMS(String templateId,String phone,String[] templateContent){
        HashMap<String, Object> result = null;
        CCPRestSmsSDK ccpRestSmsSDK = new CCPRestSmsSDK();
        // 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883.
        ccpRestSmsSDK.init(ConfigUtils.rlyServiceIp,ConfigUtils.rlyServicePort);
        // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在控制首页中看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN。
        ccpRestSmsSDK.setAccount(ConfigUtils.rlyaccountsid,ConfigUtils.rlyauthtoken);
        // 请使用管理控制台中已创建应用的APPID。
        ccpRestSmsSDK.setAppId(ConfigUtils.rlyappid);
        //result = restAPI.sendTemplateSMS("号码1,号码2等","模板Id" ,new String[]{"模板内容1","模板内容2"});
        result = ccpRestSmsSDK.sendTemplateSMS(phone, templateId , templateContent);

        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
            return true;
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            return false;
        }
    }
}
