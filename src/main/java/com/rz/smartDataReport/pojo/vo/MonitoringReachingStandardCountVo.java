package com.rz.smartDataReport.pojo.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MonitoringReachingStandardCountVo {
    private Integer id;
    private Integer type;
    private String typeName;
    private String monitoringDate;
    private Integer reachingStandardCount;
    private Integer categoryId;
}
