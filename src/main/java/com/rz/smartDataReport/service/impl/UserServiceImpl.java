package com.rz.smartDataReport.service.impl;

import com.rz.smartDataReport.entity.User;
import com.rz.smartDataReport.mapper.UserMapper;
import com.rz.smartDataReport.pojo.vo.UserVo;
import com.rz.smartDataReport.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Resource
    private UserMapper userMapper;
    @Override
    public List<UserVo> getAllUser() {
        return userMapper.getAllUser();
    }
}
