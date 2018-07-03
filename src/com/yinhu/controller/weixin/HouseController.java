package com.yinhu.controller.weixin;

import com.yinhu.pojo.HouseParts;
import com.yinhu.tools.Message;
import com.yinhu.pojo.custom.*;
import com.yinhu.service.*;
import com.yinhu.tools.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;
import java.util.logging.Logger;

/**
 * @ClassName HouseController
 * @auther 魏星
 * @DATE 2018/6/30
 */
@RequestMapping(value = "weixin")
@Controller
public class HouseController {
    @Resource
    private UserService userService;

    @Resource
    private HouseService houseService;

    @Resource
    private HouseImgService houseImgService;

    @Resource
    private HousePartsService housePartsService;

    @Resource
    private HouseCollectService houseCollectService;

    private Logger logger = Logger.getLogger(HouseController.class.toString());
    /**
     * @auther 魏星
     * @date   2018/6/28
     *   * @param houseID  房屋ID
     * @return Message
     */
    //通过houseID查询房屋信息
    @RequestMapping(value = "getHouseIfoById")
    public @ResponseBody
    Message getHouseIfoById(String houseID, String userID) throws Exception{
        Map<Object,Object> condition = new HashMap<Object, Object>();
        if (houseID!=null&&!"".equals(houseID)){
            condition.put("houseID",houseID);
            HouseCustom houseCustom = houseService.queryById(houseID);


            if(houseCustom==null){
                return new Message("0","访问失败");
            }else{
                List<HouseImgCustom> houseImgCustomList = houseImgService.queryList(condition);
                HousePartsCustom housePartsCustom = housePartsService.queryOne(condition);
                if(houseImgCustomList!=null){
                    houseCustom.setHouseImgListCustom(houseImgCustomList);
                }
                if(housePartsCustom!=null) {
                    houseCustom.setHousePartsCustom(housePartsCustom);
                }
                condition.put("userID",userID);
                HouseCollectCustom houseCollectCustom = houseCollectService.queryOne(condition);
                if(houseCollectCustom!=null){
                    houseCustom.setCollectType(1);
                }else{
                    houseCustom.setCollectType(0);
                }
                return new Message("1","访问成功",houseCustom);
            }
        }else{
            return new Message("0","用户ID有误");
        }
    }
    /**
     * @auther 魏星
     * @date   2018/6/29
     *   * @param openID
     * @return Message
     */
    //通过openID获取
    @RequestMapping(value = "getHouseIfoByUserID")
    public @ResponseBody Message getHouseIfoByUserID(String userID){
        if(userID!=null){
            try {
                Map<Object,Object> map = new HashMap<Object, Object>();
                map.put("userID",userID);
                List<HouseCollectCustom> houseCollectCustomList = houseCollectService.queryList(map);
                List<HouseCustom> houseCustomList = new ArrayList<HouseCustom>();
                for(HouseCollectCustom houseCollectCustom:houseCollectCustomList){
                    houseCustomList.add(houseService.queryById(houseCollectCustom.getHouseID()));
                }
                return new Message("1", "获取我的收藏成功", houseCustomList);

            }catch (Exception e){
                logger.info(e.toString());
                e.printStackTrace();
                return new Message("0","服务器错误");

            }
        }else{
            logger.info("参数错误");
            return new Message("0","参数错误");
        }
    }
    /**
     * @auther 魏星
     * @date   2018/6/28
     *   * @param
     * @return Message
     */
    //首页展示房屋信息
    @RequestMapping(value = "getHouseIfo")
    public @ResponseBody Message getHouseIfo() throws Exception{
        Map<Object,Object> condition = new HashMap<Object, Object>();
        List<HouseCustom> houseCustomList = houseService.queryList(condition);
        if(houseCustomList!=null){
            return new Message("1","访问成功",houseCustomList);
        }else {
            return new Message("0", "获取失败");
        }
    }
    /**
     * @auther 魏星
     * @date   2018/6/29
     *   * @param houseID
     * @param collectType
     * @return Message
     */
    //收藏房屋
    @RequestMapping(value="collect")
    public @ResponseBody Message collect(String houseID,int collectType,String openID){
        try {
            HouseCustom houseCustom = houseService.queryById(houseID);
            if(houseCustom!=null){
                Map<Object,Object> map = new HashMap<Object, Object>();
                map.put("openID",openID);
                UserCustom userCustom = userService.queryOne(map);
                if(userCustom!=null) {
                    if (collectType == 0) {
                        HouseCollectCustom houseCollectCustom = new HouseCollectCustom();
                        houseCollectCustom.setHouseID(houseID);
                        houseCollectCustom.setUserID(userCustom.getUserID());
                        houseCollectService.insert(houseCollectCustom);
                        return new Message("1", "收藏成功");
                    } else {
                        Map<Object, Object> condition = new HashMap<Object, Object>();
                        condition.put("houseID", houseID);
                        condition.put("userID", userCustom.getUserID());
                        houseCollectService.deleteByCondition(condition);
                        return new Message("1", "取消收藏成功");
                    }
                }else{
                    return new Message("0","openID有误");
                }
            }else{
                logger.info("该houseID无效");
                return new Message("0","该houseID无效");
            }
        }catch (Exception e){
            logger.info(e.toString());
            e.printStackTrace();
            return new Message("0","服务器错误");
        }
    }
    /**
    * @auther 魏星
    * @date   2018/6/30
    *   * @param userID
     * @param price                 房屋價格
     * @param mztype                租房还是卖房
     * @param houseDescribe         房屋描述
     * @param houseSize             房屋面积
     * @param houseLocal            房屋具体位置
     * @param houseImgs             房屋照片
     * @param houseLayout           房屋格局（一室一厅）
     * @param housezx               房屋装修
     * @param houselc               房屋所在楼层
     * @param housecw               房屋车位
     * @param housekf               看房要求
     * @param zffkfs                租房付款方式
     * @param mffkfs                买房付款方式
     * @param langitude             房屋经度
     * @param latitude              房屋维度
     * @param BDType                步梯还是电梯
     * @param province              房屋所在省
     * @param city                  房屋所在市
     * @param county                房屋所在区县
    * @return Message
    */
    @RequestMapping(value = "addHouse")
    public @ResponseBody Message addHouse(String userID,List<Map<String,String>> houseParts, int price, int mztype, String houseDescribe, int houseSize, String houseLocal,String[] houseImgs,String houseLayout,String housezx,String houselc,int housecw,String housekf,int zffkfs,int mffkfs,int langitude,int latitude,int BDType,String province,String city,String county){
        logger.info("添加房屋信息");
        if(!StringUtil.isEmpty(userID) && !StringUtil.isEmpty(houseDescribe) && !StringUtil.isEmpty(houseLocal) && houseImgs.length==0 && !StringUtil.isEmpty(houseLayout) && !StringUtil.isEmpty(housezx) && !StringUtil.isEmpty(houselc) && !StringUtil.isEmpty(housekf) && !StringUtil.isEmpty(province) && !StringUtil.isEmpty(city) && !StringUtil.isEmpty(county) && houseParts!=null){
            try {
                //插入房屋信息
                HouseCustom houseCustom = new HouseCustom();
                houseCustom.setUserID(userID);
                houseCustom.setPrice(price);
                houseCustom.setMztype(mztype);
                houseCustom.setHouseDescribe(houseDescribe);
                houseCustom.setHouseSize(houseSize);
                houseCustom.setHouseLocal(houseLocal);
                houseCustom.setHouseImg(houseImgs[0]);
                houseCustom.setHouseLayout(houseLayout);
                houseCustom.setHousezx(housezx);
                houseCustom.setHouselc(houselc);
                houseCustom.setHousecw(housecw);
                houseCustom.setHousekf(housekf);
                houseCustom.setZffkfs(zffkfs);
                houseCustom.setMffkfs(mffkfs);
                houseCustom.setLangitude(langitude);
                houseCustom.setLatitude(latitude);
                houseCustom.setBDType(BDType);
                houseCustom.setProvince(province);
                houseCustom.setCity(city);
                houseCustom.setCounty(county);
                //houseService.insert(houseCustom);

                //插入房屋照片
                Map<Object,Object> condition = new HashMap<Object, Object>();
                condition.put("houseImg",houseCustom.getHouseImg());
                HouseCustom houseCustom1 = houseService.queryOne(condition);
                for(String image:houseImgs) {
                    HouseImgCustom houseImgCustom = new HouseImgCustom();
                    houseImgCustom.setHouseImgUrl(image);
                    houseImgCustom.setHouseID(houseCustom1.getHouseID());
                    //houseImgService.insert(houseImgCustom);
                }

                for(int i = 0;i<houseParts.size();i++){
                    HousePartsCustom housePartsCustom = new HousePartsCustom();
                   if(houseParts.get(i).get("name").equals("WIFI")) {
                        if(houseParts.get(i).get("checked") == "true"){
                            housePartsCustom.setWifi(1);
                        }
                   }
                }
                return new Message("1","成功");

            }catch (Exception e){
                e.printStackTrace();
                logger.info(e.toString());
                return new Message("0","服务器错误");
            }
        }else{
            logger.info("房屋信息不全");
            return  new Message("0","房屋信息不全");
        }
    }
}
