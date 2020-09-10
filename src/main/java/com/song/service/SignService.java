package com.song.service;

import com.song.pojo.Login;
import com.song.pojo.PlanStu;
import com.song.pojo.User;
import com.song.pojo.vo.LoginVo;
import com.song.pojo.vo.SinginVo;
import com.song.util.JsonUtils;
import com.song.util.Mycall;
import com.song.util.NetworkApi;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 宋LS
 * @version 1.0
 * @date 2020/9/6 16:31
 */
@Service
public class SignService {
    private static String uri = "https://api.moguding.net:9000";

    public String getPlan(LoginVo login) throws InterruptedException {

        final String[] plan = new String[1];
        String loginurl = uri + "/session/user/v1/login";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss"); //使用了默认的格式创建了一个日期格式化对象。

        System.out.println(dateFormat.format(new Date()) + "   开始登录 登录信息为：" + login);
//String plan
        NetworkApi.request(JsonUtils.serialize(login), loginurl, "", new Mycall() {
            public void success(String json) {
                String token;
                System.out.println(dateFormat.format(new Date()) + "  登录成功：" + json);
                User parse = JsonUtils.parse(json, User.class);

                if (parse != null) {
                    token = parse.getData().getToken();
//                    获取任务id
                    String planurl = uri + "/practice/plan/v1/getPlanByStu";
                    NetworkApi.request("{\"state\":\"\"}", planurl, token, new Mycall() {
                        public void success(String json) {
                            System.out.println(dateFormat.format(new Date()) + "  获取任务列表：" + json);
                            PlanStu planStu = JsonUtils.parse(json, PlanStu.class);
                            String planId = planStu.getData().get(0).getPlanId();
                            plan[0] = planId;
                            System.out.println("planId = " + planId);
                        }
                    });
                }

            }
        });
        Thread.sleep(5000);
        return plan[0];
    }
}
