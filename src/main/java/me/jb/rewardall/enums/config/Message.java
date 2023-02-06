package me.jb.rewardall.enums.config;

import org.jetbrains.annotations.NotNull;

public enum Message {

    NO_PERM("noPerm"),
    ERROR("helpCmd"),
    NOT_INT("notInt"),
    NOT_INT_SET("notIntSetCmd"),
    TOO_HIGH("tooHigh"),
    SET("progresseSet"),

    RELOAD("reload"),
    ADD_POINTS("addPoints"),
    RESET_POINTS("resetPoints"),

    COMPLETE("complete"),

    ;

    private final String key;

    Message(String key) {
        this.key = key;
    }

    @NotNull
    public String getKey() {
        return this.key;
    }
}
