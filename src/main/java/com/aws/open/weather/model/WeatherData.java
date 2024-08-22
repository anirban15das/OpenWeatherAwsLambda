package com.aws.open.weather.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherData {

    private String id;

    private String cityName;

    private String cityId;

    private double temp;

    private double feelsLike;

    private double humidity;

    private double pressure;

    private double windSpeed;


    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public static String tableName(){
        return "WeatherData";
    }
}
