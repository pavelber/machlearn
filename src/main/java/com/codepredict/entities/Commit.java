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
public class Commit extends Entity {

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String author;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "commitId", referencedColumnName = "id")
    @Cascade({CascadeType.ALL})
    @Fetch(FetchMode.SUBSELECT)
    private List<File> files = new ArrayList<>();

    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "issueId")
    private CodePredIssue codePredIssue;

    private String branch;

    protected Commit(){}

    public Commit(Long id) {
        super(id);
    }

    public Commit(Long id, Date date, String author, String message) {
        super(id);
        this.date = date;
        this.author = author;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public List<File> getFiles() {
        return files;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public void addFile(File file) {
        files.add(file);
    }

    public CodePredIssue getIssue() {
        return codePredIssue;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
