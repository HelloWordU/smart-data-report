package com.rz.smartDataReport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.common.ResultEntityList;
import com.rz.smartDataReport.entity.CategoryHotWord;
import com.rz.smartDataReport.entity.CategoryIndustryHotWord;
import com.rz.smartDataReport.pojo.vo.CategoryHotWordVo;
import com.rz.smartDataReport.service.ICategoryHotWordService;
import com.rz.smartDataReport.service.ICategoryIndustryHotWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/categoryIndustryHotWord")
@Slf4j
@Api(value = "关键词行业热词")
public class CategoryIndustryHotWordController {
    @Resource
    private ICategoryIndustryHotWordService iCategoryIndustryHotWordService;

    @GetMapping("/get")
    public ResultEntityList<CategoryHotWordVo> getCategoryIndustryHotWordList(@RequestParam int categoryId) {
        LambdaQueryWrapper<CategoryIndustryHotWord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryIndustryHotWord::getCategoryId, categoryId);
        wrapper.orderBy(true, true, CategoryIndustryHotWord::getSort);
        List<CategoryIndustryHotWord> listData = iCategoryIndustryHotWordService.list(wrapper);
        List<CategoryHotWordVo> res = new ArrayList<>();
        if (listData != null) {
            for (CategoryIndustryHotWord item : listData) {
                CategoryHotWordVo itemData = new CategoryHotWordVo();
                itemData.setTitle(item.getName());
                res.add(itemData);
            }
        }
        return new ResultEntityList(200, res, "获取成功");
    }

    @GetMapping("/admin/get")
    public ResultEntityList<CategoryIndustryHotWord> getCategoryHotWordListAdmin(@RequestParam int categoryId) {
        LambdaQueryWrapper<CategoryIndustryHotWord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryIndustryHotWord::getCategoryId, categoryId);
        wrapper.orderBy(true, true, CategoryIndustryHotWord::getSort);
        List<CategoryIndustryHotWord> listData = iCategoryIndustryHotWordService.list(wrapper);
        return new ResultEntityList(200, listData, "获取成功");
    }

    @PostMapping("/save")
    public ResultEntity<Boolean> save(@RequestBody CategoryIndustryHotWord data) {

        if (data.getCategoryId() == null || data.getCategoryId() < 1) {
            return new ResultEntity(0, false, "无效的关键词");
        }
        if (data.getCreateTime() == null)
            data.setCreateTime(new Date());
        data.setUpdateTime(new Date());
        try {
            iCategoryIndustryHotWordService.saveOrUpdate(data);
            return new ResultEntity(200, true, "保存成功");
        } catch (Exception e) {
            log.error("CategoryHotWord 保存失败", e);
            return new ResultEntity(0, false, "保存失败");
        }

    }

    @PostMapping("/delete")
    public ResultEntity<Boolean> delete(@RequestParam Integer id) {
        try {
            iCategoryIndustryHotWordService.removeById(id);
            return new ResultEntity(200, true, "删除成功");
        } catch (Exception e) {
            log.error("CategoryHotWord 删除失败", e);
            return new ResultEntity(0, false, "删除失败");
        }

    }
}
