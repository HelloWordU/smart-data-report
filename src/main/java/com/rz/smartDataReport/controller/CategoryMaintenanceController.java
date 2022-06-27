package com.rz.smartDataReport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.entity.CategoryMaintenance;
import com.rz.smartDataReport.entity.Project;
import com.rz.smartDataReport.pojo.vo.CategoryMaintenanceVo;
import com.rz.smartDataReport.service.ICategoryMaintenanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/categoryMaintenance")
@Api(value = "关键词指标设置")
public class CategoryMaintenanceController {

    @Resource
    private ICategoryMaintenanceService iCategoryMaintenanceService;

    @GetMapping("/get")
    public ResultEntity<CategoryMaintenanceVo> getCategoryMaintenance(@RequestParam int categoryId) {
        LambdaQueryWrapper<CategoryMaintenance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryMaintenance::getCategoryId, categoryId);
        CategoryMaintenance one = iCategoryMaintenanceService.getOne(wrapper);
        CategoryMaintenanceVo res = new CategoryMaintenanceVo();
        if (one != null) {
            res.setHavepublish(one.getPublishTotal());
            res.setHaveRead(one.getReadTotal());
            res.setHaveRrite(one.getReadTotal());
            res.setPublish(one.getPublishPlanTotal());
            res.setRead(one.getReadPlanTotal());
            res.setWrite(one.getWritePlanTotal());
        }
        return new ResultEntity(200, res, "获取成功");
    }

    @PostMapping("/save")
    public ResultEntity<Boolean> saveCategoryMaintenance(@RequestBody CategoryMaintenance data) {
        iCategoryMaintenanceService.saveOrUpdate(data);
//        LambdaQueryWrapper<CategoryMaintenance> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(CategoryMaintenance::getCategoryId, categoryId);
//        CategoryMaintenance one = iCategoryMaintenanceService.getOne(wrapper);
//        CategoryMaintenanceVo res = new CategoryMaintenanceVo();
//        if (one != null) {
//            res.setHavepublish(one.getPublishTotal());
//            res.setHaveRead(one.getReadTotal());
//            res.setHaveRrite(one.getReadTotal());
//            res.setPublish(one.getPublishPlanTotal());
//            res.setRead(one.getReadPlanTotal());
//            res.setWrite(one.getWritePlanTotal());
//        }
        return new ResultEntity(200, true, "保存成功");
    }

}
