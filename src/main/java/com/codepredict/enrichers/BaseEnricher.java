package com.codepredict.enrichers;

import com.codepredict.entities.Entity;
import com.codepredict.entities.Parameter;
import com.codepredict.entities.ParameterType;
import com.codepredict.entities.ParameterValue;

public abstract class BaseEnricher implements IEnricher {

    protected ParameterValue enumParam( String name, String value) {
        return new ParameterValue(new Parameter(ParameterType.Enum, name), value);
    }

    protected ParameterValue intParam( String name, Integer value) {
        return new ParameterValue(new Parameter(ParameterType.Double, name), value.toString());
    }

}
