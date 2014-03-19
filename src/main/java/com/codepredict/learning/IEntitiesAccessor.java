package com.codepredict.learning;

import com.codepredict.entities.Entity;
import com.codepredict.entities.Parameter;

import java.util.List;
import java.util.Set;

/**
 * Created by Pavel on 3/13/14.
 */
public interface IEntitiesAccessor {
    Set<Parameter> getParameters();
    Set<String> getEnumParamPossibleValues(Parameter p);
    List<? extends Entity> getTrainingEntities();
}
