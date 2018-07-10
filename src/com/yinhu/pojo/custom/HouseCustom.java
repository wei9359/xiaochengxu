package com.yinhu.pojo.custom;

import com.yinhu.pojo.House;
import com.yinhu.pojo.HouseImg;

import java.util.List;

/**
 * @ClassName HouseCustom
 * @auther 魏星
 * @DATE 2018/6/20
 */
public class HouseCustom extends House {
    private  List<HouseImgCustom> houseImgCustomList;
    private  HousePartsCustom housePartsCustom;
    //房东姓名
    private  String ownerName;
    //房东电话
    private String ownerPhone;
    //用户是否收藏标志
    private int collectType;

    public List<HouseImgCustom> getHouseImgCustomList() {
        return houseImgCustomList;
    }

    public void setHouseImgCustomList(List<HouseImgCustom> houseImgCustomList) {
        this.houseImgCustomList = houseImgCustomList;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public int getCollectType() {
        return collectType;
    }

    public void setCollectType(int collectType) {
        this.collectType = collectType;
    }

    public HousePartsCustom getHousePartsCustom() {
        return housePartsCustom;
    }

    public void setHousePartsCustom(HousePartsCustom housePartsCustom) {
        this.housePartsCustom = housePartsCustom;
    }

}
