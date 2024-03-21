package org.monkey.mqtx.common.enums;

public enum OrderState {
    NEW("new"),
    PAY("pay"),
    FIN("fin"),
    EXP("exp"),
    FAI("fai")
    ;

    private String name;

    OrderState(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
