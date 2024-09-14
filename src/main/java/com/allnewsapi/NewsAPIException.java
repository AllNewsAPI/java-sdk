package com.allnewsapi;

/**
 * Custom exception class for NewsAPI errors
 */
public class NewsAPIException extends Exception {
    private final int statusCode;
    
    /**
     * Create a new NewsAPIException
     * @param statusCode - HTTP status code
     * @param message - Error message
     */
    public NewsAPIException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
    
    /**
     * Get the HTTP status code
     * @return The HTTP status code
     */
    public int getStatusCode() {
        return statusCode;
    }
}