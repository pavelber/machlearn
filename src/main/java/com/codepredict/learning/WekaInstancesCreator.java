package com.codepredict.learning;

import com.codepredict.entities.Entity;
import com.codepredict.entities.Parameter;
import com.codepredict.entities.ParameterValue;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.util.*;


public class WekaInstancesCreator implements IWekaInstancesCreator {

    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static int c = 0;

    private IEntitiesAccessor entitiesAccessor;
    private Map<String, Integer> p2index;

    public WekaInstancesCreator(IEntitiesAccessor entitiesAccessor) {
        this.entitiesAccessor = entitiesAccessor;
    }

    @Override
    public Instances getInstances() {
        Map<String, Attribute> name2attr = new HashMap<>();
        p2index = new HashMap<>();
        FastVector attrs = new FastVector();

        int i = 0;
        Set<Parameter> parameters = entitiesAccessor.getParameters();
        for (Parameter p : parameters) {
            String name = p.getName();

            Attribute attribute = null;
            switch (p.getType()) {
                case Enum:
                    Set<String> enumParamPossibleValues = entitiesAccessor.getEnumParamPossibleValues(p);
                    FastVector attributeValues = new FastVector(enumParamPossibleValues.size());
                    enumParamPossibleValues.forEach(attributeValues::addElement);
                    attribute = new Attribute(name, attributeValues);
                    break;
                case String:
                    break;
                case Date:
                    attribute = new Attribute(name, "dd-MM-yyyy");
                    break;
                case Double:
                    attribute = new Attribute(name);
                    break;
                default:
                    throw new RuntimeException("Unknown attr type");

            }
            if (attribute != null) {
                name2attr.put(name, attribute);
                p2index.put(p.getName(), i++);
                attrs.addElement(attribute);
            }
        }


        Instances dataset = new Instances("my_dataset", attrs, 7);

        List<? extends Entity> entities = entitiesAccessor.getTrainingEntities();
        for (Entity e : entities) {
            double[] attValues = new double[dataset.numAttributes()];
            for (ParameterValue parameterValue : e.getParameterValues()) {
                Parameter p = parameterValue.getParameter();
                String name = p.getName();
                Integer index = p2index.get(p.getName());
                String value = parameterValue.getValue();

                switch (p.getType()) {
                    case Enum:
                        attValues[index] = name2attr.get(name).indexOfValue(value);
                        break;
                    case String:
                        //      attValues[index] = name2attr.get(name).addStringValue(value);
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
            Instance instance = new Instance(1.0, attValues);
            //  System.out.println(getExc(instance));

            dataset.add(instance);

            System.out.println(getExc(instance));

        }
        for (int ii = 0; ii < dataset.numInstances(); ii++) {
            System.out.println(getExc(dataset.instance(ii)));
        }
        return dataset;
    }

    private boolean getExc(final Instance instance) {
        try {
            String s = instance.toString();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
