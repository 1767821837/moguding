package com.song.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    private String id;

    private String password;

    private String phone;

    private String logintype;

    private String uuid;

    private Boolean type;
    private Singin singins;

}