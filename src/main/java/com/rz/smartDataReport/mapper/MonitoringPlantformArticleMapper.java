package com.rz.smartDataReport.mapper;

import com.rz.smartDataReport.entity.MonitoringPlantformArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rz.smartDataReport.pojo.vo.MonitoringPlantformArticleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 监控平台文章 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-06-18
 */
@Mapper
public interface MonitoringPlantformArticleMapper extends BaseMapper<MonitoringPlantformArticle> {

    List<MonitoringPlantformArticleVo> getByPlantformId(int plantformId);

    List<MonitoringPlantformArticleVo> getByCategoryId(int categoryId);
}
