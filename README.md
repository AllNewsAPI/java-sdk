 
# FreeNewsAPI Java SDK

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

The official **FreeNewsAPI SDK** for the Java programming language. 

Easily access real-time and historical news articles and headlines from multiple sources around the world. 

---

## ✨ Features

- 🔍 Real-time and historical news articles
- 📰 Headlines from multiple sources around the world. 
- 🌍 Filter by language, country, region, category, publisher and more
- 🗓 Date range support
- 📄 Choose between `json`, `csv`, or `xlsx` formats
- ⚙️ Customizable configuration

---

## 🛠 Installation

### Maven

```xml
<dependency>
  <groupId>com.freenewsapi</groupId>
  <artifactId>freenewsapi-java-sdk</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'com.freenewsapi:freenewsapi-java-sdk:1.0.0'
```

---

## 🚀 Getting Started

> 📌 **Note:** You need an API key to use this SDK. [Sign up here](https://freenewsapi.com/signup) to get one for free.

### 🔹 Basic Search

```java
import com.freenewsapi.NewsAPI;
import com.freenewsapi.NewsAPIException;

import java.util.HashMap;
import java.util.Map;

public class Example {
    public static void main(String[] args) {
        String apiKey = "your_api_key_here";

        try {
            NewsAPI newsAPI = new NewsAPI(apiKey);

            Map<String, Object> options = new HashMap<>();
            options.put("q", "climate change"); 

            String results = newsAPI.search(options);
            System.out.println(results);

        } catch (NewsAPIException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```

---

### 🔹 Advanced Search

```java
import com.freenewsapi.NewsAPI;
import com.freenewsapi.NewsAPIException;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdvancedExample {
    public static void main(String[] args) {
        String apiKey = "your_api_key_here";

        try {
            NewsAPI newsAPI = new NewsAPI(apiKey);

            Map<String, Object> options = new HashMap<>();
            options.put("q", "artificial intelligence");
            options.put("lang", Arrays.asList("en", "de"));
            options.put("country", "us");
            options.put("category", Arrays.asList("technology", "business"));
            options.put("startDate", new Date());
            options.put("sortby", "publishedAt");
            options.put("content", true);
            options.put("format", "json");

            String results = newsAPI.search(options);
            System.out.println(results);

        } catch (NewsAPIException e) {
            e.printStackTrace();
        }
    }
}
```

---

## 📄 Available Search Parameters

| Parameter     | Type                  | Description                                                  |
|---------------|-----------------------|--------------------------------------------------------------|
| `q`           | `String`              | Keywords to search for                                       |
| `startDate`   | `String` or `Date`    | Start date (`YYYY-MM-DD` or `Date`)                          |
| `endDate`     | `String` or `Date`    | End date (`YYYY-MM-DD` or `Date`)                            |
| `content`     | `Boolean`             | Include full article content                                 |
| `lang`        | `String` or `List<String>` | Filter by language(s)                                 |
| `country`     | `String` or `List<String>` | Filter by country code(s) (ISO 3166-1 alpha-2)         |
| `region`      | `String` or `List<String>` | Filter by region(s)                                    |
| `category`    | `String` or `List<String>` | Filter by category (e.g. `technology`, `sports`)       |
| `max`         | `Integer`             | Max results to return                                 |
| `attributes`  | `String` or `List<String>` | Fields to search (`title`, `description`, `content`)  |
| `page`        | `Integer`             | Page number for pagination                                   |
| `sortby`      | `String`              | Sort by `publishedAt` or `relevance`                         |
| `publisher`   | `String` or `List<String>` | Filter by news source(s)                              |
| `format`      | `String`              | Response format: `json`, `csv`, `xlsx`                       |

 
---

## 📚 Documentation & Links

- [🌐 FreeNewsAPI Documentation](https://freenewsapi.com/documentation)
- [🐛 Report Issues](https://github.com/FreeNews-API/java-sdk/issues)
- [⭐ Star the SDK on GitHub](https://github.com/FreeNews-API/java-sdk)

---

## 🧾 License

Licensed under the [MIT License](LICENSE)
 