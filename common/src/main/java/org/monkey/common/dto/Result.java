package org.monkey.common.dto;

import lombok.Data;
import org.monkey.common.utils.JsonUtil;

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

    public static final String ERROR_CODE = "-1";

    public static Result ok() {
        return new Result(null);
    }
    public static Result ok(Object data) {
        return new Result(data);
    }
    public static Result error(String code, String errorMsg) {
        return new Result(code, errorMsg);
    }
    public static Result error(String errorMsg) {
        return new Result(ERROR_CODE, errorMsg);
    }

}
