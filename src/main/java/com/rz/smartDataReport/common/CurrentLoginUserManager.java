package com.rz.smartDataReport.common;

import com.rz.smartDataReport.entity.User;

import java.util.concurrent.ConcurrentHashMap;

public class CurrentLoginUserManager {

    public  static ConcurrentHashMap<Integer, User> currentLoginUser = new ConcurrentHashMap<>();
}
