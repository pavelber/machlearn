package com.codepredict.learning;

import com.codepredict.entities.Commit;

import java.util.List;

public class BranchCommitInstancesCreator extends CommitInstancesCreator {
    @Override
    protected List<Commit> getCommits() {
        return commitsRepo.findByBranch("branches/builds/3.5");
    }
}
