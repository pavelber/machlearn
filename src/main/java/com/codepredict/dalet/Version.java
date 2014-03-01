package com.codepredict.dalet;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity()
@Table(name = "VERSIONS")
public class Version  extends RevisionIdEntity  {
    private String product;
    private String type;
    private String branch;
    private String productversion;
    private String fullversion;

    public String getProduct() {
        return product;
    }

    public String getType() {
        return type;
    }

    public String getBranch() {
        return branch;
    }

    public String getProductversion() {
        return productversion;
    }

    public String getFullversion() {
        return fullversion;
    }
}
