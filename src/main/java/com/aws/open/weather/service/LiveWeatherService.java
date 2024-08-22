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
        try {
            JsonNode jsonNode = objectMapper.readTree(weatherResp);
            JsonNode mainNode = jsonNode.get("main");
            JsonNode windNode = jsonNode.get("wind");
            weather.setCityName(jsonNode.path("name").asText());
            weather.setTemp(mainNode.path("temp").asDouble());
            weather.setCityId(jsonNode.path("id").asText());
            weather.setPressure(mainNode.path("pressure").asDouble());
            weather.setHumidity(mainNode.path("humidity").asDouble());
            weather.setFeelsLike(mainNode.path("feels_like").asDouble());
            weather.setWindSpeed(windNode.path("speed").asDouble());
            weather.setId((UUID.randomUUID().toString()));
            LOGGER.info("Weather: {}", weather);
        } catch (Exception ex) {
            LOGGER.error("Error while parsing weather response", ex);
        }
        weatherRepository.create(weather);
        return weather;
    }
}
