package com.codepredict.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@javax.persistence.Entity
public class Issue extends Entity {

    @Enumerated(EnumType.STRING)
    private String type;
    private String title;
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "issueId", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    @Cascade({CascadeType.ALL})
    private List<Commit> commits = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date started;
    @Temporal(TemporalType.TIMESTAMP)
    private Date finished;

    private int implementationDays;

    private Issue() {
    }

    public Issue(Long id, String type, String reference) {
        super(id);
        this.type = type;
        this.title = reference;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void addCommit(Commit commit) {
        commits.add(commit);
    }
}
