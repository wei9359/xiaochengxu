package com.yinhu.serviceimp;

import com.yinhu.mapper.HouseCollectMapper;
import com.yinhu.pojo.custom.HouseCollectCustom;
import com.yinhu.service.HouseCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName HouseCollectServiceImp
 * @auther 魏星
 * @DATE 2018/6/28
 */
@Service
public class HouseCollectServiceImp extends BaseServiceImpl<HouseCollectCustom> implements HouseCollectService {
    @Autowired
    private HouseCollectMapper houseCollectMapper;
    @Autowired
    public void setHouseImgMapper(HouseCollectMapper mapper) {
        super.setMapper(mapper);
    }
}
