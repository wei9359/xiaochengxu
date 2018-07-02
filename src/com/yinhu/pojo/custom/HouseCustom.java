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
    //用户是否收藏标志
    private int collectType;

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

    public List<HouseImgCustom> getHouseImgListCustom() {
        return houseImgCustomList;
    }

    public void setHouseImgListCustom(List<HouseImgCustom> houseImgListCustom) {
        this.houseImgCustomList = houseImgListCustom;
    }
}
