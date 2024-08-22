package com.aws.open.weather.controller;

import com.aws.open.weather.exception.WeatherException;
import com.aws.open.weather.model.WeatherData;
import com.aws.open.weather.service.LiveWeatherService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@AllArgsConstructor
@NoArgsConstructor
public class LiveWeatherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveWeatherController.class);

    @Autowired
    private LiveWeatherService liveWeatherService;

    @SneakyThrows
    @GetMapping
    public ResponseEntity<WeatherData> getWeatherByCity(@RequestParam(name = "q") String city) throws WeatherException {
        LOGGER.info("Get Weather for City : {} ",city);
        return ResponseEntity.ok(liveWeatherService.getWeatherByCity(city));
    }
}
