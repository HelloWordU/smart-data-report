package com.rz.smartDataReport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.common.ResultEntityList;
import com.rz.smartDataReport.entity.CategoryScreenConfig;
import com.rz.smartDataReport.entity.Project;
import com.rz.smartDataReport.entity.ProjectCategory;
import com.rz.smartDataReport.service.ICategoryScreenConfigService;
import com.rz.smartDataReport.service.IProjectCategoryService;
import com.rz.smartDataReport.service.IProjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {



    @Resource
    private IProjectCategoryService iProjectCategoryService;

    @GetMapping("/getProjectCategory")
    public ResultEntityList<ProjectCategory> getProjectCategory(@RequestParam int projectId) {
        LambdaQueryWrapper<ProjectCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectCategory::getProjectId, projectId);
        List<ProjectCategory> data = iProjectCategoryService.list(wrapper);
        return new ResultEntityList<>(200, data, "获取成功");
    }


    @PostMapping("/save")
    public ResultEntity<Boolean> save(@RequestBody ProjectCategory entity) {
        entity.setUpdateTime(new Date());
        LambdaQueryWrapper<ProjectCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectCategory::getProjectId, entity.getProjectId());
        List<ProjectCategory> data = iProjectCategoryService.list(wrapper);
        if (entity.getId() < 0 && data.size() > 9) {
            return new ResultEntity<>(-1, true, "当前项目配置的关键词已经达到最大");
        }
        iProjectCategoryService.save(entity);

        return new ResultEntity<>(200, true, "保存");
    }

    @PostMapping("/delete")
    public ResultEntity<Boolean> delete(@RequestParam Integer id) {
        iProjectCategoryService.removeById(id);
        return new ResultEntity<>(200, true, "保存");
    }





}
