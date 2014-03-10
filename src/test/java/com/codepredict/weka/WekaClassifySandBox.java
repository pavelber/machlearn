package com.codepredict.weka;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Clusterer;
import weka.clusterers.EM;
import weka.core.Instance;
import weka.core.Instances;

public class WekaClassifySandBox {

    public static void main(String[] args) throws Exception {

        InstancesCreator instancesCreator = new InstancesCreator();
        Instances data = instancesCreator.createTrainingSet(true);
        trainAndClassify(instancesCreator, data);



    }

    private static void trainAndClassify(InstancesCreator instancesCreator, Instances data) throws Exception {
        Classifier cModel = train(data);


        // Specify that the instance belong to the training set
        // in order to inherit from the set description
        Instance iUse = instancesCreator.createInstance();
        iUse.setDataset(data);
        classify(cModel, iUse);
    }

    private static void classify(Classifier cModel, Instance iUse) throws Exception {
        // Get the likelihood of each classes
        // fDistribution[0] is the probability of being “positive”
        // fDistribution[1] is the probability of being “negative”
        double[] fDistribution = cModel.distributionForInstance(iUse);
        System.out.println(fDistribution[0]);
        System.out.println(fDistribution[1]);
    }

    private static Classifier train(Instances data) throws Exception {
        // Create a naïve bayes classifier
        Classifier cModel = new NaiveBayes();
        cModel.buildClassifier(data);
        // Test the model
        Evaluation eTest = new Evaluation(data);
        eTest.evaluateModel(cModel, data);

        // Print the result à la Weka explorer:
        String strSummary = eTest.toSummaryString();
        System.out.println(strSummary);

        // Get the confusion matrix
        double[][] cmMatrix = eTest.confusionMatrix();
        return cModel;
    }
}
