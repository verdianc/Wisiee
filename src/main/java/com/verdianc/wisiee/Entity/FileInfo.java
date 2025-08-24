package com.verdianc.wisiee.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "file")
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // S3 API에서는 영속적인 파일 식별자를 bucket + objectKey + versionId로 함
    private String bucket;

    private String objectKey;

    private String versionId;

    private Long size;

    private String name;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "form_id", nullable = false)
    private FormEntity form;

}
