package org.monkey.mqtx.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.monkey.common.utils.JsonUtil;

@Data
public class UpdateAccountDto {
    @JsonProperty("messageId")
    private String messageId;
    @JsonProperty("name")
    private String accountNo;
    @JsonProperty("pass")
    private String accountPwd;
    @JsonProperty("type")
    private String accountType;

    @Override
    public String toString() {
        return JsonUtil.objToStr(this);
    }
}
