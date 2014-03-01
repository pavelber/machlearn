package com.codepredict.dalet;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity()
@Table(name = "COMMITS")
public class DaletCommit extends RevisionIdEntity {
    private Date date;
    private String author;
    private String team;
    private Integer changedfilescount;
    private String message;

    public Date getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getTeam() {
        return team;
    }

    public Integer getChangedfilescount() {
        return changedfilescount;
    }

    public String getMessage() {
        return message;
    }
}
