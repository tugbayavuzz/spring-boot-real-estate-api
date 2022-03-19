package com.realestate.utils;

import com.realestate.model.enums.MessageType;

public enum MessageUtil {
    SUCCESS("E0000", "Success!", MessageType.INFO),
    UNKNOWN_ERROR("E9999", "Unknown error occurred!", MessageType.ERROR),
    ID_IS_REQUIRED("E0001", "ID is required!", MessageType.WARNING),
    EMAIL_IS_REQUIRED("E0002", "Email is required!", MessageType.WARNING),
    DATA_NOT_FOUND("E0003", "Data not found!", MessageType.ERROR),
    VALIDATION_ERROR("E0004", "Validation error. Check 'errors' field for details.", MessageType.ERROR),
    INSUFFICIENT_PRIVILEGE("E0005", "Insufficient privilege.", MessageType.ERROR),
    PASSIVE_STATUS_ERROR("E0006", "This advert is already in passive status.", MessageType.ERROR),
    ACTIVE_STATUS_ERROR("E0007", "This advert is already in active status.", MessageType.ERROR),
    INVALID_PACKAGE_ERROR("E0008", "This package is finished. It couldn't be update.", MessageType.ERROR);

    String code;
    String text;
    MessageType type;

    MessageUtil(String code, String text, MessageType type) {
        this.code = code;
        this.text = text;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public MessageType getType() {
        return type;
    }

}