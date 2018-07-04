package com.todo.todo.enums;

public enum EntityStatus {
    ENABLE("Enable"),
    DISABLE("Disable");

    String status;

    EntityStatus(String status) {
        this.status = status;
    }
}
