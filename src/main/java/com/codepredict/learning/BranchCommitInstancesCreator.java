package com.codepredict.learning;

import com.codepredict.entities.Commit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BranchCommitInstancesCreator extends CommitInstancesCreator {
    @Override
    protected List<Commit> getCommits() {
        return commitsRepo.findByBranch("builds/3.4");
    }
}
