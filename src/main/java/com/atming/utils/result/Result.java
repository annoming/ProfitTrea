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

    public static Result fail(String msg) {
        return fail(ResultCode.FAIL.getCode(),msg,null);
    }

    public static Result fail(String msg, Object data) {
        return fail(ResultCode.FAIL.getCode(),msg,data);

    }

    private static Result fail(Integer code, String msg,Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result refuse(String msg) {
        return refuse(ResultCode.VALIDATE_FAILED.getCode(),msg,null);
    }

    private static Result refuse(Integer code, String msg,Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result error(String msg) {
        return error(ResultCode.ERROR.getCode(),msg,null);
    }

    private static Result error(Integer code, String msg,Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result warning(String msg) {
        return error(ResultCode.WARNING.getCode(),msg,null);
    }

    private static Result warning(Integer code, String msg,Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result exit(String msg) {
        return error(ResultCode.EXIT.getCode(),msg,null);
    }

    private static Result exit(Integer code, String msg,Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    /**
     * getter方法一定要设置，否则使用@ResponseBody时返回数据会报错
     * @return Integer
     */
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
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
