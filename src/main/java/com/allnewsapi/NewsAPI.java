package com.allnewsapi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * AllNewsAPI SDK - A simple Java wrapper for AllNewsAPI
 * @author AllNewsAPI
 * @license MIT
 * @description This SDK provides a simple interface to interact with the AllNewsAPI, 
 *              allowing you to search for news articles based on various parameters.
 * @see https://allnewsapi.com/docs for the API documentation.
 * @version 1.0.0
 */
public class NewsAPI {
    private String apiKey;
    private String baseUrl;
    private String searchEndpoint;
    private String headlinesEndpoint;

    /**
     * Create a new instance of the NewsAPI client with default configuration
     * @param apiKey - Your AllNewsAPI key
     */
    public NewsAPI(String apiKey) {
        this(apiKey, new NewsAPIConfig());
    }

    /**
     * Create a new instance of the NewsAPI client with custom configuration
     * @param apiKey - Your AllNewsAPI key
     * @param config - Configuration options
     */
    public NewsAPI(String apiKey, NewsAPIConfig config) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("API key is required");
        }

        this.apiKey = apiKey;
        this.baseUrl = config.getBaseUrl();
        this.searchEndpoint = this.baseUrl + "/v1/search";
        this.headlinesEndpoint = this.baseUrl + "/v1/headlines";
    }

    /**
     * Build the URL with query parameters for the API request
     * @param endpoint - The API endpoint to use
     * @param params - Query parameters for the request
     * @return The complete URL for the API request
     * @throws URISyntaxException if the URL is malformed
     */
    private String buildUrl(String endpoint, Map<String, Object> params) throws URISyntaxException {
        StringBuilder urlBuilder = new StringBuilder(endpoint);
        urlBuilder.append("?apikey=").append(URLEncoder.encode(apiKey, StandardCharsets.UTF_8));
        
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            if (value != null) {
                urlBuilder.append("&").append(key).append("=");
                
                if (value instanceof List) {
                    List<?> list = (List<?>) value;
                    StringJoiner joiner = new StringJoiner(",");
                    for (Object item : list) {
                        if (item != null) {
                            joiner.add(item.toString());
                        }
                    }
                    urlBuilder.append(URLEncoder.encode(joiner.toString(), StandardCharsets.UTF_8));
                } else {
                    urlBuilder.append(URLEncoder.encode(value.toString(), StandardCharsets.UTF_8));
                }
            }
        }
        
        return new URI(urlBuilder.toString()).toString();
    }

    /**
     * Make a request to the API
     * @param endpoint - The API endpoint to use
     * @param params - Query parameters for the request
     * @return The API response as a String
     * @throws NewsAPIException if the request fails
     */
    private String makeRequest(String endpoint, Map<String, Object> params) throws NewsAPIException {
        HttpURLConnection connection = null;
        
        try {
            String url = buildUrl(endpoint, params);
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            int status = connection.getResponseCode();
            
            if (status >= 200 && status < 300) {
                return readResponse(connection.getInputStream());
            } else {
                String errorMessage = readResponse(connection.getErrorStream());
                throw new NewsAPIException(status, errorMessage.isEmpty() ? getErrorMessage(status) : errorMessage);
            }
        } catch (IOException | URISyntaxException e) {
            throw new NewsAPIException(500, "Request failed: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Read response from an input stream
     * @param inputStream - The input stream to read from
     * @return The response as a String
     */
    private String readResponse(InputStream inputStream) {
        if (inputStream == null) {
            return "";
        }
        
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    /**
     * Get an error message based on status code
     * @param statusCode - HTTP status code
     * @return Error message
     */
    private String getErrorMessage(int statusCode) {
        Map<Integer, String> errorMessages = new HashMap<>();
        errorMessages.put(400, "Bad Request - Your request is invalid");
        errorMessages.put(401, "Unauthorized - Invalid API Key or Account status is inactive");
        errorMessages.put(403, "Forbidden - Your account is not authorized to make that request");
        errorMessages.put(429, "Too Many Requests - You have reached your daily request limit. The next reset is at 00:00 UTC");
        errorMessages.put(500, "Internal Server Error - We had a problem with our server. Please try again later");
        errorMessages.put(503, "Service Unavailable - We're temporarily offline for maintenance. Please try again later");
        
        return errorMessages.getOrDefault(statusCode, "Unknown error occurred");
    }

    /**
     * Search for news articles
     * @param options - Search options as a Map
     * @return Search results as a String (JSON by default)
     * @throws NewsAPIException if the request fails
     */
    public String search(Map<String, Object> options) throws NewsAPIException {
        // Convert Date objects to ISO format strings
        Map<String, Object> processedOptions = new HashMap<>(options);
        
        if (options.containsKey("startDate") && options.get("startDate") instanceof Date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            processedOptions.put("startDate", dateFormat.format((Date) options.get("startDate")));
        }
        
        if (options.containsKey("endDate") && options.get("endDate") instanceof Date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            processedOptions.put("endDate", dateFormat.format((Date) options.get("endDate")));
        }
        
        return makeRequest(searchEndpoint, processedOptions);
    }

    /**
     * Get news headlines
     * @param options - Search options as a Map (same parameters as search)
     * @return Headlines results as a String (JSON by default)
     * @throws NewsAPIException if the request fails
     */
    public String headlines(Map<String, Object> options) throws NewsAPIException {
        // Convert Date objects to ISO format strings
        Map<String, Object> processedOptions = new HashMap<>(options);
        
        if (options.containsKey("startDate") && options.get("startDate") instanceof Date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            processedOptions.put("startDate", dateFormat.format((Date) options.get("startDate")));
        }
        
        if (options.containsKey("endDate") && options.get("endDate") instanceof Date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            processedOptions.put("endDate", dateFormat.format((Date) options.get("endDate")));
        }
        
        return makeRequest(headlinesEndpoint, processedOptions);
    }

    /**
     * Configuration class for NewsAPI
     */
    public static class NewsAPIConfig {
        private String baseUrl;
        
        /**
         * Create a new instance of NewsAPIConfig with default values
         */
        public NewsAPIConfig() {
            this.baseUrl = "https://api.allnewsapi.com";
        }
        
        /**
         * Set the base URL for the API
         * @param baseUrl - The base URL
         * @return This config instance
         */
        public NewsAPIConfig setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }
        
        /**
         * Get the base URL for the API
         * @return The base URL
         */
        public String getBaseUrl() {
            return baseUrl;
        }
    }
}