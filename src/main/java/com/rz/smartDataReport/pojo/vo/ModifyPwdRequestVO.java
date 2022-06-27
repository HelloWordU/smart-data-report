package com.rz.smartDataReport.pojo.vo;

import lombok.Data;

@Data
public class ModifyPwdRequestVO {
    private String passWord;
    private String newPassWord;
    private String newPassWordConfirm;
}
