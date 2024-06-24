package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;

@ApplicationScoped
public class AwsHelper {

    @ConfigProperty(name = "aws.notificationProtocol")
    String notificationProtocol;
    @ConfigProperty(name = "aws.s3BucketName")
    String s3BucketName;
    @ConfigProperty(name = "aws.s3ContentType")
    String s3ContentType;
    @ConfigProperty(name = "aws.topicArn")
    String topicArn;
    @ConfigProperty(name = "aws.notificationEmail")
    String notificationEmail;
    @ConfigProperty(name = "aws.region")
    String region;
    @ConfigProperty(name = "aws.accessKey")
    String accessKey;
    @ConfigProperty(name = "aws.secretAccessKey")
    String secretAccessKey;

    public S3Client buildS3Client() {
        return S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretAccessKey)))
            .build();
    }

    public SnsClient buildSnsClient() {
        return SnsClient.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretAccessKey)))
            .build();
    }

    public PublishRequest buildSnsPublishRequest(String message) {
        return PublishRequest.builder()
            .message(message)
            .topicArn(topicArn)
            .build();
    }

    public SubscribeRequest buildSnsSubscribeRequest() {
        return SubscribeRequest.builder()
            .protocol(notificationProtocol)
            .endpoint(notificationEmail)
            .returnSubscriptionArn(true)
            .topicArn(topicArn)
            .build();
    }

    public PutObjectRequest buildPutObjectRequest(String fileName) {
        return PutObjectRequest.builder()
            .bucket(s3BucketName)
            .key(fileName)
            .contentType(s3ContentType)
            .build();
    }

    public GetObjectRequest buildGetObjectRequest(String fileName) {
        return GetObjectRequest.builder()
            .bucket(s3BucketName)
            .key(fileName)
            .build();
    }

    public GetObjectRequest buildGetObjectRequest(String fileName, String bucketName) {
        return GetObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .build();
    }

}
