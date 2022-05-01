import com.freenewsapi.NewsAPI;
import com.freenewsapi.NewsAPIException;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FreeNewsAPIExample {
    public static void main(String[] args) {
        // Replace with your actual API key
        String apiKey = "your_api_key_here";
        
        try {
            // Initialize the API client
            NewsAPI newsAPI = new NewsAPI(apiKey);
            
            // Create search options as a Map (similar to JavaScript object)
            Map<String, Object> options = new HashMap<>();
            options.put("q", "climate change");
            options.put("lang", "en");
            options.put("max", 10);
            options.put("sortby", "publishedAt");
            options.put("category", Arrays.asList("environment", "science"));
            options.put("startDate", "2023-01-01");
            options.put("content", true);
            
            // Perform the search
            String results = newsAPI.search(options);
            
            // Print the results
            System.out.println("Search Results:");
            System.out.println(results);
            
        } catch (NewsAPIException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Status Code: " + e.getStatusCode());
        }
    }
}