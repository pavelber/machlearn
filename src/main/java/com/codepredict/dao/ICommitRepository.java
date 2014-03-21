package com.codepredict.dao;

import com.codepredict.entities.Commit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Pavel on 2/26/14.
 */
public interface ICommitRepository extends JpaRepository<Commit,Long> {
    List<Commit> findByBranch(String branch);
}
