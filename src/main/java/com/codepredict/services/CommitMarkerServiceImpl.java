package com.codepredict.services;

import com.codepredict.dao.ICommitRepository;
import com.codepredict.dao.IIssueRepository;
import com.codepredict.entities.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lev on 3/9/14.
 */
@Service
public class CommitMarkerServiceImpl implements ICommitMarkerService {
    @Autowired
    private ICommitRepository commitRepository;

    @Autowired
    private IIssueRepository issueRepository;

    @Override
    public boolean isGood(Commit commit) {
        return false;
    }
}
