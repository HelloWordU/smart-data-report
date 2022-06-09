package com.rz.smartDataReport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.Captcha;
import com.rz.smartDataReport.common.CurrentLoginUserManager;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.entity.User;
import com.rz.smartDataReport.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private IUserService iUserService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/getVerificationCode")
    public ResultEntity<String> getVerificationCode() {
        return new ResultEntity<String>("");
    }

    //调取获得验证码
    //请求的时候添加一个时间戳
    //http://localhost:8080/captcha?date=1121212
    @GetMapping(value = "/captcha")
    public ResultEntity<String> imagecode(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String username) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        OutputStream os = response.getOutputStream();
        //返回验证码和图片的map
        Map<String, Object> map = Captcha.getImageCode(86, 37, os);
        String simpleCaptcha = "simpleCaptcha";
        request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
        request.getSession().setAttribute("codeTime", System.currentTimeMillis());

        try {
            ImageIO.write((BufferedImage) map.get("image"), "jpg", os);
        } catch (IOException e) {
            return new ResultEntity<String>("验证码获取失败");//BaseResult.error();
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
        return null;
    }

    /**
     * @param checkCode 前端用户输入返回的验证码
     *                  参数若需要，自行添加
     */
    @PostMapping(value = "/login")
    public ResultEntity<Boolean> login(@RequestBody String checkCode,
                                           @RequestBody String userName,
                                           @RequestBody String password) throws Exception {

        HttpSession session = request.getSession();
        // 获得验证码对象
        Object cko = session.getAttribute("simpleCaptcha");
        if (cko == null) {
            request.setAttribute("errorMsg", "请输入验证码！");
            return new ResultEntity(-1, "请输入验证码！");
        }
        String captcha = cko.toString();
        // 判断验证码输入是否正确
        //验证码创建时间
        String codeTime1 = session.getAttribute("codeTime") + "";
        System.out.println("时间戳：" + codeTime1);
        Long aLong = Long.valueOf(codeTime1);
        //当前时间
        long l = System.currentTimeMillis();
        System.out.println("当前时间戳：" + l);
        long l1 = l - aLong;
        // 验证码有效时长为1分钟
        if (l1 / 1000 / 60 > 1) {

            request.setAttribute("errorMsg", "验证码已失效，请重新输入！");
            return new ResultEntity(-1, "验证码已失效，请重新输入！");
        }


        if (StringUtils.isEmpty(checkCode) || captcha == null || !(checkCode.equalsIgnoreCase(captcha))) {
            request.setAttribute("errorMsg", "验证码错误！");
            return new ResultEntity(-1, "验证码错误，请重新输入！");

            // 验证码有效时长为1分钟
//            long l = System.currentTimeMillis();
//            String codeTime1 = session.getAttribute("codeTime") + "";
//            Long aLong = Long.valueOf(codeTime1);

//            if((time- aLong) / 1000 / 60 > 1){
//                request.setAttribute("errorMsg", "验证码已失效，请重新输入！");
//                return "验证码已失效，请重新输入！";
//            }

        } else {
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getName, userName);
            userLambdaQueryWrapper.eq(User::getPassword, password);
            User one = iUserService.getOne(userLambdaQueryWrapper);
            if (one == null || one.getId() < 1)
                return new ResultEntity<>(-1, "用户名密码错误");
            // 在这里可以处理自己需要的事务，比如验证登陆等
            request.getSession().setAttribute("userId", one.getId());
            CurrentLoginUserManager.currentLoginUser.put(one.getId(), one);
            return new ResultEntity(200, "验证通过！");
        }
    }
}
