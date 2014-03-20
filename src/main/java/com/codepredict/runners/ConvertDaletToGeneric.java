package com.codepredict.runners;

import com.codepredict.dalet.*;
import com.codepredict.dao.ICommitRepository;
import com.codepredict.dao.IIssueRepository;
import com.codepredict.entities.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertDaletToGeneric {
    public static final String ISSUE_NUMBER_PATTERN = "#\\d+";

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        IDaletIssueRepository daletIssueRepository = context.getBean(IDaletIssueRepository.class);
        IDaletCommitRepository daletCommitRepository = context.getBean(IDaletCommitRepository.class);
        IDaletFileRepository daletFileRepository = context.getBean(IDaletFileRepository.class);
        IIssueRepository issuesRepo = context.getBean(IIssueRepository.class);
        ICommitRepository commitsRepo = context.getBean(ICommitRepository.class);

        Parameter team = new Parameter(ParameterType.Enum, "team");
        Parameter branch = new Parameter(ParameterType.Enum, "branch");
        Parameter numOfFiles = new Parameter(ParameterType.Double, "num of files");


        System.out.println("Loading dalet issues");
        List<DaletIssue> daletIssues = daletIssueRepository.findAll();
        System.out.println("END OF Loading dalet issues");

        Map<Long, CodePredIssue> refid2issue = new HashMap<>();
        Map<Long, Commit> rev2commit = new HashMap<>();
        for (DaletIssue di : daletIssues) {
            String reference = di.getReference();
            CodePredIssue issue = refid2issue.get(reference);
            if (issue == null) {
                issue = new CodePredIssue(getId(reference), di.getType(), di.getReference());
                refid2issue.put(issue.getId(), issue);
            }
            Commit commit = new Commit(di.getRevision());
            rev2commit.put(commit.getId(), commit);
            issue.addCommit(commit);
        }

        System.out.println("Loading dalet commits");
        List<DaletCommit> daletCommits = daletCommitRepository.findAll();
        System.out.println("END OF Loading dalet commits");

        for (DaletCommit dc : daletCommits) {
            Commit commit = rev2commit.get(dc.getRevision());
            if (commit == null) {
                commit = new Commit(dc.getRevision());
                commit.addParameterValue(new ParameterValue(team, dc.getTeam()));
                commit.addParameterValue(new ParameterValue(numOfFiles, dc.getChangedfilescount().toString()));
                rev2commit.put(commit.getId(), commit);
                //        commitsRepo.save(commit);
            }
            commit.setAuthor(dc.getAuthor());
            commit.setDate(dc.getDate());
            commit.setMessage(dc.getMessage());
        }
        System.out.println("Loading dalet files");
        List<DaletFile> daletFiles = daletFileRepository.findAll();
        System.out.println("End of Loading dalet files");

        for (DaletFile df : daletFiles) {
            File file = new File(df.getFile(), 0, df.getKind(), df.getType());
            Commit commit = rev2commit.get(df.getRevision());
            if (commit == null) {
                commit = new Commit(df.getRevision());
                rev2commit.put(commit.getId(), commit);
                commit.addFile(file);
                String branchName = commit.getParameterValue(branch);
                String fileBranch = extractBranch(file.getName());
                if (branchName == null) {
                    commit.addParameterValue(new ParameterValue(branch, fileBranch));
                } else {
                    if (!branchName.equals(fileBranch)) {
                        throw new RuntimeException("Different branches for commit " + commit.getId());
                    }
                }
                //commitsRepo.save(commit);
            }
            commit.addFile(file);
        }

        System.out.println("END OF PROCESSING " + rev2commit.size() + " commits " + refid2issue.size() + " issues");

        issuesRepo.save(refid2issue.values());
        commitsRepo.save(rev2commit.values());

        System.exit(0);
    }

    private static String extractBranch(String name) {
        String parts[] = name.split("/");
        return "/" + parts[0] + "/" + parts[1] + "/" + parts[2];
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
