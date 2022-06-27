package com.rz.smartDataReport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.common.ResultEntity;
import com.rz.smartDataReport.common.ResultEntityList;
import com.rz.smartDataReport.entity.MonitoringReachingStandardCount;
import com.rz.smartDataReport.service.IMonitoringReachingStandardCountService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 监控达标率 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-06-18
 */
@RestController
@RequestMapping("/monitoringReachingStandardCount")
@Slf4j
@Api("监控达标")
public class MonitoringReachingStandardCountController {

    @Resource
    private IMonitoringReachingStandardCountService iMonitoringReachingStandardCountService;

    @GetMapping("/getSelfTodayMonitoring")
    public ResultEntity<MonitoringReachingStandardCount> getSelfTodayMonitoring(@RequestParam int categoryId) {

        MonitoringReachingStandardCount res = null;
        try {
            res = iMonitoringReachingStandardCountService.getSelfTodayMonitoring(categoryId);

        } catch (Exception e) {

        }

        return new ResultEntity(200, res, "获取成功");
    }

    @GetMapping("/getWeekMonitoring")
    public ResultEntityList<MonitoringReachingStandardCount> getWeekMonitoring(@RequestParam int categoryId) {

        List<MonitoringReachingStandardCount> res = null;
        try {
            res = iMonitoringReachingStandardCountService.getWeekMonitoring(categoryId);

        } catch (Exception e) {

        }
        return new ResultEntityList(200, res, "获取成功");
    }


}
