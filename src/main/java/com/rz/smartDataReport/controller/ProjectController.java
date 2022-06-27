package com.rz.smartDataReport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.CacheManager;
import com.rz.smartDataReport.common.CurrentLoginUserManager;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.common.ResultEntityList;
import com.rz.smartDataReport.entity.Project;
import com.rz.smartDataReport.entity.ProjectCategory;
import com.rz.smartDataReport.entity.User;
import com.rz.smartDataReport.pojo.entity.CacheEntity;
import com.rz.smartDataReport.service.IProjectCategoryService;
import com.rz.smartDataReport.service.IProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/project")
@Api(tags = "项目")
public class ProjectController {

    @Resource
    private IProjectService iProjectService;

    @Resource
    private HttpServletRequest request;
    @Resource
    private IProjectCategoryService iProjectCategoryService;

    @GetMapping("/get")
    @ApiOperation(value = "获取项目")
    public ResultEntity<Project> getProject() {
        //  HttpSession session = request.getSession();
        String accessToken = request.getHeader("accessToken");
        CacheEntity cacheInfo = CacheManager.getCacheInfo(accessToken);
        if (cacheInfo == null) {
            return new ResultEntity<>(0, "当前用户获取大屏配置失败,请重新登录");
        }
        Integer companyId = ((User) cacheInfo.getValue()).getCompanyId();
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getCompanyId, companyId);
        Project one = iProjectService.getOne(wrapper);
        return new ResultEntity<>(200, one, "获取成功");
    }


    @PostMapping("/save")
    public ResultEntity<Boolean> save(@RequestBody Project entity) {
        entity.setUpdateTime(new Date());
        if (entity.getId() < 1) {
            entity.setCreateTime(new Date());
        }
        iProjectService.saveOrUpdate(entity);
        return new ResultEntity<>(200, true, "保存成功");
    }

}
