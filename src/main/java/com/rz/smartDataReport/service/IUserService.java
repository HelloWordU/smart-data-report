package com.rz.smartDataReport.service;

import com.rz.smartDataReport.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rz.smartDataReport.pojo.vo.UserVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-06
 */
public interface IUserService extends IService<User> {

    List<UserVo> getAllUser();
}
