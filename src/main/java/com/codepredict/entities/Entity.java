package com.codepredict.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@Inheritance
public abstract class Entity extends ProvidedLongIdEntity {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "entityid", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    @Cascade({CascadeType.ALL})
    private List<ParameterValue> values = new ArrayList<>();
    protected Entity(){}
    public Entity(Long id) {
        super(id);
    }

    public void addParameterValue(ParameterValue pv){
        values.add(pv);
    }



}
