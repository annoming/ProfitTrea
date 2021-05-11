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
     * 响应码类别
     */
    SUCCESS(100,"操作成功"),
    FAIL(103,"响应失败"),
    VALIDATE_FAILED(102,"token失效"),
    ERROR(104,"未知错误"),
    EXIT(101,"注销token"),
    WARNING(105,"token未生效");

    private Integer code;
    private String msg;


    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
