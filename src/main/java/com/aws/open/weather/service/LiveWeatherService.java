package com.aws.open.weather.service;


import com.aws.open.weather.model.WeatherData;
import com.aws.open.weather.repository.WeatherRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class LiveWeatherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveWeatherService.class);
    @Autowired
    private RestClient restClient;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeatherRepository weatherRepository;


    public WeatherData getWeatherByCity(String city) {
        WeatherData weather = new WeatherData();
        String weatherResp = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/")
                        .queryParam("q", city)
                        .queryParam("units","metric")
                        .queryParam("appid", "d28f4704638f6046c536515574d04f4f")
                        .build())
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .toEntity(String.class)
                .getBody();

        LOGGER.info("Weather of City : {}",city);
        LOGGER.info("WeatherDataResp: {}", weatherResp);

        try {
            JsonNode jsonNode = objectMapper.readTree(weatherResp);
            LOGGER.info("jsonNode : {}", jsonNode.asText());
            JsonNode mainNode = jsonNode.get("main");
            LOGGER.info("mainNode : {}", mainNode.asText());
            JsonNode windNode = jsonNode.get("wind");
            LOGGER.info("windNode : {}", windNode.asText());
            weather.setCityName(jsonNode.path("name").asText());
            weather.setTemp(mainNode.path("temp").asDouble());
            weather.setCityId(jsonNode.path("id").asText());
            LOGGER.info("WeatherData : {}", weather);
            weather.setPressure(mainNode.path("pressure").asDouble());
            weather.setHumidity(mainNode.path("humidity").asDouble());
            LOGGER.info("WeatherData : {}", weather);
            weather.setFeelsLike(mainNode.path("feels_like").asDouble());
            weather.setWindSpeed(windNode.path("speed").asDouble());
            LOGGER.info("WeatherData : {}", weather);
            weather.setId((UUID.randomUUID().toString()));
            LOGGER.info("WeatherData : {}", weather);
        } catch (Exception ex) {
            LOGGER.error("Error while parsing weather response", ex);
        }
        weatherRepository.create(weather);
        return weather;
    }
}
