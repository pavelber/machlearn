package com.codepredict.entities;

public class GenericEntity extends Entity {

    private Status status;

    public GenericEntity(Long id, Status status) {
        super(id);
        this.status = status;
    }
}
