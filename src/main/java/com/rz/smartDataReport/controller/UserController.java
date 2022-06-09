package com.rz.smartDataReport.controller;

import com.rz.smartDataReport.common.ResultEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/modifyPwd")
    public ResultEntity<Boolean> modifyPwd() {
        return new ResultEntity<>();
    }
}
