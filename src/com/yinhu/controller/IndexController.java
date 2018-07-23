package com.yinhu.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yinhu.pojo.custom.UserCustom;
import com.yinhu.service.UserService;
import com.yinhu.tools.Message;
import com.yinhu.tools.kuangshi.CardOperate;
import com.yinhu.tools.kuangshi.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName IndexController
 * @auther 魏星
 * @DATE 2018/6/19
 */
@Controller
public class IndexController {
    private static Logger logger = Logger.getLogger(IndexController.class.toString());
    private String apiKey = "MVgl1iMkpYUcwwy8anJRTP1v4FFjYORD";
    private String apiSecret = "hFKrfffW3b0GzYAHS-dz_nBJGJ2VUGoU";
    @Resource
    public UserService userService;
    @RequestMapping(value = "index")
    public String index(Model model) throws Exception{
        Map<Object,Object> condition = new HashMap<Object, Object>();
        condition.put("userName","魏星");
        logger.info("哈哈哈");
        UserCustom userCustom = userService.queryOne(condition);
        if (userCustom==null){
            model.addAttribute("message","名字错误");
        }else {
            model.addAttribute("age", userCustom.getUserID());
        }
        return "index";
    }

    @RequestMapping(value = "getIdCardIfo")
    public @ResponseBody
    Message getIdCardIfo(String imageUrl) throws Exception{
        CardOperate cardOperate = new CardOperate(apiKey,apiSecret);
        Response response = cardOperate.ocrIDcard(imageUrl,null,"",1);
        String s = new String(response.getContent());
        JSONObject jsonArray = JSONObject.parseObject(s);
        //JSONArray jsonArray = JSONArray.toJSONString(unicodeToString(s.replaceAll("/","")));
        return new Message("1","成功",jsonArray);
    }

    public static java.lang.String unicodeToString(java.lang.String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch+"" );
        }
        return str;
    }
}
