package com.codepredict.learning;

import com.codepredict.dao.ICommitRepository;
import com.codepredict.entities.Commit;
import com.codepredict.learning.EntitiesAccessor;
import com.codepredict.learning.IEntitiesAccessor;
import com.codepredict.learning.IWekaInstancesCreator;
import com.codepredict.learning.WekaInstancesCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.core.Instances;

import java.util.List;

@Service
public class CommitInstancesCreator implements IWekaInstancesCreator {

    @Autowired
    private ICommitRepository commitsRepo;

    @Override
    public Instances getInstances() {
        List<Commit> all = commitsRepo.findAll();
        IEntitiesAccessor accessor = new EntitiesAccessor(all);
        WekaInstancesCreator creator = new WekaInstancesCreator(accessor);
        return creator.getInstances();
    }
}
