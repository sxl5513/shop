package com.anber.service.impl;

import com.anber.bo.UserBO;
import com.anber.common.DateUtil;
import com.anber.common.MD5Utils;
import com.anber.mapper.UsersMapper;
import com.anber.pojo.Users;
import com.anber.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author Anber
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Sid sid;
    /**
     * 用户默认头像
     */
    private final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Transactional (propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public boolean queryUsernameIsExist(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        Users users = usersMapper.selectOneByExample(userExample);
        return users == null ? false : true;
    }

    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Users createUser(UserBO userBO) {
        Users user = new Users();
        user.setId(sid.nextShort());
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        user.setFace(USER_FACE);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        usersMapper.insertSelective(user);
        return user;
    }

    @Transactional (propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Users queryUserForLogin(UserBO userBO) throws Exception {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", userBO.getUsername());
        criteria.andEqualTo("password", MD5Utils.getMD5Str(userBO.getPassword()));
        return usersMapper.selectOneByExample(userExample);
    }
}
