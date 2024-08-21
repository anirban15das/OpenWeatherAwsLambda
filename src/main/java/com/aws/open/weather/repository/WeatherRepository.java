package com.aws.open.weather.repository;

import com.aws.open.weather.exception.WeatherException;
import com.aws.open.weather.model.WeatherData;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Repository
@AllArgsConstructor
@NoArgsConstructor
public class WeatherRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherRepository.class);

    @Autowired
    private DynamoDbTable<WeatherData> weatherDataTable;

    public void create(WeatherData weatherData) {
        try {
            weatherDataTable.putItem(weatherData);
        }catch (Exception e){
            LOGGER.error("Exception in saving weather data ex = {}",e);
            throw new WeatherException("Exception in saving weather data",e);
        }
    }

}
