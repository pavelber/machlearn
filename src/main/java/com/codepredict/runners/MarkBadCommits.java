package com.codepredict.runners;

import com.codepredict.dao.ICommitRepository;
import com.codepredict.entities.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkBadCommits {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        ICommitRepository commitsRepo = context.getBean(ICommitRepository.class);

        Map<String, Commit> filename2Commit = new HashMap<>();
        Page p;

        Parameter bad = new Parameter(ParameterType.Enum, "BAD");
        int page = 0;
        do {

            Pageable pageable = new PageRequest(page, 100, Sort.Direction.ASC, "date");
            p = commitsRepo.findAll(pageable);
            List<Commit> commits = p.getContent();
            for (Commit c : commits) {
                List<File> files = c.getFiles();
                CodePredIssue issue = c.getIssue();
                boolean fix = issue == null ? false : issue.getType().equals("B");
                for (File f : files) {

                    String name = f.getName();
                    if (!fix) {
                        filename2Commit.put(name, c);
                    } else {
                        Commit remove = filename2Commit.remove(name);
                        if (remove != null) {
                            remove.addParameterValue(new ParameterValue(bad, "true"));
                            commitsRepo.save(remove);
                        }
                    }
                }
            }
            page++;
        } while (p.hasNextPage());
    }
}
