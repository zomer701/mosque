package com.live.mosque.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.extensions.VersionedRecordExtension;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;


@Configuration
@Slf4j
public class DynamoDbConfiguration {

    @Value("${dbAccessKey}")
    private String dbAccessKey;

    @Value("${dbSecretKey}")
    private String dbSecretKey;

    @Bean
    public DynamoDbAsyncClient getDynamoDbAsyncClient() {
        return DynamoDbAsyncClient.builder()
                .region(Region.US_EAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(dbAccessKey, dbSecretKey)))
                .endpointOverride(URI.create("https://dynamodb.us-east-2.amazonaws.com"))
                .build();

    }

    @Bean
    public DynamoDbEnhancedAsyncClient getDynamoDbEnhancedClient() {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(getDynamoDbAsyncClient())
                .extensions(VersionedRecordExtension.builder().build())
                .build();
    }
}
