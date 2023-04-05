package com.rz.smartDataReport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.CacheManager;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.common.ResultEntityList;
import com.rz.smartDataReport.entity.CategoryHotWord;
import com.rz.smartDataReport.entity.NegativeAll;
import com.rz.smartDataReport.pojo.vo.CategoryHotWordVo;
import com.rz.smartDataReport.service.ICategoryHotWordService;
import com.rz.smartDataReport.service.INegativeAllService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/negativeAll")
@Slf4j
@Api(value = "关键词热词")
public class NegativeAllController {
    @Resource
    private ICategoryHotWordService iCategoryHotWordService;
    @Resource
    private INegativeAllService iNegativeAllService;

    @GetMapping("/getAll")
    public ResultEntityList<NegativeAll> getAll(@RequestParam int categoryId) {
        LambdaQueryWrapper<NegativeAll> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NegativeAll::getCategoryId, categoryId);
        wrapper.orderBy(true, true, NegativeAll::getTime);
        List<NegativeAll> listData = iNegativeAllService.list(wrapper);
        return new ResultEntityList(200, listData, "获取成功");
    }

    @PostMapping("/save")
    public ResultEntity<Boolean> save(@RequestBody NegativeAll data) {

        if (data.getCategoryId() == null || data.getCategoryId() < 1) {
            return new ResultEntity(0, false, "无效的关键词");
        }
//        if (data.getCreateTime() == null)
//            data.setCreateTime(new Date());
//        data.setUpdateTim(new Date());
        LambdaQueryWrapper<NegativeAll> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NegativeAll::getCategoryId, data.getCategoryId());
        wrapper.eq(NegativeAll::getTime, data.getTime());
        List<NegativeAll> list = iNegativeAllService.list(wrapper);
        if(list!=null && list.size()>0)
        {
            data.setId(list.get(0).getId());
        }
        try {
            iNegativeAllService.saveOrUpdate(data);
            Calendar c = Calendar.getInstance();
            int i = c.get(Calendar.HOUR_OF_DAY);
            String cacheKey = "negativeRate_getAll_"+i;
            CacheManager.removeCache(cacheKey);
            return new ResultEntity(200, true, "保存成功");
        } catch (Exception e) {
            log.error("NegativeAllService 保存失败", e);
            return new ResultEntity(0, false, "保存失败");
        }

    }

    @PostMapping("/delete")
    public ResultEntity<Boolean> delete(@RequestParam Integer id) {
        try {
            iNegativeAllService.removeById(id);
            Calendar c = Calendar.getInstance();
            int i = c.get(Calendar.HOUR_OF_DAY);
            String cacheKey = "negativeRate_getAll_"+i;
            CacheManager.removeCache(cacheKey);
            return new ResultEntity(200, true, "删除成功");
        } catch (Exception e) {
            log.error("NegativeAllService 删除失败", e);
            return new ResultEntity(0, false, "删除失败");
        }

    }


}
