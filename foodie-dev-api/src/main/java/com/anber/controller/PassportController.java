package com.anber.controller;

import com.anber.bo.UserBO;
import com.anber.common.CookieUtils;
import com.anber.common.JsonResult;
import com.anber.common.JsonUtils;
import com.anber.pojo.Users;
import com.anber.service.UserService;
import com.sun.deploy.net.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Anber
 */
@Api(value = "用户注册",tags = "用户注册")
@RestController
@RequestMapping("passport")
public class PassportController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public JsonResult queryUsernameIsExist(@RequestParam String username) {
        //1. 判断用户名是否为空
        if (StringUtils.isBlank(username)) {
            return JsonResult.errorMsg("用户名为空！");
        }
        //2. 验证用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return JsonResult.errorMsg("用户名已存在！");
        }
        return JsonResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/register")
    public JsonResult register(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        // 1. 判断参数非空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPassword)) {
            return JsonResult.errorMsg("用户名或密码为空！");
        }
        // 2. 密码大于6位
        if (userBO.getPassword().length() < 6) {
            return JsonResult.errorMsg("密码不能小于6位！");
        }
        // 3. 密码一致
        if (!userBO.getPassword().equals(userBO.getConfirmPassword())) {
            return JsonResult.errorMsg("密码不一致！");
        }
        // 4. 校验用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return JsonResult.errorMsg("用户名已存在！");
        }
        // 5. 注册用户
        Users user = userService.createUser(userBO);
        user.setPassword(null);
        // 3. 设置cookie信息
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);
        return JsonResult.ok(user);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public JsonResult login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 1. 判断参数非空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return JsonResult.errorMsg("用户名或密码为空！");
        }
        // 2. 登录查询用户信息
        Users user = null;
        try {
            user = userService.queryUserForLogin(userBO);
        } catch (Exception e) {
            return JsonResult.errorMsg("系统错误！");
        }
        if (user == null) {
            return JsonResult.errorMsg("用户名或密码错误！");
        }
        user.setPassword(null);
        // 3. 设置cookie信息
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);
        return JsonResult.ok(user);
    }
}
