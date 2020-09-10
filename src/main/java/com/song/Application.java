package com.song;


import com.song.pojo.Login;


import com.song.pojo.Singin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScans;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.song.dao"})

public class Application {
        public static void main(String[] args) {

        SpringApplication.run(Application.class);
    }

//    public static void main(String[] args) {
//        SpringApplication.run(Application.class);
//    }
}
