package com.unq.edu.li.pdesa.mentiUnq.exceptions;

public class EntityNotFoundException extends Exception {
    private String entityId;

    public static EntityNotFoundException createWith(String entityId) {
        return new EntityNotFoundException(entityId);
    }
    private EntityNotFoundException(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getMessage() {
        return "Entity '" + entityId + "' not found";
    }
}
