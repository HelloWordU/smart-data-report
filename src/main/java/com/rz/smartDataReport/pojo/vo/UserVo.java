package com.rz.smartDataReport.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVo {
    private Integer id;

    private String name;

    private String password;

    private Date createTime;

    private Date updateTime;

    private Integer companyId;

    private String companyName;
}
