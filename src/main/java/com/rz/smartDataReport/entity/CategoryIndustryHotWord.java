package com.rz.smartDataReport.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2022-06-06
 */
@Getter
@Setter
@TableName("category_industry_hot_word")
@ApiModel(value = "CategoryIndustryHotWord对象", description = "")
public class CategoryIndustryHotWord implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Integer sort;

    private Date createTime;

    private Date updateTime;

    private Integer categoryId;


}
