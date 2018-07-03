package com.yinhu.controller.weixin;

import com.yinhu.tools.Message;
import com.yinhu.pojo.custom.UserCustom;
import com.yinhu.service.HouseCollectService;
import com.yinhu.service.HouseService;
import com.yinhu.service.UserService;
import com.yinhu.tools.MD5;
import com.yinhu.tools.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @ClassName UserIfoController
 * @auther 魏星
 * @DATE 2018/6/20
 */
@Controller
@RequestMapping(value = "weixin")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private HouseService houseService;

    @Resource
    private HouseCollectService houseCollectService;

    private Logger logger = Logger.getLogger(UserController.class.toString());
    /**
    * @auther 魏星
    * @date   2018/6/26
    *   * @param userID
    * @return Message
    */
    @RequestMapping(value = "getUserIfo")
    public @ResponseBody Message getUserIfo(String userID) throws Exception{
        Map<Object,Object> condition = new HashMap<Object, Object>();
        if(userID!=null&&"".equals(userID)){
          UserCustom  userCustomList = (UserCustom)userService.queryOne(condition);

          if(userCustomList!=null){
              return new Message("1","访问成功",userCustomList);
          }else{
              return new Message("0","访问失败");
          }
        }else{
            return new Message("0","用户ID错误");
        }
    }
    /**
    * @auther 魏星
    * @date   2018/6/29
    *   * @param openID
    * @return Message
    */
    @RequestMapping(value = "getUserIfoByOpenID")
    public @ResponseBody Message getUserIfoByOpenID(String openID){
        if(!StringUtil.isEmpty(openID)){
            try {
                Map<Object, Object> map = new HashMap<Object, Object>();
                map.put("openID", openID);
                UserCustom userCustom = userService.queryOne(map);
                return new Message("1","成功",userCustom);
            }catch (Exception e){
                logger.info(e.toString());
                return new Message("0","服务器错误");
            }
        }else{
            logger.info("参数有误");
            return new Message("0","参数错误");
        }
    }

    /**
     * @auther 魏星
     * @date   2018/6/23
     *   * @param userName      用户名
     * @param userPassword   密码
     * @return Message
     */
    @RequestMapping(value = "login")
    public @ResponseBody Message login(String userName,String userPassword) throws Exception{
        Map<Object,Object> condition = new HashMap<Object, Object>();
        condition.put("userName",userName);
        condition.put("userPassword",userPassword);
        UserCustom userCustom = userService.queryOne(condition);
        if(userCustom==null){
            return new Message("0","用户名或密码错误");
        }else{
            return new Message("1","登录成功",userCustom);
        }
    }
    /**
     * @auther 魏星
     * @date   2018/6/23
     *   * @param phoneNum      手机号码
     * @param identifyCode   验证码
     * @return Message
     */
    @RequestMapping(value = "loginByPhone")
    public @ResponseBody Message loginByPhone(String phoneNum,String identifyCode){
        return new Message("1","登录成功");
    }
    /**
     * @auther 魏星
     * @date   2018/6/23
     *   * @param userName        用户名
     * @param userPassword     密码
     * @param userPhoneNum     电话号码
     * @param identifyCode     验证码
     * @param userAddress      用户地址
     * @param userAge          年龄
     * @param userType         用户类型  0:个人1:商家
     * @param userImg         用户头像
     * @param session
     * @return Message
     */
    @RequestMapping(value = "regist")
    public @ResponseBody Message regist(String userName,String userPassword,String userPhoneNum,String userAddress,int userAge,int userType,String userImg,HttpSession session) throws Exception{
        if(!StringUtil.isEmpty(userPhoneNum)){
            Map<Object,Object> condition = new HashMap<Object, Object>();
            if(userService.count(condition)>0){
                return new Message("2","该用户名已被注册！");
            }
            try {
                UserCustom userCustom = new UserCustom();
                userCustom.setUserAddress(userAddress);
                userCustom.setUserAge(userAge);
                userCustom.setUserName(userName);
                userCustom.setUserPassword(MD5.MD5Encode(userPassword));
                userCustom.setUserType((byte) userType);
                userCustom.setUserImg(userImg);
                userService.insert(userCustom);
                return new Message("1","注册成功",userCustom);
            }catch(Exception e){
                logger.info("服务器错误"+e);
                return new Message("0","服务器错误");
            }
        }else{
            logger.info("注册信息不完整");
            return new Message("0","注册信息不完整");
        }

    }
    /**
     * @auther 魏星
     * @date   2018/6/23
     *   * @param userPhoneNum   手机号
     * @param type            类型 1：注册 2：登录
     * @return Message
     */
    @RequestMapping(value = "getIdentifyCode")
    public @ResponseBody Message getIdentifyCode(String userPhoneNum, String type, HttpSession session){
        String identifyCode = "1234";
        return new Message("1","获取成功",identifyCode);
    }

    @RequestMapping(value = "updateUserIfo")
    public @ResponseBody Message updateUserIfo(String userName,String userPassword,String userPhoneNum,String identifyCode,String userAddress,int userAge,int userType,String userImg,String openID,HttpSession session) throws Exception{
        if(!StringUtil.isEmpty(userPhoneNum)){
            Map<Object,Object> condition = new HashMap<Object, Object>();
            condition.put("openID",openID);
            UserCustom userCustom = userService.queryOne(condition);
            if(!StringUtil.isEmpty(userName)) {
                userCustom.setUserName(userName);
            }
            if(!StringUtil.isEmpty(userPassword)) {
                userCustom.setUserPassword(userPassword);
            }
            if(!StringUtil.isEmpty(userPhoneNum)) {
                userCustom.setUserPhoneNum(userPhoneNum);
            }
            if(!StringUtil.isEmpty(userAddress)){
                userCustom.setUserAddress(userAddress);
            }

            userCustom.setUserAge(userAge);
            userCustom.setUserType((byte)userType);
            if(!StringUtil.isEmpty(userImg)) {
                userCustom.setUserImg(userImg);
            }
            userService.update(userCustom);
            return new Message("1","更新成功",userCustom);
        }else{
            return new Message("0","验证码错误");
        }
    }

}
