package com.weather.weatherassistant.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.weatherassistant.client.OpenMeteoClient;
import com.weather.weatherassistant.dto.LocationResponse;
import com.weather.weatherassistant.dto.WeatherResponse;

@Service
public class WeatherService {
	private final OpenMeteoClient openMeteoClient;
	private final GeocodingService geocodingService;
	private final ObjectMapper objectMapper;

	public WeatherService(OpenMeteoClient openMeteoClient, GeocodingService geocodingService,
			ObjectMapper objectMapper) {

		this.openMeteoClient = openMeteoClient;
		this.geocodingService = geocodingService;
		this.objectMapper = objectMapper;
	}

	public WeatherResponse getCurrentWeather(String city) throws IOException, InterruptedException {

		// Step 1: Get city coordinates
		LocationResponse location = geocodingService.getLocation(city);

		// Step 2: Fetch weather using coordinates
		String response = openMeteoClient.getCurrentWeather(location.latitude(), location.longitude());

		// Step 3: Parse JSON response
		JsonNode root = objectMapper.readTree(response);

		JsonNode current = root.path("current");

		if (current.isMissingNode()) {
			throw new IOException("Current weather data not found");
		}

		// Step 4: Extract weather details
		double temperature = current.path("temperature_2m").asDouble();

		double feelsLike = current.path("apparent_temperature").asDouble();

		int humidity = current.path("relative_humidity_2m").asInt();

		double windSpeed = current.path("wind_speed_10m").asDouble();

		int weatherCode = current.path("weather_code").asInt();

		// Step 5: Convert weather code into condition
		String condition = getWeatherCondition(weatherCode);

		// Step 6: Return WeatherResponse
		return new WeatherResponse(location.name(), location.country(), temperature, feelsLike, humidity, windSpeed,
				weatherCode, condition);
	}

	private String getWeatherCondition(int code) {

		return switch (code) {
		case 0 -> "Clear sky";
		case 1 -> "Mainly clear";
		case 2 -> "Partly cloudy";
		case 3 -> "Overcast";
		case 45, 48 -> "Fog";
		case 51, 53, 55 -> "Drizzle";
		case 56, 57 -> "Freezing drizzle";
		case 61, 63, 65 -> "Rain";
		case 66, 67 -> "Freezing rain";
		case 71, 73, 75 -> "Snowfall";
		case 77 -> "Snow grains";
		case 80, 81, 82 -> "Rain showers";
		case 85, 86 -> "Snow showers";
		case 95 -> "Thunderstorm";
		case 96, 99 -> "Thunderstorm with hail";
		default -> "Unknown weather condition";
		};
	}

}
