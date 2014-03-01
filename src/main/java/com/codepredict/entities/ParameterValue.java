package com.codepredict.entities;


import org.hibernate.annotations.Cascade;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@javax.persistence.Entity
public class ParameterValue extends GeneratedLongIdEntity {


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parameterId")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.REFRESH})
    private Parameter p;
    private String value;

    protected ParameterValue(){}
    public ParameterValue(Parameter p, String value) {
        this.p = p;
        this.value = value;
    }

    public Parameter getParameter() {
        return p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ParameterValue that = (ParameterValue) o;

        if (p != null ? !p.equals(that.p) : that.p != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (p != null ? p.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
