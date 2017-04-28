package com.zte.ums.esight.domain.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ResultSerializer.class)
public class Result {
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    private int status;
    private Object data;
    private String message;

    public Result(){}

    public Result(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
