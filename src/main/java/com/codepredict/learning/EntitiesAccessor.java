package com.codepredict.learning;

import com.codepredict.entities.Entity;
import com.codepredict.entities.Parameter;
import com.codepredict.entities.ParameterType;
import com.codepredict.entities.ParameterValue;
import org.springframework.stereotype.Component;

import java.util.*;

public class EntitiesAccessor implements IEntitiesAccessor {

    private int MAX_S2E = 10;

    private List<? extends Entity> entities;
    private Map<Parameter, Set<String>> param2Values = new HashMap<>(); // for Enum only
    private Set<Parameter> params = new HashSet<>();

    public EntitiesAccessor(List<? extends Entity> entities) {
        this.entities = entities;
        for(Entity e:entities){
            for(ParameterValue pv:e.getParameterValues()){
                Parameter parameter = pv.getParameter();
                params.add(parameter);
                if (parameter.getType() == ParameterType.Enum) {
                    Set<String> values = param2Values.get(parameter);
                    if (values == null){
                        values = new HashSet<>();
                        param2Values.put(parameter,values);
                    }
                    values.add(pv.getValue());
                }
            }
        }
    }

    @Override
    public Set<Parameter> getParameters() {
        return params;
    }

    @Override
    public Set<String> getEnumParamPossibleValues(Parameter p) {
        return param2Values.get(p);
    }

    @Override
    public List<? extends Entity> getTrainingEntities() {
        return entities;
    }
}
