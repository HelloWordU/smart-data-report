package com.rz.smartDataReport.pojo.vo;

import com.rz.smartDataReport.entity.MonitoringPlantformConfig;
import lombok.Data;

import java.util.List;

@Data
public class MonitoringPlantPageDataVo {
    public Integer reachNum;
    public Integer noReachNum;
    public Integer totalNum;
    public List<Integer> zsData;
    public List<Integer> jpData;
    public List<Integer> hyData;
    public List<Integer> dbData;
    public List<String> timeData;
    public List<MonitoringPlantformConfig> plantformData;
    public String categoryName;
}
