package com.rz.smartDataReport.service.impl;

import com.rz.smartDataReport.entity.MonitoringPlantformArticle;
import com.rz.smartDataReport.mapper.MonitoringPlantformArticleMapper;
import com.rz.smartDataReport.pojo.vo.MonitoringPlantformArticleVo;
import com.rz.smartDataReport.service.IMonitoringPlantformArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 监控平台文章 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-18
 */
@Service
public class MonitoringPlantformArticleServiceImpl extends ServiceImpl<MonitoringPlantformArticleMapper, MonitoringPlantformArticle> implements IMonitoringPlantformArticleService {

    @Resource
    private MonitoringPlantformArticleMapper monitoringPlantformArticleMapper;

    @Override
    public List<MonitoringPlantformArticleVo> getByPlantformId(int plantformId) {
        return monitoringPlantformArticleMapper.getByPlantformId(plantformId);
    }

    @Override
    public List<MonitoringPlantformArticleVo> getByCategoryId(int categoryId) {
        return monitoringPlantformArticleMapper.getByCategoryId(categoryId);
    }
}
