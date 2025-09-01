package com.verdianc.wisiee.Infrastructure.S3;

import java.time.Duration;
import java.util.Map;

public interface S3Port {

  /** 설정된 기본 버킷명 */
  String bucket();

  /** 바이트 업로드(서버 업로드 방식) */
  PutResult put(String objectKey, byte[] data, String contentType, Map<String, String> metadata);

  /** 메타 조회(사이즈/콘텐츠타입/ETag/버전) */
  HeadResult head(String objectKey, String versionId);

  /** 객체 삭제 (versionId 없으면 최신) */
  void delete(String objectKey, String versionId);

  /** 다운로드용 프리사인 GET (선택) */
  String presignGet(String objectKey, String versionId, Duration ttl);

  // ===== 내부 결과 VO (서비스–어댑터 사이에서만 사용) =====
  public record PutResult(String eTag, String versionId, long size) {}
  public record HeadResult(String eTag, String versionId, long size, String contentType) {}
}
