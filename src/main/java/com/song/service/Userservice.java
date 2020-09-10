package com.song.service;

import com.song.pojo.Login;

import java.util.List;

public interface Userservice {
    List<Login> getLoginInfo();

    void addUser(Login login) throws InterruptedException;
}
