package org.monkey.mqtx.account.pojo;

import lombok.Data;
import org.monkey.common.utils.JsonUtil;

import java.util.Date;

@Data
public class Account {
    private Integer id;
    private String accountNo;
    private String accountPwd;
    private String accountType;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private boolean status;

    @Override
    public String toString() {
        return JsonUtil.objToStr(this);
    }
}
