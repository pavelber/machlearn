package com.codepredict.learning;

import com.codepredict.entities.Entity;
import com.codepredict.entities.Parameter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntitiesAccessor implements IEntitiesAccessor {
    @Override
    public List<Parameter> getParameters() {
        return null;
    }

    @Override
    public List<String> getPossibleValues(Parameter p) {
        return null;
    }

    @Override
    public List<Entity> getTrainingEntities() {
        return null;
    }
}
