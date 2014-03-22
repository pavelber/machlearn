package com.codepredict.enrichers;

import com.codepredict.entities.Commit;
import com.codepredict.entities.Parameter;
import com.codepredict.entities.ParameterType;
import com.codepredict.entities.ParameterValue;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class S2EEnricher extends BaseEnricher {

    public static final int MAX_VALUES_FOR_ENUM = 40;

    @Override
    public void enrich(final List<Commit> all) {
        final Map<String, Set<String>> param2Value = new HashMap<>();
        all.forEach( c -> {
            c.getParameterValues().forEach( pv -> {
                Parameter parameter = pv.getParameter();
                if (parameter.getType() == ParameterType.String) {
                    String name = parameter.getName();
                    Set<String> values = param2Value.get(name);
                    if (values == null){
                        values = new HashSet<String>();
                        param2Value.put(name,values);
                    }
                    values.add(pv.getValue());
                }
            });
        });
        all.forEach( c -> {
            c.getParameterValues().forEach( pv -> {
                Parameter parameter = pv.getParameter();
                if (parameter.getType() == ParameterType.String) {
                    String name = parameter.getName();
                    if (param2Value.get(name).size()> MAX_VALUES_FOR_ENUM){
                        c.addParameterValue(enumParam(name, pv.getValue()));
                    }
                }
            });
        });

    }
}
