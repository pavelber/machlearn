package com.codepredict.runners;

import com.codepredict.dalet.*;
import com.codepredict.dao.ICommitRepository;
import com.codepredict.dao.IIssueRepository;
import com.codepredict.entities.Commit;
import com.codepredict.entities.File;
import com.codepredict.entities.Issue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ConvertDaletToGeneric {
    public static final String ISSUE_NUMBER_PATTERN = "#\\d+";

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        IDaletIssueRepository daletIssueRepository = context.getBean(IDaletIssueRepository.class);
        IDaletCommitRepository daletCommitRepository = context.getBean(IDaletCommitRepository.class);
        IDaletFileRepository daletFileRepository = context.getBean(IDaletFileRepository.class);
        IIssueRepository issuesRepo = context.getBean(IIssueRepository.class);
        ICommitRepository commitsRepo = context.getBean(ICommitRepository.class);


        List<DaletIssue> daletIssues = daletIssueRepository.findAll();
        Map<Long,Issue> refid2issue = new HashMap<>();
        Map<Long,Commit> rev2commit = new HashMap<>();
        for(DaletIssue di:daletIssues){
            String reference = di.getReference();
            Issue issue = refid2issue.get(reference);
            if (issue==null) {
                issue = new Issue(getId(reference),di.getType(),di.getReference());
                refid2issue.put(issue.getId(),issue);
            }
            Commit commit = new Commit(di.getRevision());
            rev2commit.put(commit.getId(),commit);
            issue.addCommit(commit);
        }

        List<DaletCommit> daletCommits = daletCommitRepository.findAll();
        for(DaletCommit dc:daletCommits){
            Commit commit = rev2commit.get(dc.getRevision());
            if (commit==null) {
                commit = new Commit(dc.getRevision());
                rev2commit.put(commit.getId(),commit);
                commitsRepo.save(commit);
            }
            commit.setAuthor(dc.getAuthor());
            commit.setDate(dc.getDate());
            commit.setMessage(dc.getMessage());
        }

        List<DaletFile> daletFiles  = daletFileRepository.findAll();
        for(DaletFile df:daletFiles){
            File file =  new File(df.getFile(),0,df.getKind(), df.getType());
            Commit commit = rev2commit.get(df.getRevision());
            if (commit==null) {
                commit = new Commit(df.getRevision());
                rev2commit.put(commit.getId(),commit);
                commit.addFile(file);
                commitsRepo.save(commit);
            }
            commit.addFile(file);
        }

        issuesRepo.save(refid2issue.values());
    }

    private static Long getId(String s) {
        Pattern p = Pattern.compile(ISSUE_NUMBER_PATTERN);
        Matcher m = p.matcher(s);
        Long id = null;
        if (m.find()) {
            id = Long.parseLong(m.group().substring(1));
        }
        return id;
    }
}
