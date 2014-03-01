package com.codepredict.dalet;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity()
@Table(name = "FILES")
public class DaletFile extends RevisionIdEntity {
    private String type;
    private String kind;
    private String file;

    public String getType() {
        return type;
    }

    public String getKind() {
        return kind;
    }

    public String getFile() {
        return file;
    }
}
