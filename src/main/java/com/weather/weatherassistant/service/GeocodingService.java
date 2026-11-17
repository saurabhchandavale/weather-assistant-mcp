package com.weather.weatherassistant.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.weatherassistant.client.*;
import com.weather.weatherassistant.dto.*;

@Service
public class GeocodingService {
	private final OpenMeteoClient openMeteoClient;
	private final ObjectMapper objectMapper;

	public GeocodingService(OpenMeteoClient openMeteoClient, ObjectMapper objectMapper) {

		this.openMeteoClient = openMeteoClient;
		this.objectMapper = objectMapper;
	}

	public LocationResponse getLocation(String city) throws IOException, InterruptedException {

		// Call Geocoding API
		String response = openMeteoClient.searchLocation(city);

		// Convert JSON String into JsonNode
		JsonNode root = objectMapper.readTree(response);

		// Extract first search result
		JsonNode result = root.path("results").path(0);

		if (result.isMissingNode()) {
			throw new IllegalArgumentException("City not found: " + city);
		}

		// Extract location details
		String name = result.path("name").asText();
		double latitude = result.path("latitude").asDouble();
		double longitude = result.path("longitude").asDouble();
		String country = result.path("country").asText();

		// Return location DTO
		return new LocationResponse(name, latitude, longitude, country);
	}

}
