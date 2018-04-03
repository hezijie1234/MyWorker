package com.gongyou.worker.mvp.bean;

/**
 * 服务器回复数据
 */

public class ResponseInfo<T> {
    /**
     * {
     * "code": "0",
     * "data": "{……}"
     * }
     */
    public String flag;
    public String msg;
    public T data;

    @Override
    public String toString() {
        return "ResponseInfo{" +
                "flag='" + flag + '\'' +
                ", msg='" + msg + '\'' +
                ", T='" + data + '\'' +
                '}';
    }
}
