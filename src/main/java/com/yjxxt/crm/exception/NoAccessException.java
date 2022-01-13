package com.yjxxt.crm.exception;

/**
 * @ClassName NoAccess
 * @Desc
 * @Author xiaoding
 * @Date 2022-01-03 10:52
 * @Version 1.0
 */
public class NoAccessException extends RuntimeException{
    private Integer code=401 ;
    private String msg="权限不足!";


    public NoAccessException() {
        super("权限不足!");
    }

    public NoAccessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public NoAccessException(Integer code) {
        super("权限不足!");
        this.code = code;
    }

    public NoAccessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
