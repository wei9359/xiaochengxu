package com.yinhu.controller.weixin;

import com.qiniu.util.Auth;
import com.yinhu.tools.ConfigUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName XCXQiNiuController
 * @auther 魏星
 * @DATE 2018/6/27
 */
@Controller
@RequestMapping("weixin")
public class XCXQiNiuController {
    @RequestMapping("qiniuToken")
    public @ResponseBody
    Map<Object,Object> qiniuToken(){
        String uptoken = Auth.create(ConfigUtils.qiniuAccessKey,ConfigUtils.qiniuSecretKey).uploadToken("houseimg");
        Map<Object,Object> map = new HashMap<Object, Object>();
        map.put("uptoken",uptoken);
        return  map;
    }
}
