package com.codepredict.services;

import com.codepredict.entities.Commit;

/**
 * Created by lev on 3/9/14.
 */
public interface ICommitMarkerService {
    boolean isGood(Commit commit);
}
