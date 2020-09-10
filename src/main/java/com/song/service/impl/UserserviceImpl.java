package com.song.service.impl;

import com.song.dao.LoginMapper;
import com.song.dao.SinginMapper;
import com.song.pojo.Login;
import com.song.pojo.Singin;
import com.song.pojo.vo.LoginVo;
import com.song.service.SignService;
import com.song.service.Userservice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserserviceImpl implements Userservice {

    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private SinginMapper singinMapper;

    @Autowired
    private SignService signService;

    public List<Login> getLoginInfo() {

        List<Login> logins = loginMapper.selectAll();
        if (logins == null)
            throw new ArrayIndexOutOfBoundsException();
        return logins;
    }

//    添加用户


    public void addUser(Login login) throws InterruptedException {
        String replace = UUID.randomUUID().toString().replace("-", "");


        LoginVo loginvo = new LoginVo();
        BeanUtils.copyProperties(login,loginvo);
        loginvo.setLoginType(login.getLogintype());
        String plan = signService.getPlan(loginvo);
        login.setId(replace);
        loginMapper.insert(login);
        Singin singins = login.getSingins();
        singins.setId(UUID.randomUUID().toString().replace("-", ""));
        singins.setPlanId(plan);
        singins.setLoginId(replace);
        singinMapper.insert(login.getSingins());
    }


}
