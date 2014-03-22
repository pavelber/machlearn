package com.codepredict.learning;

import com.codepredict.entities.Entity;
import com.codepredict.entities.Parameter;
import com.codepredict.entities.ParameterValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class WekaInstancesCreator implements IWekaInstancesCreator {

    public static final String DATE_FORMAT = "dd-MM-yyyy";

    private IEntitiesAccessor entitiesAccessor;

    public WekaInstancesCreator(IEntitiesAccessor entitiesAccessor) {
        this.entitiesAccessor = entitiesAccessor;
    }

    @Override
    public Instances getInstances() {
        Map<String, Attribute> name2attr = new HashMap<>();
        Map<Parameter, Integer> p2index = new HashMap<>();
        int i = 0;
        Set<Parameter> parameters = entitiesAccessor.getParameters();
        for (Parameter p : parameters) {
            String name = p.getName();

            switch (p.getType()) {
                case Enum:
                    Set<String> enumParamPossibleValues = entitiesAccessor.getEnumParamPossibleValues(p);
                    FastVector attributeValues = new FastVector(enumParamPossibleValues.size());
                    for (String s : enumParamPossibleValues) {
                        attributeValues.addElement(s);
                    }
                    name2attr.put(name, new Attribute(name, attributeValues));
                    break;
                case String:
                    name2attr.put(name, new Attribute(name, (FastVector) null));
                    break;
                case Date:
                    name2attr.put(name, new Attribute(name, "dd-MM-yyyy"));
                    break;
                case Double:
                    name2attr.put(name, new Attribute(name));
                    break;
                default:
                    throw new RuntimeException("Unknown attr type");

            }

            p2index.put(p, i++);
        }

        FastVector attrs = new FastVector();
        name2attr.values().stream().forEach(attr -> attrs.addElement(attr));
        Instances dataset = new Instances("my_dataset", attrs, 0);

        List<? extends Entity> entities = entitiesAccessor.getTrainingEntities();
        for (Entity e : entities) {
            double[] attValues = new double[dataset.numAttributes()];
            for (ParameterValue parameterValue : e.getParameterValues()) {
                Parameter p = parameterValue.getParameter();
                String name = p.getName();
                Integer index = p2index.get(p);
                String value = parameterValue.getValue();

                switch (p.getType()) {
                    case Enum:
                        Set<String> enumParamPossibleValues = entitiesAccessor.getEnumParamPossibleValues(p);
                        FastVector attributeValues = new FastVector(enumParamPossibleValues.size());
                        for (String s : enumParamPossibleValues) {
                            attributeValues.addElement(s);
                        }
                        name2attr.put(name, new Attribute(name, attributeValues));
                        break;
                    case String:
                        attValues[index] = name2attr.get(name).addStringValue(value);
                        break;
                    case Date:
                        attValues[index] = Double.valueOf(value); // not sure that it is okay //todo: how do I create value for Date???
                        break;
                    case Double:
                        attValues[index] = Double.valueOf(value);
                        break;
                    default:
                        throw new RuntimeException("Unknown attr type");

                }
            }
            dataset.add(new Instance(1.0, attValues));

        }

        return dataset;
    }
}
