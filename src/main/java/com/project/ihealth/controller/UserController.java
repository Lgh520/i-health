package com.project.ihealth.controller;

import com.project.ihealth.dao.UserInfoMapper;
import com.project.ihealth.dao.po.UserInfo;
import com.project.ihealth.po.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @PostMapping("/register")
    private RestResponse register(UserInfo req){
        userInfoMapper.insertSelective(req);
        return RestResponse.success(null);
    }

    @PostMapping("/login")
    private RestResponse login(UserInfo req){
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(req.getUserId());
        if (null != userInfo) {
            if (req.getPasswd().equals(userInfo.getPasswd())){
                return RestResponse.success(null);
            }else{
                return RestResponse.error(1,"密码不正确");
            }
        }else{
            return RestResponse.error(1,"账号未注册!");
        }
    }
}
