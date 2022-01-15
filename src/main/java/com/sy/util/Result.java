package com.sy.util;

/**
 * @ClassName Result
 * @Description TODO
 * @Author Administrator
 * @Date: 2021/12/9 16:55
 * @Version 1.0
 */
public class Result {
    public final static String MSG_SUCCESS = "success";
    public final static String MSG_ERROR = "error";
    public final static Integer CODE_SUCCESS = 20000;
    public final static Integer CODE_ERROR = 0;
    public final static Integer CLICK_ADD = 1;
    public final static Integer ANSWER_ADD = 1;
    private Integer code;
    private String msg;
    private Object data;
    private Long count;
    private int pages;
    private String token;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
