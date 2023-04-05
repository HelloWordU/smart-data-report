package com.rz.smartDataReport.controller;


import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.common.ResultEntityList;
import com.rz.smartDataReport.entity.MonitoringPlantformConfig;
import com.rz.smartDataReport.entity.MonitoringPlantformStatistic;
import com.rz.smartDataReport.pojo.vo.MonitoringPlantPageDataVo;
import com.rz.smartDataReport.pojo.vo.MonitoringPlantformStatisticVo;
import com.rz.smartDataReport.service.IMonitoringPlantformStatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 监控平台统计 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-06-18
 */
@RestController
@RequestMapping("/monitoringPlantformStatistic")
@Slf4j
@Api("监控平台统计")
public class MonitoringPlantformStatisticController {

    @Resource
    private IMonitoringPlantformStatisticService iMonitoringPlantformStatisticService;

    @GetMapping("/getByCategoryId")
    @ApiOperation(value = "获取平台达标统计")
    public ResultEntityList<MonitoringPlantformStatisticVo> getByCategoryId(@RequestParam int categoryId) {

        List<MonitoringPlantformStatisticVo> res = iMonitoringPlantformStatisticService.getByCategoryId(categoryId);
        return new ResultEntityList(200, res, "获取成功");
    }

    @GetMapping("/getPageDataByCategoryId")
    @ApiOperation(value = "获取核心数据页面数据折线图数据")
    public ResultEntity<MonitoringPlantPageDataVo> getPageDataByCategoryId(@RequestParam int categoryId) {

        MonitoringPlantPageDataVo res = iMonitoringPlantformStatisticService.getPageDataByCategoryId(categoryId);
        return new ResultEntity(200, res, "获取成功");
    }

    @PostMapping("/save")
    public ResultEntity<Boolean> save(@RequestBody MonitoringPlantformStatistic data) {

        if (data.getPlantformId() == null || data.getPlantformId() < 1) {
            return new ResultEntity(0, false, "无效的平台");
        }
        try {
            iMonitoringPlantformStatisticService.saveOrUpdate(data);
            return new ResultEntity(200, true, "保存成功");
        } catch (Exception e) {
            log.error("MonitoringPlantformStatistic 保存失败", e);
            return new ResultEntity(0, false, "保存失败");
        }

    }

}
