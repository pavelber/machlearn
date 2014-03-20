package com.codepredict.dao;

import com.codepredict.entities.CodePredIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Pavel on 2/26/14.
 */
public interface IIssueRepository extends JpaRepository<CodePredIssue, Long> {
    List<CodePredIssue> findByTitle(String title);
}
