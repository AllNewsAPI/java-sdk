import com.allnewsapi.NewsAPI;
import com.allnewsapi.NewsAPIException;

import java.util.HashMap;
import java.util.Map;

/**
 * Example demonstrating the headlines functionality of the AllNewsAPI Java SDK
 */
public class HeadlinesExample {
    public static void main(String[] args) {
        String apiKey = "your_api_key_here";

        try {
            NewsAPI newsAPI = new NewsAPI(apiKey);

            // Example 1: Get general headlines
            System.out.println("=== General Headlines ===");
            Map<String, Object> options = new HashMap<>();
            options.put("lang", "en");
            options.put("country", "us");
            
            String headlines = newsAPI.headlines(options);
            System.out.println(headlines);
            System.out.println();

            // Example 2: Get technology headlines
            System.out.println("=== Technology Headlines ===");
            options.put("category", "technology");
            
            String techHeadlines = newsAPI.headlines(options);
            System.out.println(techHeadlines);
            System.out.println();

            // Example 3: Compare with search functionality
            System.out.println("=== Search Results for Comparison ===");
            Map<String, Object> searchOptions = new HashMap<>();
            searchOptions.put("q", "technology");
            searchOptions.put("lang", "en");
            
            String searchResults = newsAPI.search(searchOptions);
            System.out.println(searchResults);

        } catch (NewsAPIException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Status Code: " + e.getStatusCode());
        }
    }
}