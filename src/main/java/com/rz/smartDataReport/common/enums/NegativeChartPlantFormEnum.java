package com.rz.smartDataReport.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum NegativeChartPlantFormEnum {
    网易(1, "网易"),
    头条(2, "头条"),
    腾讯(3, "腾讯"),
    新浪(4, "新浪"),
    搜狐(5, "搜狐"),
    凤凰网(6, "凤凰网");
    private int code;
    private String value;
    NegativeChartPlantFormEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // 方式一、每次取枚举用for循环遍历
    public static NegativeChartPlantFormEnum getByCode(int code) {
        for (NegativeChartPlantFormEnum it : NegativeChartPlantFormEnum.values()) {
            if (it.getCode() == code) {
                return it;
            }
        }
        return null;
    }
    // 方式二、放入map中，通过键取值
    private static Map<Integer,NegativeChartPlantFormEnum > zyMap = new HashMap<>();
    static {
        for (NegativeChartPlantFormEnum value : NegativeChartPlantFormEnum .values()) {
            zyMap.put(value.getCode(),value);
        }
    }
    public static NegativeChartPlantFormEnum getByCode(Integer code){
        return zyMap.get(code);
    }
}
