package com.verdianc.wisiee.Infrastructure.S3;

import java.time.Duration;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Adapter implements S3Port{


  private final S3Client s3;
  private final S3Presigner presigner;

  @Value("${s3.bucket}")
  private String bucket;

  @Override
  public String bucket() {
    return bucket;
  }

  @Override
  public PutResult put(String objectKey, byte[] data, String contentType, Map<String, String> metadata) {
    PutObjectRequest req = PutObjectRequest.builder()
        .bucket(bucket)
        .key(objectKey)
        .contentType(contentType == null ? "application/octet-stream" : contentType)
        .contentLength((long) data.length)
        .metadata(metadata == null ? Map.of() : metadata)
        .build();

    PutObjectResponse res = s3.putObject(req, RequestBody.fromBytes(data));
    return new PutResult(res.eTag(), res.versionId(), data.length);
  }

  @Override
  public HeadResult head(String objectKey, String versionId) {
    HeadObjectRequest.Builder b = HeadObjectRequest.builder()
        .bucket(bucket)
        .key(objectKey);
    if (versionId != null && !versionId.isBlank()) b.versionId(versionId);

    HeadObjectResponse h = s3.headObject(b.build());
    return new HeadResult(h.eTag(), h.versionId(), h.contentLength(), h.contentType());
  }

  @Override
  public void delete(String objectKey, String versionId) {
    DeleteObjectRequest.Builder b = DeleteObjectRequest.builder()
        .bucket(bucket)
        .key(objectKey);
    if (versionId != null && !versionId.isBlank()) b.versionId(versionId);

    s3.deleteObject(b.build());
  }

  @Override
  public String presignGet(String objectKey, String versionId, Duration ttl) {
    var get = GetObjectRequest.builder()
        .bucket(bucket)
        .key(objectKey);
    if (versionId != null && !versionId.isBlank()) get.versionId(versionId);

    var presign = GetObjectPresignRequest.builder()
        .signatureDuration(ttl == null ? Duration.ofMinutes(10) : ttl)
        .getObjectRequest(get.build())
        .build();

    return presigner.presignGetObject(presign).url().toString();
  }

}
