package com.codepredict.dao;

import com.codepredict.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Pavel on 2/26/14.
 */
public interface IFileRepository extends JpaRepository<File, Long> {
}
