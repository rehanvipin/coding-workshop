import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

public class WeatherForecast {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final OkHttpClient client = new OkHttpClient();
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) throws Exception {
        var places = loadPlaces();
        var weatherCodes = loadWeatherCodes();
        
        System.out.println("Weather Forecast App");
        System.out.println("Find the weather in a city of your choice.");
        System.out.print("Please enter the name of the city: ");
        var cityName = scanner.nextLine().strip().toLowerCase();
        
        var place = findPlace(places, cityName);
        if (place == null) {
            System.out.println("Could not find the place");
            System.exit(1);
        }
        
        if (!place.has("location")) {
            System.out.println("Could not find location of place");
            System.exit(1);
        }
        
        var location = place.get("location");
        var coords = new double[]{location.get(0).asDouble(), location.get(1).asDouble()};
        
        var weatherData = fetchWeatherData(coords);
        displayForecast(weatherData, weatherCodes);
    }
    
    private static List<JsonNode> loadPlaces() throws IOException {
        return Files.lines(Path.of("../data/place-city.ndjson"))
                   .map(line -> {
                       try { return mapper.readTree(line); }
                       catch (Exception e) { throw new RuntimeException(e); }
                   })
                   .toList();
    }
    
    private static Map<String, JsonNode> loadWeatherCodes() throws IOException {
        var json = mapper.readTree(Path.of("../data/weather-codes.json").toFile());
        var codes = new HashMap<String, JsonNode>();
        json.fields().forEachRemaining(entry -> codes.put(entry.getKey(), entry.getValue()));
        return codes;
    }
    
    private static JsonNode findPlace(List<JsonNode> places, String searchName) {
        return places.stream()
                    .filter(place -> matchesPlace(place, searchName))
                    .findFirst()
                    .orElse(null);
    }
    
    private static boolean matchesPlace(JsonNode place, String searchName) {
        // Check main name
        if (place.has("name") && !place.get("name").isNull()) {
            if (place.get("name").asText().toLowerCase().contains(searchName)) {
                return true;
            }
        }
        
        // Check other names
        if (place.has("other_names") && !place.get("other_names").isNull()) {
            for (var otherName : place.get("other_names")) {
                if (otherName.asText().toLowerCase().contains(searchName)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private static JsonNode fetchWeatherData(double[] coords) throws IOException {
        var url = "https://api.open-meteo.com/v1/forecast?latitude=%.6f&longitude=%.6f&daily=weather_code,temperature_2m_max,temperature_2m_min&timezone=Asia%%2FCalcutta&temporal_resolution=hourly_6"
                .formatted(coords[0], coords[1]);
        
        var request = new Request.Builder().url(url).build();
        
        try (var response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response: " + response);
            }
            return mapper.readTree(response.body().string());
        }
    }
    
    private static void displayForecast(JsonNode weatherData, Map<String, JsonNode> weatherCodes) {
        System.out.println();
        
        var daily = weatherData.get("daily");
        var times = daily.get("time");
        var codes = daily.get("weather_code");
        var minTemps = daily.get("temperature_2m_min");
        var maxTemps = daily.get("temperature_2m_max");
        
        for (int i = 0; i < times.size(); i++) {
            var day = times.get(i).asText();
            var weatherCode = codes.get(i).asInt();
            var minTemp = minTemps.get(i).asDouble();
            var maxTemp = maxTemps.get(i).asDouble();
            
            System.out.println("Forecast for day: " + day);
            
            var description = weatherCodes.containsKey(String.valueOf(weatherCode))
                ? weatherCodes.get(String.valueOf(weatherCode)).get("description").asText()
                : "Unknown atmosphere";
            
            System.out.println("Atmosphere: " + description);
            System.out.println("Min Temperature: " + minTemp);
            System.out.println("Max Temperature: " + maxTemp);
            System.out.println();
        }
    }
}

// Alternative record-based approach for better data modeling (Java 17+)
record Place(String name, List<String> otherNames, double[] location) {}
record WeatherCode(String description) {}
record DailyForecast(String date, String atmosphere, double minTemp, double maxTemp) {}