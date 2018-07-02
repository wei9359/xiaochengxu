package com.yinhu.serviceimp;

import com.yinhu.mapper.HouseImgMapper;
import com.yinhu.mapper.HouseMapper;
import com.yinhu.pojo.custom.HouseCustom;
import com.yinhu.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName HouseServiceImp
 * @auther 魏星
 * @DATE 2018/6/20
 */
@Service
public class HouseServiceImp extends BaseServiceImpl<HouseCustom> implements HouseService {
    @Autowired
    public HouseImgMapper houseMapper;
    @Autowired
    public void setHouseImgMapper(HouseMapper mapper){
        super.setMapper(mapper);
    }
}
