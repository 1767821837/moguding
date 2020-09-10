package com.song.controller;

import com.song.dao.LoginMapper;
import com.song.job.MyCronJob;
import com.song.job.MyJob;
import com.song.job.SchedulerManager;
import com.song.pojo.Login;
import com.song.pojo.vo.LoginVo;
import com.song.pojo.vo.SinginVo;
import com.song.service.SignService;
import com.song.service.Userservice;
import com.song.util.SigninUtil;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/quartz")
public class QuartzController {
    @Autowired
    public SchedulerManager myScheduler;


    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private SigninUtil signinUtil;
    @Autowired
    private Userservice userservice;
    @Autowired
    private SignService signService;

    @RequestMapping(value = "/job2", method = RequestMethod.GET)
    @ResponseBody
    public String scheduleJob2() {
//        "20 10 8,17 * * ?"
        try {
            myScheduler.startJob("25 01 18 * * ?", "job2", "group2", MyCronJob.class);//每五秒执行一次
            myScheduler.startJob("25 51 8 * * ?", "job1", "group1", MyJob.class);//每五秒执行一次
            return "启动定时器成功";
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "启动定时器失败";
    }

    @RequestMapping(value = "/del_job2", method = RequestMethod.GET)
    @ResponseBody
    public String deleteScheduleJob2() {
        try {
            myScheduler.deleteJob("job2", "group2");
            return "删除定时器成功";
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "删除定时器失败";
    }

    @RequestMapping("get")
    @ResponseBody
    public void get() {
        List<Login> logins = loginMapper.selectAll();
        for (Login login : logins) {
            LoginVo loginVo = new LoginVo();
            BeanUtils.copyProperties(login, loginVo);
            loginVo.setLoginType(login.getLogintype());
            SinginVo singinVo = new SinginVo();
            BeanUtils.copyProperties(login.getSingins(), singinVo);
            signinUtil.Sign(loginVo, singinVo);
        }
    }

    @RequestMapping("add")
    @ResponseBody
    public String adduser(@RequestBody Login login) throws InterruptedException {

        userservice.addUser(login);
        return "添加成功";
    }

    @RequestMapping("index")
    public String index() {

        return "index.html";
    }

    //    执行一次签到
    @ResponseBody
    @RequestMapping("sign")
    public String sgin(String type) {
        List<Login> logins = loginMapper.selectAll();
        for (Login login : logins) {
            LoginVo loginVo = new LoginVo();
            BeanUtils.copyProperties(login, loginVo);
            SinginVo singinVo = new SinginVo();
            BeanUtils.copyProperties(login.getSingins(), singinVo);
            loginVo.setLoginType(login.getLogintype());
            SigninUtil signinUtil = new SigninUtil();
            singinVo.setType(type);
            signinUtil.Sign(loginVo, singinVo);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "成功";
    }
    @RequestMapping("planid")
    public  String getPlanId(@RequestBody Login login) throws InterruptedException {

        LoginVo loginvo = new LoginVo();
        BeanUtils.copyProperties(login,loginvo);
        loginvo.setLoginType(login.getLogintype());
        String plan = signService.getPlan(loginvo);

        return  plan;
    }

}