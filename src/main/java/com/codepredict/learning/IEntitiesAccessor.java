package com.codepredict.learning;

import com.codepredict.entities.Entity;
import com.codepredict.entities.Parameter;

import java.util.List;

/**
 * Created by Pavel on 3/13/14.
 */
public interface IEntitiesAccessor {
    List<Parameter> getParameters();
    List<String> getPossibleValues(Parameter p);
    List<Entity> getTrainingEntities();
}
