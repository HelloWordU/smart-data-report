package com.rz.smartDataReport.common;

import lombok.Data;

import java.util.List;

@Data
public class ResultEntityList<T> {

    private List<T> data;
    private Integer code;
    private String message;

    public ResultEntityList() {
        this.code = 200;
    }

    public ResultEntityList(List<T> data) {
        this.code = 200;
        this.data = data;
    }

    public ResultEntityList(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultEntityList(Integer code, List<T> data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
