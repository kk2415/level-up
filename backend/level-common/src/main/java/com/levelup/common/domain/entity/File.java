package com.levelup.common.domain.entity;

import com.levelup.common.domain.base.BaseTimeEntity;
import com.levelup.common.util.file.UploadFile;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Table(name = "file")
@Entity
public class File extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Embedded
    @Column(nullable = false)
    private UploadFile uploadFile;

    public static File createFile(Object object, UploadFile uploadFile) {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof File)) return false;
        return id != null && id.equals(((File) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
