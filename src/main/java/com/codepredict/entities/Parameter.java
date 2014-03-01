package com.codepredict.entities;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@javax.persistence.Entity
public class Parameter extends GeneratedLongIdEntity {

    @Enumerated(EnumType.STRING)
    private ParameterType type;
    private String name;


    protected Parameter(){}
    public Parameter(ParameterType type, String name) {
        this.type = type;
        this.name = name;
    }


    public ParameterType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (!name.equals(parameter.name)) return false;
        if (type != parameter.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
