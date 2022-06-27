package com.rz.smartDataReport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.CacheManager;
import com.rz.smartDataReport.common.Captcha;
import com.rz.smartDataReport.common.CurrentLoginUserManager;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.entity.User;
import com.rz.smartDataReport.pojo.entity.CacheEntity;
import com.rz.smartDataReport.pojo.vo.CaptchaVo;
import com.rz.smartDataReport.pojo.vo.LoginVo;
import com.rz.smartDataReport.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/index")
@Api(tags = "登录页")
@Slf4j
public class IndexController {

    @Resource
    private IUserService iUserService;

    @Autowired
    private HttpServletRequest request;

    //调取获得验证码
    //请求的时候添加一个时间戳
    //http://localhost:8080/captcha?date=1121212
    @GetMapping(value = "/captcha")
    @ApiOperation(value = "验证码")
    public ResultEntity<CaptchaVo> captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        response.setDateHeader("Expires", 0);
//        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
//        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
//        response.setHeader("Pragma", "no-cache");
//        response.setContentType("image/jpeg");

        //OutputStream os = response.getOutputStream();
        //返回验证码和图片的map
        CaptchaVo res = new CaptchaVo();
        Map<String, Object> map = Captcha.getImageCode(86, 37);
        String simpleCaptcha = "simpleCaptcha";

//        request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
//        request.getSession().setAttribute("codeTime", System.currentTimeMillis());
        String cacheKey = UUID.randomUUID().toString();
        String cacheValue = map.get("strEnsure").toString().toLowerCase();
        CacheEntity cacheEntity = new CacheEntity(cacheKey, cacheValue, 60 * 1000, false);
        CacheManager.putCache(cacheKey, cacheEntity);
        try {
            BufferedImage image = (BufferedImage) map.get("image");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
            ImageIO.write(image, "png", baos);//写入流中
            byte[] bytes = baos.toByteArray();//转换成字节
            BASE64Encoder encoder = new BASE64Encoder();
            String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
            png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
            res.setCaptchaData("data:image/png;base64," + png_base64);
            res.setCaptchaAccessToken(cacheKey);
            return new ResultEntity<CaptchaVo>(res);
        } catch (IOException e) {
            return new ResultEntity<CaptchaVo>(-1, "验证码获取失败");//BaseResult.error();
        }
    }

    /**
     *
     */
    @PostMapping(value = "/login")
    @ApiOperation(value = "登录")
    public ResultEntity<String> login(@RequestBody LoginVo data) throws Exception {

        // 获得验证码对象
        CacheEntity cko = CacheManager.getCacheInfo(data.getCaptchaToken());
        if (cko == null) {
            return new ResultEntity(-1, "验证码已失效，请刷新验证码！");
        }
        String captcha = "";
        try {
            captcha = (String) cko.getValue();
        } catch (Exception e) {
            return new ResultEntity(-1, "验证码已失效，请刷新验证码！");
        }
        String checkCode = data.getCheckCode().toLowerCase();
        if (StringUtils.isEmpty(checkCode) || captcha == null || !(checkCode.equalsIgnoreCase(captcha))) {
            return new ResultEntity(-1, "验证码错误，请重新输入！");

        } else {
            String userName = data.getUserName();
            String password = data.getPassword();
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getName, userName);
            userLambdaQueryWrapper.eq(User::getPassword, password);
            User one = iUserService.getOne(userLambdaQueryWrapper);
            if (one == null || one.getId() < 1)
                return new ResultEntity<>(-1, "用户名密码错误");
            // 在这里可以处理自己需要的事务，比如验证登陆等
            // request.getSession().setAttribute("userId", one.getId());
            CurrentLoginUserManager.currentLoginUser.put(one.getId(), one);
            CacheManager.clearOnly(data.getCaptchaToken());
            String accessToken = UUID.randomUUID().toString();
            CacheManager.putCache(accessToken, new CacheEntity(accessToken, one, 0, false));
            return new ResultEntity(200, accessToken, "验证通过！");
        }

    }

    @GetMapping(value = "/loginOut")
    public ResultEntity<Boolean> loginOut() throws Exception {

        String accessToken = request.getHeader("accessToken");
        CacheEntity cacheInfo = CacheManager.getCacheInfo(accessToken);
        if (cacheInfo == null) {
            return new ResultEntity(true);
        }
        CacheManager.clearOnly(accessToken);
        // 在这里可以处理自己需要的事务，比如验证登陆等
        CurrentLoginUserManager.currentLoginUser.remove(((User) cacheInfo.getValue()).getId());
        return new ResultEntity(true);
    }
}
