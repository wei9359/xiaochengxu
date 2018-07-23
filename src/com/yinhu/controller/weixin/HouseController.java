package com.yinhu.controller.weixin;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yinhu.pojo.House;
import com.yinhu.pojo.HouseParts;
import com.yinhu.tools.*;
import com.yinhu.pojo.custom.*;
import com.yinhu.service.*;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
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
                    houseCustom.setHouseImgCustomList(houseImgCustomList);
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
                UserCustom userCustom = userService.queryById(houseCustom.getUserID());
                if(userCustom!=null){
                    houseCustom.setOwnerName(userCustom.getUserName());
                    houseCustom.setOwnerPhone(userCustom.getUserPhoneNum());
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
    //获取收藏
    @RequestMapping(value = "getCollection")
    public @ResponseBody Message getCollection(String userID){
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
    public @ResponseBody Message getHouseIfo(String province,String city,String county) throws Exception{
        if(!StringUtil.isEmpty(province) && !StringUtil.isEmpty(city) && !StringUtil.isEmpty(county)) {
            try {
                Map<Object, Object> condition = new HashMap<Object, Object>();
                condition.put("province",province);
                condition.put("city",city);
                condition.put("county",county);
                condition.put("useType",1);
                condition.put("isTop",1);
                List<HouseCustom> houseCustomList = houseService.queryList(condition);
                List<HouseCustom> houseCustomList1 = new ArrayList<HouseCustom>();
                long time;
                Date date = new Date();
                SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                for(HouseCustom houseCustom:houseCustomList){
                    time = s.parse(houseCustom.getTopTime()).getTime();
                    if (date.getTime() - time >= houseCustom.getTopday() * 24 * 60 * 60 * 1000) {
                        houseCustom.setIsTop((byte) 0);
                        houseService.update(houseCustom);
                    }else{
                        houseCustomList1.add(houseCustom);
                    }
                }
                if (houseCustomList != null) {
                    return new Message("1", "访问成功", houseCustomList1);
                } else {
                    return new Message("0", "获取失败");
                }
            }catch (Exception e){
                e.printStackTrace();
                return new Message("0","首页房屋信息获取-服务器错误");
            }
        }else{
            return new Message("0","参数错误");
        }
    }

    @RequestMapping(value = "getHouseByMzType")
    public @ResponseBody Message getHouseByMzType(String province,String city,String county,Integer mzType,Integer beginRow){
        if(!StringUtil.isEmpty(province) && !StringUtil.isEmpty(city) && !StringUtil.isEmpty(county) && mzType!=null&&beginRow!=null) {
            try {
                Map<Object, Object> condition = new HashMap<Object, Object>();
                condition.put("province",province);
                condition.put("city",city);
                condition.put("county",county);
                condition.put("useType",1);
                condition.put("mztype",mzType);
                //List<HouseCustom> houseCustomList = houseService.queryList(condition);
                Pager<HouseCustom> pager = houseService.queryPager(condition,beginRow,ConfigUtils.pageSize,"createDate",ConfigUtils.DESC,null);
                if (pager != null) {
                    return new Message("1", "访问成功", pager);
                } else {
                    return new Message("0", "获取失败");
                }
            }catch (Exception e){
                e.printStackTrace();
                return new Message("0","首页房屋信息获取-服务器错误");
            }
        }else{
            return new Message("0","参数错误");
        }
    }
    /**
    * @gongneng  通过首页的参数查询房屋信息
    * @auther 魏星
    * @date   2018/7/9    20:54
    * @param  mztype 房屋类型 0:整租,1:合租,2:售房
    houseLayout     房屋格局 一室一 厅
    housekf         看房要求
    housezx         房屋装修
    * @return com.yinhu.tools.Message
    */
    @RequestMapping(value = "getHouseIfoByCondition")
        public @ResponseBody Message getHouseIfoByCondition(Integer mztype,String houseLayout,String housekf,Integer housecw,Integer zffkfs,Integer houseType,Integer ZFPrice,Integer cqxx){
        try {
            Map<Object, Object> condition = new HashMap<Object, Object>();
            if (mztype != null) {
                condition.put("mztype", mztype);
                if(mztype == 2){
                    if(zffkfs!=null&&zffkfs!=0) {
                        condition.put("mffkfs", zffkfs-1);
                    }
                    if(ZFPrice !=null && ZFPrice != 0) {
                      if(ZFPrice == 1){
                          condition.put("price1",0);
                          condition.put("price2",200000);
                      }else if(ZFPrice == 2){
                          condition.put("price1",200000);
                          condition.put("price2",400000);
                      }else if(ZFPrice == 3){
                          condition.put("price1",400000);
                          condition.put("price2",1000000);
                      }else{
                          condition.put("price1",1000000);
                      }
                    }
                }else if(mztype == 1){
                    if(zffkfs!=null&&zffkfs!=0) {
                        condition.put("zffkfs", zffkfs - 1);
                    }
                    if(ZFPrice !=null && ZFPrice != 0){
                        if(ZFPrice==1){
                            condition.put("price1",0);
                            condition.put("price2",500);
                        }else if(ZFPrice==2){
                            condition.put("price1",500);
                            condition.put("price2",1000);
                        }else if(ZFPrice==3){
                            condition.put("price1",1000);
                            condition.put("price2",2000);
                        }else{
                            condition.put("price1",2000);
                        }
                    }
                }else if(mztype == 0){
                    if(zffkfs!=null&&zffkfs!=0) {
                        condition.put("zffkfs", zffkfs - 1);
                    }
                    if(ZFPrice !=null && ZFPrice != 0){
                        if(ZFPrice==1){
                            condition.put("price1",0);
                            condition.put("price2",10000);
                        }else if(ZFPrice==2){
                            condition.put("price1",10000);
                            condition.put("price2",20000);
                        }else if(ZFPrice==3){
                            condition.put("price1",20000);
                            condition.put("price2",50000);
                        }else if(ZFPrice==4){
                            condition.put("price1",50000);
                            condition.put("price2",100000);
                        }else{
                            condition.put("price1",100000);
                        }
                    }
                }

            }
            System.out.println(!StringUtil.isEmpty(houseLayout));
            if (!StringUtil.isEmpty(houseLayout)&&!houseLayout.equals("全部")) {
                condition.put("houseLayout", houseLayout);
            }
            if (!StringUtil.isEmpty(housekf)&&!housekf.equals("全部")) {
                condition.put("housekf", housekf);
            }
//            if (!StringUtil.isEmpty(housezx)) {
//                condition.put("housezx", housezx);
//            }
            if(housecw!=null&&housecw!=0){
                condition.put("housecw",housecw-1);
            }
            if(houseType!=null&&houseType!=0){
                condition.put("housetype",houseType-1);
            }
           List<HouseCustom> houseCustomList = houseService.queryList(condition);
            if(houseCustomList!=null && houseCustomList.size()!=0){
                return new Message("1","成功",houseCustomList);
            }else{
                return new Message("0","没有符合的房屋");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("房屋信息查询-服务器错误");
            return new Message("0","房屋信息查询-服务器错误");
        }

    }
    /**
    * @gongneng   获取给定位置三公里内的所有房源
    * @auther 魏星
    * @date   2018/7/10    16:28
    * @param  longitude   精度
    latitude   维度
    * @return com.yinhu.tools.Message
    */
    @RequestMapping(value = "getHouseListInArea")
    public @ResponseBody Message getHouseListInArea(Double longitude,Double latitude,String city,String district,Integer area,Integer mztype,String houseLayout,String housekf,Integer housecw,Integer zffkfs,Integer housetype,Integer cqxx){
        if(longitude!=null&&latitude!=null&&!StringUtil.isEmpty(city)&&!StringUtil.isEmpty(district)&&area!=null&&mztype!=null){
            try {
                Map<Object, Object> condition = new HashMap<Object, Object>();
                condition.put("city", city);
                condition.put("district", district);
                condition.put("mztype",mztype);
                System.out.println(!houseLayout.equals("全部"));
                if(!StringUtil.isEmpty(houseLayout)&&!houseLayout.equals("全部")) {
                    condition.put("houseLayout", houseLayout);
                }
                if(!StringUtil.isEmpty(housekf)&&!housekf.equals("全部")) {
                    condition.put("housekf",housekf);
                }
                if(housecw!=null&&housecw!=0){
                    condition.put("housecw",housecw-1);
                }
                if(zffkfs!=null&&zffkfs!=0){
                    if(mztype==2){
                        condition.put("mffkfs",zffkfs-1);
                    }else{
                        condition.put("zffkfs",zffkfs-1);
                    }
                }
                if(housetype!=null&&housetype!=0){
                    condition.put("housetype",housetype-1);
                }
                if(cqxx!=null&&cqxx!=0){
                    condition.put("cqxx",cqxx);
                }
                List<HouseCustom> houseCustomList = houseService.queryList(condition);
                List<HouseCustom> houseCustomList1 = new ArrayList<HouseCustom>();
                if(houseCustomList!=null){
                    for(HouseCustom houseCustom:houseCustomList){
                       Double distance =  Distance.getDistanceByZuoBiao(longitude,latitude,houseCustom.getLongitude(),houseCustom.getLatitude());
                       if(distance<area){
                           houseCustomList1.add(houseCustom);
                       }
                    }
                    return new Message("1","成功",houseCustomList1);
                }else{
                    return new Message("0","当前区域没有房源");
                }
            }catch (Exception e){
                e.printStackTrace();
                return new Message("0","服务器错误");
            }
        }else{
            return new Message("0","参数错误");
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
    public @ResponseBody Message addHouse(String userID, String houseParts, int price, int mztype, String houseDescribe, int houseSize, String houseLocal, String houseImgs,String detailImages, String houseLayout, String housezx, String houselc, int housecw, String housekf, int zffkfs, int mffkfs, double longitude, double latitude, int BDType, String province, String city, String county){
        logger.info("添加房屋信息");
        if(!StringUtil.isEmpty(userID) && !StringUtil.isEmpty(houseDescribe) && !StringUtil.isEmpty(houseLocal) && !StringUtil.isEmpty(houseImgs) && !StringUtil.isEmpty(houseLayout) && !StringUtil.isEmpty(housezx) && !StringUtil.isEmpty(houselc) && !StringUtil.isEmpty(housekf) && !StringUtil.isEmpty(province) && !StringUtil.isEmpty(city) && !StringUtil.isEmpty(county)){
            try {
                JSONArray houseImgJsonArray = JSONArray.parseArray(detailImages);
                //插入房屋信息
                HouseCustom houseCustom = new HouseCustom();
                houseCustom.setUserID(userID);
                houseCustom.setPrice(price);
                houseCustom.setMztype(mztype);
                houseCustom.setHouseDescribe(houseDescribe);
                houseCustom.setHouseSize(houseSize);
                houseCustom.setHouseLocal(houseLocal);
                houseCustom.setHouseImg(houseImgs);
                houseCustom.setHouseLayout(houseLayout);
                houseCustom.setHousezx(housezx);
                houseCustom.setHouselc(houselc);
                houseCustom.setHousecw(housecw);
                houseCustom.setHousekf(housekf);
                houseCustom.setZffkfs(zffkfs);
                houseCustom.setMffkfs(mffkfs);
                houseCustom.setLongitude(longitude);
                houseCustom.setLatitude(latitude);
                houseCustom.setBDType(BDType);
                houseCustom.setProvince(province);
                houseCustom.setCity(city);
                houseCustom.setCounty(county);
                houseService.insert(houseCustom);
                //插入房屋家具
                List<Map<String,String>> housePartsListMap = JSONTools.stringToListMap(houseParts);
                HousePartsCustom housePartsCustom = new HousePartsCustom();
                Map<Object,Object> condition = new HashMap<Object, Object>();
                condition.put("houseImg",houseCustom.getHouseImg());
                HouseCustom houseCustom1 = houseService.queryOne(condition);
                if(houseCustom1!=null) {
                    for (int i = 0; i < housePartsListMap.size(); i++) {
                        if (housePartsListMap.get(i).get("value").equals("1")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setWifi(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("2")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setBed(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("3")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setArmoire(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("4")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setSofa(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("5")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setWashing_machine(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("6")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setAir_conditioner(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("7")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setDesk_chair(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("8")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setTv(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("9")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setCentral_heating(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("10")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setWater_heat(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("11")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setSmoke_machine(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("12")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setElectromagnetic_oven(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("13")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setMicrowave_oven(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("14")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setToilet(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("15")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setHotplate(1);
                            }
                        } else if (housePartsListMap.get(i).get("value").equals("16")) {
                            if (housePartsListMap.get(i).get("checked").equals("true")) {
                                housePartsCustom.setBalcony(1);
                            }
                        }

                    }
                    housePartsCustom.setHouseID(houseCustom1.getHouseID());
                }else{
                    logger.info("插入失败");
                    return new Message("0","插入失败");
                }
                housePartsService.insert(housePartsCustom);
                //插入房屋照片
                if(houseCustom1!=null) {
                    for (int i = 0 ; i < houseImgJsonArray.size();i++) {
                        HouseImgCustom houseImgCustom = new HouseImgCustom();
                        houseImgCustom.setHouseImgUrl((String)houseImgJsonArray.get(i));
                        houseImgCustom.setHouseID(houseCustom1.getHouseID());
                        houseImgService.insert(houseImgCustom);
                    }
                }else{
                    return new Message("0","插入错误");
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
    /**
    * @gongneng  获取当前user发布的所有房屋
    * @auther 魏星
    * @date   2018/7/7    9:05
    * @param  userID
    * @return com.yinhu.tools.Message
    */
    @RequestMapping(value = "myHouse")
    public @ResponseBody Message myHouse(String userID){
        if(!StringUtil.isEmpty(userID)){
            try{
                Map<Object,Object> condition = new HashMap<Object, Object>();
                condition.put("userID",userID);
                List<HouseCustom> houseCustomList = houseService.queryList(condition);
                return new Message("1","获取成功",houseCustomList);
            }catch (Exception e){
                e.printStackTrace();
                logger.info("我的房子-服务器错误");
                return new Message("0","我的房子-服务器错误");
            }
        }else{
            logger.info("参数有错");
            return new Message("0","参数有错");
        }
    }
    /**
    * @gongneng   房屋下架
    * @auther 魏星
    * @date   2018/7/15    14:33
    * @param  houseID
    * @return com.yinhu.tools.Message
    */
    @RequestMapping(value = "closeHouse")
    public @ResponseBody Message closeHouse(String houseID){
        if(!StringUtil.isEmpty(houseID)){
            try {
                HouseCustom houseCustom = houseService.queryById(houseID);
                if(houseCustom!=null) {
                    houseCustom.setUseType(0);
                    houseCustom.setCreateDate(TimeHelp.getCurrentTimeZhi());
                    houseService.update(houseCustom);
                    return new Message("1", "房屋下架成功");
                }else{
                    return new Message("0","参数错误");
                }
            }catch (Exception e){
                e.printStackTrace();
                return new Message("0","房屋下架-服务器错误");
            }
        }else{
            return new Message("0","参数错误");
        }
    }
    /**
    * @gongneng   房屋上架
    * @auther 魏星
    * @date   2018/7/15    14:37
    * @param  houseID
    * @return com.yinhu.tools.Message
    */
    @RequestMapping(value = "openHouse")
    public @ResponseBody Message openHouse(String houseID){
        if(!StringUtil.isEmpty(houseID)){
            try {
                HouseCustom houseCustom = houseService.queryById(houseID);
                if(houseCustom!=null) {
                    houseCustom.setUseType(1);
                    houseCustom.setCreateDate(TimeHelp.getCurrentTimeZhi());
                    houseService.update(houseCustom);
                    return new Message("1", "房屋上架成功");
                }else{
                    return new Message("0","参数错误");
                }
            }catch (Exception e){
                e.printStackTrace();
                return new Message("0","房屋上架-服务器错误");
            }
        }else{
            return new Message("0","参数错误");
        }
    }
}
