package com.codepredict.weka;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Clusterer;
import weka.clusterers.EM;
import weka.core.Instance;
import weka.core.Instances;

public class WekaClusterSandBox {

    public static void main(String[] args) throws Exception {

        InstancesCreator instancesCreator = new InstancesCreator();
        Instances data = instancesCreator.createTrainingSet(false);


        ClusterEvaluation eval = new ClusterEvaluation();
        Clusterer clusterer = new EM();                                 // new clusterer instance, default options
        clusterer.buildClusterer(data);                                 // build clusterer
        eval.setClusterer(clusterer);                                   // the cluster to evaluate
        eval.evaluateClusterer(data);                                // data to evaluate the clusterer on
        System.out.println("# of clusters: " + eval.getNumClusters());  // output # of clusters

        int clusterInstance = clusterer.clusterInstance(instancesCreator.createInstance());
        System.out.println("clusterInstance = " + clusterInstance);
    }


}
