package org.monkey.mqtx.pay.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Pay {
    private Integer id;
    private String userName; // 用户名
    private String phone; // 手机号
    private String email; // 邮箱
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private boolean status; // 记录状态
}
