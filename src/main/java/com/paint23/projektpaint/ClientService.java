//package com.paint23.projektpaint;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.paint23.projektpaint.ForMapping.ForecastWeatherData;
//import com.paint23.projektpaint.ForMapping.PresentWeatherData;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.io.IOException;
////https://api.openweathermap.org/data/2.5/weather?lat=52.13&lon=21.00&appid=b694f4463f4b75fd3c6f63841c5d1d89
//@Service
//public class ClientService {
//
//    private final WebClient webClient;
//
////    @Value("${ProjektPaintApplication.apikey}")
//    private String apikey = "b694f4463f4b75fd3c6f63841c5d1d89";
//
//    public ClientService(WebClient.Builder webClientBuilder) {
//        webClient = webClientBuilder.baseUrl("https://api.openweathermap.org/").build();
////                .baseUrl("https://danepubliczne.imgw.pl/api/data/synop/format/json/station").build();
//    }
//
//
//
//    public Mono<String> danepubliczneRestCall(String name) {
//        return this.webClient.get()
//                .uri("/{name}", name)
//                .retrieve()
//                .bodyToMono(String.class);
//    }
//    public Mono<String> coordRestCall(String lon, String lat){
//        return this.webClient.get()
//                .uri("data/2.5/weather?lat={lat}&lon={lon}&appid={key}&units=metric", lat, lon, this.apikey)
//                .retrieve()
//                .bodyToMono(String.class);
//    }
//
//    public Mono<String> coordForescastRestCall(String lon, String lat){
//        return this.webClient.get()
//                .uri("data/2.5/forecast?lat={lat}&lon={lon}&appid={key}&units=metric", lat, lon, this.apikey)
//                .retrieve()
//                .bodyToMono(String.class);
//    }
//
//    public Mono<String> cityRestCall(String name){
//        return this.webClient.get()
//            .uri("geo/1.0/direct?q={name}&limit=5&appid={key}", name, this.apikey)
//            .retrieve()
//            .bodyToMono(String.class);
//    }
//    public PresentWeatherData presentDataMapping(String json) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        PresentWeatherData presentWeatherData = objectMapper.readValue(json, PresentWeatherData.class);
//        return presentWeatherData;
//    }
//
//    public ForecastWeatherData forecastDataMapping(String json) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        ForecastWeatherData forecastWeatherData = objectMapper.readValue(json, ForecastWeatherData.class);
//        System.out.println("Nazwa miasta: " + forecastWeatherData.getCity().getName());
//        return forecastWeatherData;
//    }
//
//}


package com.paint23.projektpaint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paint23.projektpaint.ForMapping.ForecastWeatherData;
import com.paint23.projektpaint.ForMapping.PresentWeatherData;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class ClientService {

    private final WebClient webClient;

    // API key for accessing the OpenWeatherMap API
    private String apikey = "b694f4463f4b75fd3c6f63841c5d1d89";

    public ClientService(WebClient.Builder webClientBuilder) {
        // Creating a WebClient instance with the base URL of the OpenWeatherMap API
        webClient = webClientBuilder.baseUrl("https://api.openweathermap.org/").build();
    }

    /**
     * Makes a REST call to retrieve data from the 'danepubliczne' endpoint.
     * @param name the name of the data to retrieve
     * @return a Mono containing the response body as a string
     */
    public Mono<String> danepubliczneRestCall(String name) {
        return this.webClient.get()
                .uri("/{name}", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * Makes a REST call to retrieve weather data for a specific location using coordinates.
     * @param lon the longitude of the location
     * @param lat the latitude of the location
     * @return a Mono containing the response body as a string
     */
    public Mono<String> coordRestCall(String lon, String lat){
        return this.webClient.get()
                .uri("data/2.5/weather?lat={lat}&lon={lon}&appid={key}&units=metric", lat, lon, this.apikey)
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * Makes a REST call to retrieve forecast weather data for a specific location using coordinates.
     * @param lon the longitude of the location
     * @param lat the latitude of the location
     * @return a Mono containing the response body as a string
     */
    public Mono<String> coordForescastRestCall(String lon, String lat){
        return this.webClient.get()
                .uri("data/2.5/forecast?lat={lat}&lon={lon}&appid={key}&units=metric", lat, lon, this.apikey)
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * Makes a REST call to retrieve city information based on the provided city name.
     * @param name the name of the city
     * @return a Mono containing the response body as a string
     */
    public Mono<String> cityRestCall(String name){
        return this.webClient.get()
                .uri("geo/1.0/direct?q={name}&limit=5&appid={key}", name, this.apikey)
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * Maps the JSON response to a PresentWeatherData object.
     * @param json the JSON response as a string
     * @return the mapped PresentWeatherData object
     * @throws IOException if there's an error during JSON parsing
     */
    public PresentWeatherData presentDataMapping(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PresentWeatherData presentWeatherData = objectMapper.readValue(json, PresentWeatherData.class);
        return presentWeatherData;
    }

    /**
     * Maps the JSON response to a ForecastWeatherData object.
     * @param json the JSON response as a string
     * @return the mapped ForecastWeatherData object
     * @throws IOException if there's an error during JSON parsing
     */
    public ForecastWeatherData forecastDataMapping(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ForecastWeatherData forecastWeatherData = objectMapper.readValue(json, ForecastWeatherData.class);
        System.out.println("City Name: " + forecastWeatherData.getCity().getName());
        return forecastWeatherData;
    }
}
