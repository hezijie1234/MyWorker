package com.gongyou.worker.mvp.bean.second;

/**
 * 作    者: ZhangLC
 * 创建时间: 2017/6/21.
 * 说    明:
 */

public class LoginInfo {

    private String token;
    private String mobile;
    private int isidentity;

    public int getIsidentity() {
        return isidentity;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "token='" + token + '\'' +
                ", mobile='" + mobile + '\'' +
                ", isidentity=" + isidentity +
                '}';
    }

    public String getMobile() {
        return mobile;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setIsidentity(int isidentity) {
        this.isidentity = isidentity;
    }
}
