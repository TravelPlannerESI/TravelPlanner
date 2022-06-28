package com.travelplan.global.entity.base;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseDateAndCreatorEntity extends BaseDateEntity {

    // 수정자 작성자 추가코드giota
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate
    private String lastModifiedBy;

}
