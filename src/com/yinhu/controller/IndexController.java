package com.yinhu.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yinhu.pojo.User;
import com.yinhu.pojo.custom.UserCustom;
import com.yinhu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @ClassName IndexController
 * @auther 魏星
 * @DATE 2018/6/19
 */
@Controller
public class IndexController {
    private static Logger logger = Logger.getLogger(IndexController.class.toString());
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
}
