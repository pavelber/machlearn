package com.codepredict.runners;

import com.atlassian.httpclient.apache.httpcomponents.DefaultHttpClient;
import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.codepredict.dao.ICommitRepository;
import com.codepredict.dao.IFileRepository;
import com.codepredict.dao.IIssueRepository;
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
        SearchResult sri = src.searchJql(jql_request).claim();

        System.out.println("Retrieved  " +sri.getTotal());


        //IssueRestClient irc = restClient.getIssueClient();
        //Issue issue = irc.getIssue(jql_request).claim();


        Random rg = new Random();
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        ICommitRepository commitsRepo = context.getBean(ICommitRepository.class);
        IFileRepository filesRepo = context.getBean(IFileRepository.class);
        IIssueRepository issuesRepo = context.getBean(IIssueRepository.class);

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
