package com.levelup.image.domain.entity;

import com.levelup.common.domain.FileType;
import com.levelup.common.domain.base.BaseTimeEntity;
import com.levelup.common.util.file.UploadFile;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Table(name = "file")
@Entity
public class File extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    @Embedded
    private UploadFile uploadFile;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(nullable = false)
    private Long ownerId;

    protected File() {}

    private File(Long id, UploadFile uploadFile, FileType fileType, Long ownerId) {
        this.id = id;
        this.uploadFile = uploadFile;
        this.fileType = fileType;
        this.ownerId = ownerId;
    }

    public static File of(Long id, UploadFile uploadFile, FileType fileType, Long ownerId) {
        return new File(id, uploadFile, fileType, ownerId);
    }

    public void update(UploadFile newUploadFile) {
        this.uploadFile = newUploadFile;
    }
}
