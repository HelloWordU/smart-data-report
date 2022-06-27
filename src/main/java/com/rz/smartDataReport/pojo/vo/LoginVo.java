package com.rz.smartDataReport.pojo.vo;

import lombok.Data;

@Data
public class LoginVo {
    private String checkCode;
    private String userName;
    private String password;
    private String captchaToken;
}
