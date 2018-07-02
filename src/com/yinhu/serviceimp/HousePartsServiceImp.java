package com.yinhu.serviceimp;

import com.yinhu.mapper.HousePartsMapper;
import com.yinhu.pojo.custom.HousePartsCustom;
import com.yinhu.service.HousePartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName HousePartsServiceImp
 * @auther 魏星
 * @DATE 2018/6/21
 */
@Service
public class HousePartsServiceImp extends BaseServiceImpl<HousePartsCustom> implements HousePartsService {
    @Autowired
    private HousePartsMapper housePartsMapper;
    @Autowired
    public void setBaseMapper(HousePartsMapper mapper){
        super.setMapper(mapper);
    }
}
