package com.yinhu.serviceimp;

import com.yinhu.mapper.HouseImgMapper;
import com.yinhu.pojo.custom.HouseImgCustom;
import com.yinhu.service.HouseImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName HouseImgServiceImp
 * @auther 魏星
 * @DATE 2018/6/20
 */
@Service
public class HouseImgServiceImp extends BaseServiceImpl<HouseImgCustom> implements HouseImgService {
    @Autowired
    public HouseImgMapper houseImgMapper;

    @Autowired
    public void setHouseImgMapper(HouseImgMapper houseImgMapper) {
        super.setMapper(houseImgMapper);
    }
}
