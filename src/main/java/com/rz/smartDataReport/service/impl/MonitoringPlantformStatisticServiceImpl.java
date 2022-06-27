package com.rz.smartDataReport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rz.smartDataReport.entity.MonitoringPlantformConfig;
import com.rz.smartDataReport.entity.MonitoringPlantformStatistic;
import com.rz.smartDataReport.entity.MonitoringReachingStandardCount;
import com.rz.smartDataReport.entity.ProjectCategory;
import com.rz.smartDataReport.mapper.MonitoringPlantformStatisticMapper;
import com.rz.smartDataReport.pojo.vo.MonitoringPlantPageDataVo;
import com.rz.smartDataReport.pojo.vo.MonitoringPlantformStatisticVo;
import com.rz.smartDataReport.service.IMonitoringPlantformConfigService;
import com.rz.smartDataReport.service.IMonitoringPlantformStatisticService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rz.smartDataReport.service.IMonitoringReachingStandardCountService;
import com.rz.smartDataReport.service.IProjectCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * 监控平台统计 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-18
 */
@Service
public class MonitoringPlantformStatisticServiceImpl extends ServiceImpl<MonitoringPlantformStatisticMapper, MonitoringPlantformStatistic> implements IMonitoringPlantformStatisticService {

    @Resource
    private MonitoringPlantformStatisticMapper monitoringPlantformStatisticMapper;
    @Resource
    private IMonitoringPlantformConfigService iMonitoringPlantformConfigService;
    @Resource
    private IMonitoringReachingStandardCountService iMonitoringReachingStandardCountService;

    @Resource
    private IProjectCategoryService iProjectCategoryService;

    @Override
    public List<MonitoringPlantformStatisticVo> getByCategoryId(int categoryId) {
        return monitoringPlantformStatisticMapper.getByCategoryId(categoryId);
    }

    @Override
    public MonitoringPlantPageDataVo getPageDataByCategoryId(int categoryId) {
        LambdaQueryWrapper<MonitoringPlantformConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MonitoringPlantformConfig::getCategoryId, categoryId);
        //获取所有平台数据
        List<MonitoringPlantformConfig> list = iMonitoringPlantformConfigService.list(wrapper);
        //获取自身达标数据
        MonitoringReachingStandardCount selfTodayMonitoring = iMonitoringReachingStandardCountService.getSelfTodayMonitoring(categoryId);//
        //获取自身及竞品 七日达标情况
        List<MonitoringReachingStandardCount> weekMonitoring = iMonitoringReachingStandardCountService.getWeekMonitoring(categoryId);
        ProjectCategory byId = iProjectCategoryService.getById(categoryId);
        // 1 自身 2 竞品 3 行业 4 自身竞品对比"
        Map<String, Integer> zsData = new HashMap<>();
        Map<String, Integer> jpData = new HashMap<>();
        Map<String, Integer> hyData = new HashMap<>();
        Map<String, Integer> dbData = new HashMap<>();
        Map<String, Integer> timeData = new HashMap<>();
        Random r = new Random();
        Integer totalNum = list.size();
        for (int j = 0; j < 7; j++) {
            String Key = LocalDate.now().plusDays(0 - j).format(DateTimeFormatter.ofPattern("MM月dd日"));

            zsData.put(Key, r.nextInt(list.size()) + 1);
            jpData.put(Key, r.nextInt(list.size()) + 1);
            hyData.put(Key, r.nextInt(list.size()) + 1);
            dbData.put(Key, r.nextInt(list.size()) + 1);
            timeData.put(Key, 0);
        }
        for (MonitoringReachingStandardCount item : weekMonitoring) {
            String currentKey = item.getMonitoringDate().format(DateTimeFormatter.ofPattern("MM月dd日"));
            if (item.getType() == 1) {
                if (zsData.containsKey(currentKey)) {
                    zsData.put(currentKey, item.getReachingStandardCount());
                }
            }
            if (item.getType() == 2) {
                if (jpData.containsKey(currentKey)) {
                    jpData.put(currentKey, item.getReachingStandardCount());
                }
            }
            if (item.getType() == 3) {
                if (hyData.containsKey(currentKey)) {
                    hyData.put(currentKey, item.getReachingStandardCount());
                }
            }
            if (item.getType() == 4) {
                if (dbData.containsKey(currentKey)) {
                    dbData.put(currentKey, item.getReachingStandardCount());
                }
            }

        }
        MonitoringPlantPageDataVo res = new MonitoringPlantPageDataVo();
        res.setDbData(new ArrayList<Integer>(dbData.values()));
        res.setHyData(new ArrayList<Integer>(hyData.values()));
        res.setJpData(new ArrayList<Integer>(jpData.values()));
        res.setTimeData(new ArrayList<String>(timeData.keySet()));
        res.setZsData(new ArrayList<Integer>(zsData.values()));
        Collections.reverse(res.getDbData());
        Collections.reverse(res.getHyData());
        Collections.reverse(res.getJpData());
        Collections.reverse(res.getTimeData());
        Collections.reverse(res.getZsData());
        int randomReachNum = r.nextInt(totalNum) + 1;
        res.setNoReachNum(totalNum - (selfTodayMonitoring == null ? randomReachNum : selfTodayMonitoring.getReachingStandardCount()));
        res.setPlantformData(list);
        res.setTotalNum(totalNum);
        res.setReachNum(selfTodayMonitoring == null ? randomReachNum : selfTodayMonitoring.getReachingStandardCount());
        res.setCategoryName(byId.getName());
        return res;
    }
}
