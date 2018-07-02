package com.yinhu.controller.com.yinhu.controller.weixin;

import com.yinhu.controller.com.yinhu.tools.Message;
import com.yinhu.pojo.custom.UserCustom;
import com.yinhu.service.UserService;
import com.yinhu.tools.StringUtil;
import com.yinhu.tools.WeiXinUrils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName weixinController
 * @auther 魏星
 * @DATE 2018/6/30
 */
@RequestMapping(value = "weixin")
@Controller
public class weixinController {
    @Resource
    UserService userService;
    /**
     * @auther 魏星
     * @date   2018/6/26
     *   * @param code
     * @return com.yinhu.controller.com.yinhu.tools.Message
     */
    @RequestMapping(value = "getWeixinIfo")
    public @ResponseBody
    Message getWeixinIfo (String code) throws Exception{
        if(!StringUtil.isEmpty(code)){
            Map<Object,Object> weixinMap = WeiXinUrils.getOpenId(code);
            UserCustom userCustom = userService.queryOne(weixinMap);
            if(userCustom==null){
                userCustom = new UserCustom();
                userCustom.setOpenID((String)weixinMap.get("openID"));
                userService.insert(userCustom);
                return new Message("1","成功",userCustom);
            }else {
                return new Message("1", "成功", userCustom);
            }
        }else{
            return new Message("0","code过期");
        }

    }
}
