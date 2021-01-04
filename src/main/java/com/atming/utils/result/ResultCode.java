package com.atming.utils.result;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: Annoming
 * @Date: 2021/1/4
 * @Time: 8:28
 * @Description  响应码枚举
 */


public enum ResultCode {

    /**
     * 操作成功
     */
    SUCCESS(100,"操作成功"),
    FAIL(103,"响应失败"),
    VALIDATE_FAILED(102,"参数校验失败"),
    ERROR(104,"未知错误");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
