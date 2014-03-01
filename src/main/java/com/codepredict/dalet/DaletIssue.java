package com.codepredict.dalet;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity()
@Table(name = "ISSUES")
public class DaletIssue extends RevisionIdEntity {
    private String type;
    private String reference;



    public String getType() {
        return type;
    }

    public String getReference() {
        return reference;
    }


}
