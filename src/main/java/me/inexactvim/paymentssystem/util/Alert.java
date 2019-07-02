package me.inexactvim.paymentssystem.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Alert {

    @AllArgsConstructor
    public enum Type {

        SUCCESS("success"),
        ERROR("danger"),
        WARNING("warning"),
        INFO("info");

        private String name;

    }

    private Type type;
    private String message;

    public String getType() {
        return type.name;
    }

}
