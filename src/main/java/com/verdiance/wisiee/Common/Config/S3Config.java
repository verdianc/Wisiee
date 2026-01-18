package com.verdiance.wisiee.Common.Config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@Configuration
public class S3Config {
    @Value("${s3.region:ap-northeast-2}")
    private String region;

    @Value("${s3.endpoint:}")          // 빈 문자열이면 AWS
    private String endpoint;

    @Value("${s3.access-key:}")        // MinIO 때만 사용
    private String accessKey;

    @Value("${s3.secret-key:}")        // MinIO 때만 사용
    private String secretKey;

    @Value("${s3.path-style:true}")    // MinIO=true, AWS는 false여도 동작
    private boolean pathStyle;

    // s3 client 객체 주입
    @Bean
    public software.amazon.awssdk.services.s3.S3Client s3Client() {
        software.amazon.awssdk.auth.credentials.AwsCredentialsProvider creds = credentials();
        software.amazon.awssdk.services.s3.S3Configuration s3cfg =
                software.amazon.awssdk.services.s3.S3Configuration.builder()
                        .pathStyleAccessEnabled(pathStyle)
                        .build();

        var builder = software.amazon.awssdk.services.s3.S3Client.builder()
                .region(software.amazon.awssdk.regions.Region.of(region))
                .serviceConfiguration(s3cfg)
                .credentialsProvider(creds);

        if (!endpoint.isBlank()) {
            builder = builder.endpointOverride(java.net.URI.create(endpoint));
        }
        return builder.build();
    }

    // s3 url
    @Bean
    public software.amazon.awssdk.services.s3.presigner.S3Presigner s3Presigner() {
        software.amazon.awssdk.auth.credentials.AwsCredentialsProvider creds = credentials();
        var builder = software.amazon.awssdk.services.s3.presigner.S3Presigner.builder()
                .region(software.amazon.awssdk.regions.Region.of(region))
                .credentialsProvider(creds)
                .serviceConfiguration(software.amazon.awssdk.services.s3.S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build());
        if (!endpoint.isBlank()) {
            builder = builder.endpointOverride(java.net.URI.create(endpoint));
        }
        return builder.build();
    }

    // credentials helper
    private AwsCredentialsProvider credentials() {
        // endpoint가 있으면 MinIO로 간주 → 정적 키 사용
        if (!endpoint.isBlank() && !accessKey.isBlank() && !secretKey.isBlank()) {
            return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
        }
        // 운영(AWS): 기본 자격증명 체인(IAM Role/Env/Shared config 등)
        return DefaultCredentialsProvider.create();
    }
}
