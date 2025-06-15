package com.boot.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class Integracoes {

    @Value("${s3.accessKey}")
    private String accessKeyS3;

    @Value("${s3.secretKey}")
    private String secretKeyS3;

    @Value("${s3.region}")
    private String regionS3;

    @Value("${s3.endpoint:#{null}}")
    private String endpointS3;

    @Bean
    public S3Client getS3Client() {
        if (this.endpointS3 == null || "nulo".equals(this.endpointS3)) {
            return S3Client.builder()
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(this.accessKeyS3, this.secretKeyS3)))
                    .region(Region.of(this.regionS3))
                    .build();
        } else {
            return S3Client.builder()
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(this.accessKeyS3, this.secretKeyS3)))
                    .endpointOverride(URI.create(this.endpointS3))
                    .region(Region.of(this.regionS3))
                    .serviceConfiguration(
                            S3Configuration.builder().chunkedEncodingEnabled(false).build()
                    )
                    .build();


        }
    }
}
