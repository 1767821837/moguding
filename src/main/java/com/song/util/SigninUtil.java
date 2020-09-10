package com.song.util;

import com.song.pojo.PlanStu;
import com.song.pojo.User;
import com.song.pojo.vo.LoginVo;
import com.song.pojo.vo.SinginVo;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class SigninUtil {

    private static String uri = "https://api.moguding.net:9000";

    public  synchronized void Sign(LoginVo login, final SinginVo singin) {
//        Login login = new Login("sky.990504", "18583316649", "android", "");
        System.out.println("login = " + login);
        String loginurl = uri + "/session/user/v1/login";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss") ; //使用了默认的格式创建了一个日期格式化对象。

        System.out.println(dateFormat.format(new Date())+"   开始登录 登录信息为：" + login);

        NetworkApi.request(JsonUtils.serialize(login), loginurl, "", new Mycall() {
            public void success(String json) {
                  String token ;
                System.out.println(dateFormat.format(new Date())+"  登录成功：" + json);
                User parse = JsonUtils.parse(json, User.class);

                if (parse != null) {
                    token = parse.getData().getToken();
                            String sign = uri + "/attendence/clock/v1/save";
                            NetworkApi.request(JsonUtils.serialize(singin), sign, token, new Mycall() {
                                public void success(String json) {
                                    System.out.println(dateFormat.format(new Date())+"  签到成功：" + json);
                                }


                            });

                }
            }
        });
    }
}
