package com.codepredict.learning;

import com.codepredict.entities.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WekaInstancesCreator implements IWekaInstancesCreator {

    @Autowired
    private IEntitiesAccessor entitiesAccessor;

    @Override
    public Instances getInstances() {
        Map<String,Attribute> name2attr = new HashMap<>();
        List<Parameter> parameters = entitiesAccessor.getParameters();
        for(Parameter p:parameters){
            switch(p.getType()){
                case Enum:
                    String name = p.getName();
                   // name2attr.put(name, new Attribute(name, new FastVector(.getPossibleValues(p)))
            }
        }

        return null;
    }
}
