package com.codepredict.entities;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@javax.persistence.Entity
public class File extends GeneratedLongIdEntity {


    private String name;
    private Integer size;

    @Enumerated(EnumType.STRING)
    private FileType type;

    @Enumerated(EnumType.STRING)
    private ChangeType changeType;

    protected File() {
    }

    public File(String name, Integer size, String filetype, String changetype) {
        this.name = name;
        this.size = size;
        this.type = FileType.valueOf(filetype);
        this.changeType = ChangeType.valueOf(changetype);
    }

    public String getName() {
        return name;
    }
}
