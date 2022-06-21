package com.levelup.core.domain.base;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity extends BaseTimeEntity {

    /**
     * 생성자, 수성자 정보작 필요없는 테이블은 BaseTimeEntity를 쓰고 필요한 테이블은 BaseEntityV3를 이용
     * */
    @Column(updatable = false)
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updateBy;

}
