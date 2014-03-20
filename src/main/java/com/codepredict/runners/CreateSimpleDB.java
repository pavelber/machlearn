package com.codepredict.runners;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.codepredict.dao.ICommitRepository;
import com.codepredict.dao.IFileRepository;
import com.codepredict.dao.IIssueRepository;
import com.codepredict.entities.CodePredIssue;
import com.codepredict.entities.Commit;
import com.codepredict.entities.File;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Random;


/**
 * Created by lev on 3/10/14.
 */
public class CreateSimpleDB {
    public static void main(String[] args) throws URISyntaxException {
        System.out.println("Starting ...");

        JiraRestClientFactory jrf = new AsynchronousJiraRestClientFactory();

        URI jUri = new URI("https://jira.spring.io");

        //JiraRestClient restClient = jrf.createWithBasicHttpAuthentication(jUri, user_name, password);
        JiraRestClient restClient = jrf.create(jUri, new AnonymousAuthenticationHandler());
        String jql_request = "project=DATAJPA";

        SearchRestClient src = restClient.getSearchClient();
        SearchResult sri = src.searchJql(jql_request, 1000, 0, null).claim();

        System.out.println("Retrieved  " +sri.getTotal());
        // System.out.println("Search Result: " + sri.toString());
        Integer j = 0;
        /**
         for (Issue result : sri.getIssues()) {
            // System.out.println("Issues: " + result.getId().toString());
            j = j + 1;
            System.out.println("CodePredIssue id: " +
                    result.getId().toString() + "  " +
                    result.getStatus().getName() + "  " +
                    result.getCreationDate().toString().substring(0, 10) + "  " +
                    // result.getCreationDate().toDateTime().f
                    result.getAssignee().getName() + "  " +  // olivergierke
                    result.getAssignee().getDisplayName()     // Oliver Gierke
            );
        }
         **/
        System.out.println("Got issues:  " + j.toString());

        //IssueRestClient irc = restClient.getIssueClient();
        //CodePredIssue issue = irc.getIssue(jql_request).claim();

        // com.codepredict.entities.CodePredIssue is;

        CodePredIssue the_issue = new CodePredIssue(101L, "issue_status", "issue_reference");

        Random rg = new Random();
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        ICommitRepository commitsRepo = context.getBean(ICommitRepository.class);
        IFileRepository filesRepo = context.getBean(IFileRepository.class);
        IIssueRepository issuesRepo = context.getBean(IIssueRepository.class);

        CodePredIssue my_issue = new CodePredIssue(101L, "issue_status", "issue_reference");

        issuesRepo.save(my_issue);

        // Files
        String file_name;
        File f;
        for (int i = 0; i < 10; i++) {
            file_name = "file_" + Integer.toString(i);
            //filesRepo.save(new File(file_name, rg.nextInt(10000),
            //         FileType.values()[rg.nextInt(2)], ChangeType.values()[rg.nextInt(4)]));
        }

        Date d = new Date(101, 0, 1, 1, 1);
        System.out.printf("my first date: %s\n", d.toString());
        Commit c1 = new Commit(new Long(1), d, "p1", "First commit");
        System.out.printf("first Commit: %s\n", c1.toString());
        Commit c2 = new Commit(2L, new Date(101, 0, 1, 1, 2), "p1", "Second commit");


        commitsRepo.save(c1);
        commitsRepo.save(c2);

        System.out.printf("Commit table has %d\n", commitsRepo.count());


        System.out.println("Finish");
    }
}
