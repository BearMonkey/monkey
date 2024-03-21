package org.monkey.user.pojo;

import lombok.Data;
import org.monkey.common.utils.JsonUtil;

import java.sql.Timestamp;

@Data
public class User {
    private Integer id;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private String status;
    private Timestamp createTime;

    @Override
    public String toString() {
        return JsonUtil.objToStr(this);
    }
}
