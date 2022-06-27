package com.rz.smartDataReport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.common.ResultEntityList;
import com.rz.smartDataReport.entity.CategoryHotWord;
import com.rz.smartDataReport.entity.CategoryIndustryHotWord;
import com.rz.smartDataReport.entity.CategoryMaintenance;
import com.rz.smartDataReport.pojo.vo.CategoryHotWordVo;
import com.rz.smartDataReport.pojo.vo.CategoryMaintenanceVo;
import com.rz.smartDataReport.service.ICategoryHotWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/categoryHotWord")
@Slf4j
@Api(value = "关键词热词")
public class CategoryHotWordController {
    @Resource
    private ICategoryHotWordService iCategoryHotWordService;

    @GetMapping("/get")
    public ResultEntityList<CategoryHotWordVo> getCategoryHotWordList(@RequestParam int categoryId) {
        LambdaQueryWrapper<CategoryHotWord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryHotWord::getCategoryId, categoryId);
        wrapper.orderBy(true, true, CategoryHotWord::getSort);
        List<CategoryHotWord> listData = iCategoryHotWordService.list(wrapper);
        List<CategoryHotWordVo> res = new ArrayList<>();
        if (listData != null) {
            for (CategoryHotWord item : listData) {
                CategoryHotWordVo itemData = new CategoryHotWordVo();
                itemData.setTitle(item.getName());
                res.add(itemData);
            }
        }
        return new ResultEntityList(200, res, "获取成功");
    }

    @GetMapping("/admin/get")
    public ResultEntityList<CategoryHotWord> getCategoryHotWordListAdmin(@RequestParam int categoryId) {
        LambdaQueryWrapper<CategoryHotWord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryHotWord::getCategoryId, categoryId);
        wrapper.orderBy(true, true, CategoryHotWord::getSort);
        List<CategoryHotWord> listData = iCategoryHotWordService.list(wrapper);
        return new ResultEntityList(200, listData, "获取成功");
    }

    @PostMapping("/save")
    public ResultEntity<Boolean> save(@RequestBody CategoryHotWord data) {

        if (data.getCategoryId() == null || data.getCategoryId() < 1) {
            return new ResultEntity(0, false, "无效的关键词");
        }
        if (data.getCreateTime() == null)
            data.setCreateTime(new Date());
        data.setUpdateTim(new Date());
        try {
            iCategoryHotWordService.saveOrUpdate(data);
            return new ResultEntity(200, true, "保存成功");
        } catch (Exception e) {
            log.error("CategoryHotWord 保存失败", e);
            return new ResultEntity(0, false, "保存失败");
        }

    }

    @PostMapping("/delete")
    public ResultEntity<Boolean> delete(@RequestParam Integer id) {
        try {
            iCategoryHotWordService.removeById(id);
            return new ResultEntity(200, true, "删除成功");
        } catch (Exception e) {
            log.error("CategoryHotWord 删除失败", e);
            return new ResultEntity(0, false, "删除失败");
        }

    }


}
