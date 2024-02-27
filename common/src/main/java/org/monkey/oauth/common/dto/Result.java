package org.monkey.oauth.common.dto;

import lombok.Data;
import org.monkey.oauth.common.utils.JsonUtil;

@Data
public class Result {
    private String code;
    private String msg;
    private Object data;

    public Result() {
        this.code = SUCCESS_CODE;
        this.msg = SUCCUSS_MSG;
    }

    public Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Object data) {
        this.code = SUCCESS_CODE;
        this.msg = SUCCUSS_MSG;
        this.data = data;
    }

    @Override
    public String toString() {
        return JsonUtil.objToStr(this);
    }

    public static final String SUCCESS_CODE = "00";
    public static final String SUCCUSS_MSG = "success";

    public static Result ok() {
        return new Result(null);
    }
}
