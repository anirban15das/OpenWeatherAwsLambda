package com.aws.open.weather.config.dynamodb;

import com.aws.open.weather.model.WeatherData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDbConfig {

    Region region = Region.US_EAST_1;

    @Bean
    public DynamoDbClient dynamoDbClient(){

        return DynamoDbClient.builder()
                .region(region).build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(){
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient())
                .build();
    }

    @Bean
    public DynamoDbTable<WeatherData> weatherDataTable(){
        return  dynamoDbEnhancedClient().table(WeatherData.tableName(), TableSchema.fromBean(WeatherData.class));
    }

}
