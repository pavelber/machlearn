package com.codepredict.dalet;

import com.codepredict.dalet.DaletCommit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Pavel on 2/24/14.
 */
public interface IDaletCommitRepository extends JpaRepository<DaletCommit, Long> {
}
