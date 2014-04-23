package com.codepredict.enrichers;

import com.codepredict.entities.Commit;
import org.springframework.stereotype.Service;

import java.util.List;

public class EnrichersPipeline implements IEnrichersPipeline {

    private List<IEnricher> enrichers;

    public EnrichersPipeline(List<IEnricher> enrichers) {
        this.enrichers = enrichers;
    }

    @Override
    public void enrich(List<Commit> all) {
          enrichers.forEach(e -> e.enrich(all));
    }
}
