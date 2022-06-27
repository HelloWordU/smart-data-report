package com.rz.smartDataReport.service;

import com.rz.smartDataReport.entity.MonitoringPlantformArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rz.smartDataReport.pojo.vo.MonitoringPlantformArticleVo;

import java.util.List;

/**
 * <p>
 * 监控平台文章 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-18
 */
public interface IMonitoringPlantformArticleService extends IService<MonitoringPlantformArticle> {

    List<MonitoringPlantformArticleVo> getByPlantformId(int plantformId);

    List<MonitoringPlantformArticleVo> getByCategoryId(int categoryId);
}
