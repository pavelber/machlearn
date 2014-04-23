package com.codepredict.enrichers;

import com.codepredict.entities.Commit;

import java.util.List;

/**
 * Created by Pavel on 3/22/2014.
 */
public interface IEnricher {
    void enrich(List<Commit> all);
}
