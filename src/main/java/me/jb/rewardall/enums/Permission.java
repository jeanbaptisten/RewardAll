package me.jb.rewardall.enums;

import org.jetbrains.annotations.NotNull;

public enum Permission {

    USE,
    RELOAD,
    RESET,
    SET,
    ;

    private final String permission;

    Permission() {
        this.permission = this.name().toLowerCase().replace("_", ".");
    }

    @NotNull
    public String getPermission() {
        return "rewardall." + this.permission;
    }
}