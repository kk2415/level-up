package com.levelup.core.repository.file;


import com.levelup.core.domain.file.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    @Query("select f from File f where f.article.articleId =: articleId")
    List<File> findByArticleId(@Param("articleId") Long articleId);
}
