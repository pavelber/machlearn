package com.codepredict.runners;

import com.codepredict.learning.BranchCommitInstancesCreator;
import com.codepredict.learning.CommitInstancesCreator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.File;

public class RunClusteringOnCommit {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        CommitInstancesCreator creator = context.getBean(BranchCommitInstancesCreator.class);
        Instances dataset = creator.getInstances();
        //4.output dataset
        System.out.println(dataset);

//5.save dataset
        String file = "C:\\temp\\weka_test.arff";
        ArffSaver saver = new ArffSaver();
        saver.setInstances(dataset);
        saver.setFile(new File(file));
        saver.writeBatch();

        //7.preprocess strings (almost no classifier supports them)
        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(dataset);
        dataset = Filter.useFilter(dataset, filter);
        System.out.println(dataset);

        String[] options = new String[2];
        options[0] = "-I";                 // max. iterations
        options[1] = "100";
        EM clusterer = new EM();   // new instance of clusterer
        clusterer.setOptions(options);     // set the options
        clusterer.buildClusterer(dataset);    // build the clusterer

        ClusterEvaluation eval = new ClusterEvaluation();

        eval.setClusterer(clusterer);                                   // the cluster to evaluate
        eval.evaluateClusterer(dataset);                                // data to evaluate the clusterer on
        System.out.println("# of clusters: " + eval.getNumClusters());  // output # of clusters
    }
}
