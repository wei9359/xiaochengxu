package com.yinhu.serviceimp;

import com.yinhu.mapper.BaseMapper;
import com.yinhu.mapper.UserMapper;
import com.yinhu.pojo.User;
import com.yinhu.pojo.custom.UserCustom;
import com.yinhu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName UserServiceImp
 * @auther 魏星
 * @DATE 2018/6/19
 */
@Service
public class UserServiceImp extends BaseServiceImpl<UserCustom> implements UserService {
    @Autowired
    public UserMapper userMapper;

    @Autowired
    public void setMapper(UserMapper mapper) {
        super.setMapper(mapper);
    }
}
