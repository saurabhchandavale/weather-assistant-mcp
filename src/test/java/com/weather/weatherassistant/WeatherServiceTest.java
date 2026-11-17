package com.weather.weatherassistant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.weather.weatherassistant.dto.WeatherResponse;
import com.weather.weatherassistant.service.WeatherService;

@SpringBootTest
public class WeatherServiceTest {
	@Autowired
    private WeatherService weatherService;
	
	@Test
    void testCurrentWeather() throws Exception {

        WeatherResponse weather =
                weatherService.getCurrentWeather("Pune");

        System.out.println("Weather Details: " + weather);
    }
}
