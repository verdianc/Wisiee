package com.verdianc.wisiee.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "file")
public class FileEntity extends BaseEntity {

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "form_id", nullable = false)
    private FormEntity form;


    @Builder
    public FileEntity(String bucket,
        String objectKey,
        String versionId,
        Long size,
        String name,
        String description,
        FormEntity form) {
        this.bucket = bucket;
        this.objectKey = objectKey;
        this.versionId = versionId;
        this.size = size;
        this.name = name;
        this.description = description;
        this.form = form;
    }

}
