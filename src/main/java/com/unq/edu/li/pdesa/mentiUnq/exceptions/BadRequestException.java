package com.unq.edu.li.pdesa.mentiUnq.exceptions;

public class BadRequestException extends Exception {
    private String entityId;

    public static BadRequestException createWith(String entityId) {
        return new BadRequestException(entityId);
    }
    private BadRequestException(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getMessage() {
        return "Request body is wrong";
    }
}
