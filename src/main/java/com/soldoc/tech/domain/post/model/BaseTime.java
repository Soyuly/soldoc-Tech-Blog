package com.soldoc.tech.domain.post.model;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)  //변경되었을 때 자등으로 기록되도록
public abstract class BaseTime {

    // 작성 날짜를 자동으로 생성해주는 어노테이션
    @CreatedDate
    @Column(name="CREATE_DATE", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    // 최근 수정 날짜를 자동으로 반영해준다.
    @LastModifiedDate
    @Column(name="UPDATE_DATE", nullable = false)
    private LocalDateTime modifiedDate;

}
