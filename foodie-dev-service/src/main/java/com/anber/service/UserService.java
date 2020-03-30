package com.anber.service;

import com.anber.bo.UserBO;
import com.anber.pojo.Users;

/**
 * @author Anber
 */
public interface UserService {
    /**
     * 查询用户名是否存在
     * @param username 用户名
     * @return 是否存在
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 用户注册
     * @param userBO 用户注册信息
     * @return 用户信息
     */
    public Users createUser(UserBO userBO);

    /**
     * 用户登录
     * @param userBO 用户登录信息
     * @return 用户信息
     */
    public Users queryUserForLogin(UserBO userBO) throws Exception;
}
