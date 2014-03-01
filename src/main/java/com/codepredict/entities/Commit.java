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
    @Fetch(FetchMode.SELECT)
    @Cascade({CascadeType.ALL})
    private List<File> files = new ArrayList<>();
    private String message;

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
}
