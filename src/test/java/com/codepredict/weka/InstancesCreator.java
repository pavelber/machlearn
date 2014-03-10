package com.codepredict.weka;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class InstancesCreator {

    private Attribute attribute1;
    private Attribute attribute2;
    private FastVector fvNominalVal;
    private Attribute attribute3;
    private FastVector fvClassVal;
    private Attribute classAttribute;
    private FastVector fvWekaAttributes;

    public InstancesCreator() {
        // Declare two numeric attributes
        attribute1 = new Attribute("firstNumeric");
        attribute2 = new Attribute("secondNumeric");

        // Declare a nominal attribute along with its values
        fvNominalVal = new FastVector(3);
        fvNominalVal.addElement("blue");
        fvNominalVal.addElement("gray");
        fvNominalVal.addElement("black");
        attribute3 = new Attribute("aNominal", fvNominalVal);

        // Declare the class attribute along with its values
        fvClassVal = new FastVector(2);
        fvClassVal.addElement("positive");
        fvClassVal.addElement("negative");
        classAttribute = new Attribute("theClass", fvClassVal);

        // Declare the feature vector
        fvWekaAttributes = new FastVector(4);
        fvWekaAttributes.addElement(attribute1);
        fvWekaAttributes.addElement(attribute2);
        fvWekaAttributes.addElement(attribute3);
        fvWekaAttributes.addElement(classAttribute);
    }

    public Instances createTrainingSet(boolean classAttr) {


        // Create an empty training set
        Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 10);
        // Set class index
        if (classAttr) {
            isTrainingSet.setClassIndex(3);
        }

        // Create the instance
        Instance iExample = new Instance(4);
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(0), 1.0);
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), 0.5);
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(2), "gray");
        iExample.setValue((Attribute) fvWekaAttributes.elementAt(3), "positive");

        // add the instance
        isTrainingSet.add(iExample);

        return isTrainingSet;
    }

    public Instance createInstance() {
        Instance iUse = new Instance(4);
        iUse.setValue((Attribute) fvWekaAttributes.elementAt(0), 1.0);
        iUse.setValue((Attribute) fvWekaAttributes.elementAt(1), 0.5);
        iUse.setValue((Attribute) fvWekaAttributes.elementAt(2), "gray");
        iUse.setValue((Attribute) fvWekaAttributes.elementAt(3), "positive");
        return iUse;
    }
}
