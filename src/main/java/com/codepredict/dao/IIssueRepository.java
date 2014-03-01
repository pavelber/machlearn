package com.codepredict.dao;

import com.codepredict.entities.Commit;
import com.codepredict.entities.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Pavel on 2/26/14.
 */
public interface IIssueRepository extends JpaRepository<Issue,Long> {
}
