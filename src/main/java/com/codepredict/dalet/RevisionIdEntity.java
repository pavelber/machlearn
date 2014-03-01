package com.codepredict.dalet;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class RevisionIdEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long revision;

    public Long getRevision() {
        return revision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RevisionIdEntity)) return false;

        RevisionIdEntity that = (RevisionIdEntity) o;

        if (!revision.equals(that.revision)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return revision.hashCode();
    }
}
