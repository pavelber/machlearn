package com.codepredict.enrichers;

import com.codepredict.entities.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FilesEnricher extends BaseEnricher {

    public static final int MANY_FILES = 20;

    @Override
    public void enrich(final List<Commit> all) {
        final Map<String, Set<String>> param2Value = new HashMap<>();
        all.forEach( c -> {
            List<File> files = c.getFiles();
          //  c.addParameterValue(intParam("NUM OF FILES", files.size()));
            boolean cfiles= false;
            boolean javafiles = false;
            boolean manyfiles = files.size()> MANY_FILES;
            boolean filesremoved = false;
            boolean filesadded = false;
            boolean onlychanged = true;
            int size=0;
            for(File f:files){
                cfiles |= f.getName().toLowerCase().endsWith(".c");
                cfiles |= f.getName().toLowerCase().endsWith(".cpp");
                javafiles |= f.getName().toLowerCase().endsWith(".java");
                filesremoved|=f.getChangeType()==ChangeType.D;
                filesadded|=f.getChangeType()==ChangeType.A;
                onlychanged&=f.getChangeType()==ChangeType.M;
            }
            c.addParameterValue(enumParam("C FILES", Boolean.toString(cfiles)));
            c.addParameterValue(enumParam("Java FILES", Boolean.toString(javafiles)));
            c.addParameterValue(enumParam("Many FILES", Boolean.toString(manyfiles)));
            c.addParameterValue(enumParam("Removed FILES", Boolean.toString(filesremoved)));
            c.addParameterValue(enumParam("Added FILES", Boolean.toString(filesadded)));
            c.addParameterValue(enumParam("Only Changed FILES", Boolean.toString(onlychanged)));
        });
    }
}
