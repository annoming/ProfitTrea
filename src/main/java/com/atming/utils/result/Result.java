package com.atming.utils.result;


import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: Annoming
 * @Date: 2021/1/4
 * @Time: 9:03
 * @Description
 */
public final class Result implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public Result() {

    }



    public static Result success(Object data){
        String msg = ResultCode.SUCCESS.getMsg();
        Integer code = ResultCode.SUCCESS.getCode();
        return success(code,msg,data);
    }

    private static Result success(Integer code,String msg,Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
