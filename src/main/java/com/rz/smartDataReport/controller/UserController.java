package com.rz.smartDataReport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.CacheManager;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.common.ResultEntityList;
import com.rz.smartDataReport.entity.ProjectCategory;
import com.rz.smartDataReport.entity.User;
import com.rz.smartDataReport.pojo.entity.CacheEntity;
import com.rz.smartDataReport.pojo.vo.LoginVo;
import com.rz.smartDataReport.pojo.vo.ModifyPwdRequestVO;
import com.rz.smartDataReport.pojo.vo.UserVo;
import com.rz.smartDataReport.service.ICompanyService;
import com.rz.smartDataReport.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "用户")
public class UserController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private IUserService iUserService;

    @Resource
    private ICompanyService iCompanyService;

    @PostMapping("/modifyPwd")
    public ResultEntity<Boolean> modifyPwd(@RequestBody ModifyPwdRequestVO data) {

        String accessToken = request.getHeader("accessToken");
        CacheEntity cacheInfo = CacheManager.getCacheInfo(accessToken);
        if (cacheInfo == null) {
            return new ResultEntity<>(0, "当前用户获取失败,请重新登录");
        }
        if (!data.getNewPassWord().equals(data.getNewPassWordConfirm())) {
            return new ResultEntity<>(0, "两次输入的密码不一致");
        }
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getName, ((User) cacheInfo.getValue()).getName());
        userLambdaQueryWrapper.eq(User::getPassword, data.getPassWord());
        User one = iUserService.getOne(userLambdaQueryWrapper);
        if (one == null || one.getId() < 1)
            return new ResultEntity<>(-1, "用户名密码错误");
        one.setPassword(data.getNewPassWord());
        iUserService.saveOrUpdate(one);
        return new ResultEntity<>();
    }

    @GetMapping("/getCurrentUser")
    public ResultEntity<String> getCurrentUser() {
        String accessToken = request.getHeader("accessToken");
        CacheEntity cacheInfo = CacheManager.getCacheInfo(accessToken);
        if (cacheInfo == null) {
            return new ResultEntity<>(0, "当前用户获取失败,请重新登录");
        }
        return new ResultEntity<String>(200, ((User) cacheInfo.getValue()).getName(), "获取成功");
    }

    @GetMapping("/getCurrentUserInfo")
    public ResultEntity<User> getCurrentUserInfo() {
        String accessToken = request.getHeader("accessToken");
        CacheEntity cacheInfo = CacheManager.getCacheInfo(accessToken);
        if (cacheInfo == null) {
            return new ResultEntity<>(0, "当前用户获取失败,请重新登录");
        }
        return new ResultEntity<User>(200, (User) cacheInfo.getValue(), "获取成功");
    }

    @GetMapping("/get")
    public ResultEntityList<UserVo> getUser() {
        String accessToken = request.getHeader("accessToken");
        CacheEntity cacheInfo = CacheManager.getCacheInfo(accessToken);
        if (cacheInfo == null) {
            return new ResultEntityList<>(0, "当前用户获取失败,请重新登录");
        }
        List<UserVo> res =   iUserService.getAllUser();
//        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        userLambdaQueryWrapper.ne(User::getName, "admin");
//        userLambdaQueryWrapper.ne(User::getCompanyId, 0);
//        userLambdaQueryWrapper.orderBy(true, true, User::getId);
//        List<User> userRes = iUserService.list(userLambdaQueryWrapper);
//        List<UserVo> res = new ArrayList<>();
//        for (User item:userRes
//             ) {
//            UserVo vo = new UserVo();
//
//
//        }
        return new ResultEntityList<UserVo>(200, res, "获取成功");
    }


    @PostMapping("/add")
    @ApiOperation(value = "新增用户")
    public ResultEntity<Boolean> save(@RequestBody User entity) {
        if (entity.getName() == null || entity.getName().equals("")) {
            return new ResultEntity<>(0, "用户名不能为空");
        }
        if (entity.getCompanyId() <= 0) {
            return new ResultEntity<>(0, "无效的公司");
        }
        entity.setUpdateTime(new Date());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName, entity.getName());
        List<User> data = iUserService.list(wrapper);
        if (data != null && data.size() > 0) {
            return new ResultEntity<>(-1, true, "当前用户已经存在了");
        }
        entity.setPassword("123456");
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
//        LambdaQueryWrapper<User> wrapper1 = new LambdaQueryWrapper<>();
//        wrapper1.eq(ProjectCategory::getProjectId, entity.getProjectId());
//        wrapper1.eq(ProjectCategory::getName, entity.getName());
//        ProjectCategory one = iProjectCategoryService.getOne(wrapper1);
//        if (one != null)
//            entity.setId(one.getId());
        iUserService.saveOrUpdate(entity);

        return new ResultEntity<>(200, true, "操作成功");
    }
    @PostMapping("/resetPws")
    @ApiOperation(value = "重置密码")
    public ResultEntity<Boolean> resetPws(@RequestParam Integer id) {
        User byId = iUserService.getById(id);
        byId.setPassword("123456");
        byId.setUpdateTime(new Date());
        iUserService.saveOrUpdate(byId);
        return new ResultEntity<>(200, true, "操作成功");
    }
    @PostMapping("/delete")
    @ApiOperation(value = "删除用户")
    public ResultEntity<Boolean> delete(@RequestParam Integer id) {
        iUserService.removeById(id);
        return new ResultEntity<>(200, true, "操作成功");
    }

}
