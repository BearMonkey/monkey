package org.monkey.mqtx.account.enums;

import lombok.Getter;

@Getter
public enum MsgStatusEnum {

    NOT_CONSUME("not_consume", "未消费"),
    COMSUMED("consumed", "已消费");

    private final String msgStatus;

    private final String name;

    MsgStatusEnum(String name, String msgStatus) {
        this.name = name;
        this.msgStatus = msgStatus;
    }
}
