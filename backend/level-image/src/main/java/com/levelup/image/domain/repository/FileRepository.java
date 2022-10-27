package com.levelup.image.domain.repository;

import com.levelup.common.domain.FileType;
import com.levelup.image.domain.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByOwnerIdAndFileType(Long ownerId, FileType fileType);

    @Query("select f from File f where f.fileType = :fileType and f.ownerId in :ownerIds")
    List<File> findByOwnerIdAndFileType(
            @Param("ownerIds") List<Long> ownerIds,
            @Param("fileType") FileType fileType);
}
