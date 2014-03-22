package com.codepredict.enrichers;

import com.codepredict.entities.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FilesEnricher extends BaseEnricher {

    @Override
    public void enrich(final List<Commit> all) {
        final Map<String, Set<String>> param2Value = new HashMap<>();
        all.forEach( c -> {
            List<File> files = c.getFiles();
            c.addParameterValue(intParam("NUM OF FILES", files.size()));

        });
    }
}
