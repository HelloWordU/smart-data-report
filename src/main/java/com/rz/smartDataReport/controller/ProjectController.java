package com.rz.smartDataReport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.common.ResultEntityList;
import com.rz.smartDataReport.entity.Project;
import com.rz.smartDataReport.entity.ProjectCategory;
import com.rz.smartDataReport.service.IProjectCategoryService;
import com.rz.smartDataReport.service.IProjectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private IProjectService iProjectService;


    @Resource
    private IProjectCategoryService iProjectCategoryService;

    @GetMapping("/getProjectByCompany")
    public ResultEntity<Project> getProjectByCompany(@RequestParam int companyId) {
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getCompanyId, companyId);
        Project one = iProjectService.getOne(wrapper);
        return new ResultEntity<>(200, one, "获取成功");
    }


    @PostMapping("/save")
    public ResultEntity<Boolean> save(@RequestBody Project entity) {
        iProjectService.save(entity);
        return new ResultEntity<>(200, true, "保存");
    }

}
