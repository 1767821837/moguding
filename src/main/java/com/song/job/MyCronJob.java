package com.song.job;

import com.song.dao.LoginMapper;
import com.song.pojo.Login;
import com.song.pojo.vo.LoginVo;
import com.song.pojo.vo.SinginVo;
import com.song.util.SigninUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.*;

public class MyCronJob extends QuartzJobBean {
    @Autowired
    private LoginMapper loginMapper;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // indexController.testMail();
        List<Login> logins = loginMapper.selectAll();

        for (Login login : logins) {
            LoginVo loginVo = new LoginVo();
            BeanUtils.copyProperties(login, loginVo);
            SinginVo singinVo = new SinginVo();
            BeanUtils.copyProperties(login.getSingins(), singinVo);
            loginVo.setLoginType(login.getLogintype());
            SigninUtil signinUtil = new SigninUtil();
            Calendar calendar = Calendar.getInstance();
            int i = calendar.get(Calendar.HOUR_OF_DAY);
            singinVo.setType(i <= 12 ? "START" : "END");
            signinUtil.Sign(loginVo, singinVo);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
