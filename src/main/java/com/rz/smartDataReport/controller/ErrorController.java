package com.rz.smartDataReport.controller;

import com.rz.smartDataReport.common.ResultEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {
//    @RequestMapping("/error")
//    public ResultEntity<Boolean> error()
//    {
//        return  new ResultEntity<>(200,"未知的异常");
//    }
    @RequestMapping("/noAuthError")
    public ResultEntity<Boolean> noAuthError()
    {
        return  new ResultEntity<>(401,"用户未登录");
    }
}
