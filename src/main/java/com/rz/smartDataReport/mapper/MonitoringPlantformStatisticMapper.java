package com.rz.smartDataReport.mapper;

import com.rz.smartDataReport.entity.MonitoringPlantformStatistic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rz.smartDataReport.pojo.vo.MonitoringPlantformStatisticVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 监控平台统计 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-06-18
 */
@Mapper
public interface MonitoringPlantformStatisticMapper extends BaseMapper<MonitoringPlantformStatistic> {

    List<MonitoringPlantformStatisticVo> getByCategoryId(int categoryId);
}
