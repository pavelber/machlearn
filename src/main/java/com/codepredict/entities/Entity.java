package com.codepredict.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MappedSuperclass
@Inheritance
public abstract class Entity extends ProvidedLongIdEntity {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "entityid", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    @Cascade({CascadeType.ALL})
    private List<ParameterValue> values = new ArrayList<>();

    @Transient
    private Map<String,ParameterValue> paramName2Value = new HashMap<>();

    protected Entity(){}
    public Entity(Long id) {
        super(id);
    }

    public synchronized void  addParameterValue(ParameterValue pv){
        String name = pv.getParameter().getName();
        ParameterValue old = paramName2Value.get(name);
        if (old!=null) {
            values.remove(old);
        }
        values.add(pv);
        paramName2Value.put(name,pv);
    }



}
