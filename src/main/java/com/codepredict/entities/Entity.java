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
    @Fetch(FetchMode.SUBSELECT)
    @Cascade({CascadeType.ALL})
    private List<ParameterValue> values = new ArrayList<>();

    @Transient
    private Map<String, ParameterValue> paramName2Value;

    protected Entity() {
    }

    public Entity(Long id) {
        super(id);
    }

    public void addParameterValue(ParameterValue pv) {
        createaMap();
        String name = pv.getParameter().getName();
        ParameterValue old = paramName2Value.get(name);
        if (old != null) {
            if (old.getParameter().getType() != pv.getParameter().getType()) {
                throw new RuntimeException("Different type, the same name:" + name);
            }
            values.remove(old);
        }
        values.add(pv);
        paramName2Value.put(name, pv);
    }


    public String getParameterValue(Parameter p) {
        createaMap();
        ParameterValue parameterValue = paramName2Value.get(p.getName());
        return parameterValue == null ? null : parameterValue.getValue();
    }

    private void createaMap() {
        if (paramName2Value == null) {
            paramName2Value = new HashMap<>();
            values.forEach(v -> {
                if (v.getParameter().getType() == ParameterType.Enum)
                    paramName2Value.put(v.getParameter().getName(), v);
            });
        }
    }

    public List<ParameterValue> getParameterValues() {
        return new ArrayList(paramName2Value.values());
    }
}
