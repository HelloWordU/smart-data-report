package com.rz.smartDataReport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.common.ResultEntityList;
import com.rz.smartDataReport.entity.CategoryScreenConfig;
import com.rz.smartDataReport.entity.ProjectCategory;
import com.rz.smartDataReport.service.ICategoryScreenConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/categoryScreenConfig")
public class CategoryScreenConfigController {

    @Resource
    private ICategoryScreenConfigService iCategoryScreenConfigService;

    @ApiOperation(value = "获取分类大屏配置", notes = "获取分类大屏配置", httpMethod = "GET")
    @GetMapping("/getCategoryScreenConfig")
    public ResultEntityList<CategoryScreenConfig> getCategoryScreenConfig(@RequestParam int categoryId) {
        LambdaQueryWrapper<CategoryScreenConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryScreenConfig::getCategoryId, categoryId);
        List<CategoryScreenConfig> data = iCategoryScreenConfigService.list(wrapper);
        return new ResultEntityList<>(200, data, "获取成功");
    }

    @PostMapping("/save")
    public ResultEntity<Boolean> save(@RequestBody CategoryScreenConfig entity) {
        iCategoryScreenConfigService.save(entity);
        return new ResultEntity<>(200, true, "保存");
    }

    @PostMapping("/delete")
    public ResultEntity<Boolean> delete(@RequestParam Integer id) {
        iCategoryScreenConfigService.removeById(id);
        return new ResultEntity<>(200, true, "保存");
    }
}
