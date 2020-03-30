package com.anber.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Anber
 */
@ApiModel(value = "实体类", description = "用户注册信息实体")
public class UserBO {
    @ApiModelProperty(value = "用户名", notes = "用户名", name = "username", required = true)
    private String username;

    @ApiModelProperty(value = "密码", notes = "密码", name = "123456", required = true)
    private String password;

    @ApiModelProperty(value = "确认密码", notes = "确认密码", name = "123456", required = false)
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserBO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
