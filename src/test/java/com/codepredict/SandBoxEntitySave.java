package com.codepredict;

import com.codepredict.dao.ICommitRepository;
import com.codepredict.dao.IIssueRepository;
import com.codepredict.entities.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Date;

public class SandBoxEntitySave {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        IIssueRepository issuesRepo = context.getBean(IIssueRepository.class);
        ICommitRepository commitsRepo = context.getBean(ICommitRepository.class);
        try {
            issuesRepo.delete(0L);
        } catch (Exception e) {

        }
        try {
            issuesRepo.delete(1L);
        } catch (Exception e) {

        }
        CodePredIssue issue = new CodePredIssue(0L, "feature", "tatata");
        issuesRepo.save(issue);
        CodePredIssue issue1 = new CodePredIssue(1L, "featuqwere", "taqwetata");
        Commit commit = new Commit(1L, new Date(), "Pavel", "fix #6");
        issue1.setCommits(Arrays.asList(commit));
        issue1.addParameterValue(new ParameterValue(new Parameter(ParameterType.Enum, "num"), "1"));
        commit.addFile(new File("/tmp", 0, "dir", "A"));
        issuesRepo.save(issue1);

        Commit c = commitsRepo.findOne(1L);
        System.out.println(c.getIssue());

    }
}
