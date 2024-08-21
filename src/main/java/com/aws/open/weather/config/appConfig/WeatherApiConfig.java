package com.aws.open.weather.config.appConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class WeatherApiConfig {


    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/weather")
                .build();
    }
}
