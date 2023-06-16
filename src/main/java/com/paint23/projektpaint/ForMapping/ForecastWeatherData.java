package com.paint23.projektpaint.ForMapping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ForecastWeatherData {
    @JsonProperty("cod")
    private String cod;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }

    public void setWeatherDataList(List<WeatherData> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @JsonProperty("message")
    private int message;

    @JsonProperty("cnt")
    private int count;

    @JsonProperty("list")
    private List<WeatherData> weatherDataList;

    @JsonProperty("city")
    private City city;

    // Pozostałe pola i gettery/settery

    public static class WeatherData {
        @JsonProperty("dt")
        private long dt;

        @JsonProperty("main")
        private MainData mainData;

        @JsonProperty("weather")
        private List<Weather> weatherList;

        @JsonProperty("clouds")
        private Clouds clouds;

        @JsonProperty("wind")
        private Wind wind;

        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }

        public MainData getMainData() {
            return mainData;
        }

        public void setMainData(MainData mainData) {
            this.mainData = mainData;
        }

        public List<Weather> getWeatherList() {
            return weatherList;
        }

        public void setWeatherList(List<Weather> weatherList) {
            this.weatherList = weatherList;
        }

        public Clouds getClouds() {
            return clouds;
        }

        public void setClouds(Clouds clouds) {
            this.clouds = clouds;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public int getVisibility() {
            return visibility;
        }

        public void setVisibility(int visibility) {
            this.visibility = visibility;
        }

        public double getPop() {
            return pop;
        }

        public void setPop(double pop) {
            this.pop = pop;
        }

        public Rain getRain() {
            return rain;
        }

        public void setRain(Rain rain) {
            this.rain = rain;
        }

        public Sys getSys() {
            return sys;
        }

        public void setSys(Sys sys) {
            this.sys = sys;
        }

        public String getDtTxt() {
            return dtTxt;
        }

        public void setDtTxt(String dtTxt) {
            this.dtTxt = dtTxt;
        }

        @JsonProperty("visibility")
        private int visibility;

        @JsonProperty("pop")
        private double pop;

        @JsonProperty("rain")
        private Rain rain;

        @JsonProperty("sys")
        private Sys sys;

        @JsonProperty("dt_txt")
        private String dtTxt;

        // Pozostałe pola i gettery/settery

        public static class MainData {
            @JsonProperty("temp")
            private double temp;

            public double getTemp() {
                return temp;
            }

            public void setTemp(double temp) {
                this.temp = temp;
            }

            public double getFeelsLike() {
                return feelsLike;
            }

            public void setFeelsLike(double feelsLike) {
                this.feelsLike = feelsLike;
            }

            public double getTempMin() {
                return tempMin;
            }

            public void setTempMin(double tempMin) {
                this.tempMin = tempMin;
            }

            public double getTempMax() {
                return tempMax;
            }

            public void setTempMax(double tempMax) {
                this.tempMax = tempMax;
            }

            public int getPressure() {
                return pressure;
            }

            public void setPressure(int pressure) {
                this.pressure = pressure;
            }

            public int getSeaLevel() {
                return seaLevel;
            }

            public void setSeaLevel(int seaLevel) {
                this.seaLevel = seaLevel;
            }

            public int getGrndLevel() {
                return grndLevel;
            }

            public void setGrndLevel(int grndLevel) {
                this.grndLevel = grndLevel;
            }

            public int getHumidity() {
                return humidity;
            }

            public void setHumidity(int humidity) {
                this.humidity = humidity;
            }

            public double getTempKf() {
                return tempKf;
            }

            public void setTempKf(double tempKf) {
                this.tempKf = tempKf;
            }

            @JsonProperty("feels_like")
            private double feelsLike;

            @JsonProperty("temp_min")
            private double tempMin;

            @JsonProperty("temp_max")
            private double tempMax;

            @JsonProperty("pressure")
            private int pressure;

            @JsonProperty("sea_level")
            private int seaLevel;

            @JsonProperty("grnd_level")
            private int grndLevel;

            @JsonProperty("humidity")
            private int humidity;

            @JsonProperty("temp_kf")
            private double tempKf;

            // Gettery/settery
        }

        public static class Weather {
            @JsonProperty("id")
            private int id;

            @JsonProperty("main")
            private String main;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            @JsonProperty("description")
            private String description;

            @JsonProperty("icon")
            private String icon;

            // Gettery/settery
        }

        public static class Clouds {
            @JsonProperty("all")
            private int all;

            public int getAll() {
                return all;
            }

            public void setAll(int all) {
                this.all = all;
            }
// Gettery/settery
        }

        public static class Wind {
            @JsonProperty("speed")
            private double speed;

            public double getSpeed() {
                return speed;
            }

            public void setSpeed(double speed) {
                this.speed = speed;
            }

            public int getDeg() {
                return deg;
            }

            public void setDeg(int deg) {
                this.deg = deg;
            }

            public double getGust() {
                return gust;
            }

            public void setGust(double gust) {
                this.gust = gust;
            }

            @JsonProperty("deg")
            private int deg;

            @JsonProperty("gust")
            private double gust;

            // Gettery/settery
        }

        public static class Rain {
            public double getRainfall() {
                return rainfall;
            }

            public void setRainfall(double rainfall) {
                this.rainfall = rainfall;
            }

            @JsonProperty("3h")
            private double rainfall;

            // Gettery/settery
        }

        public static class Sys {
            public String getPod() {
                return pod;
            }

            public void setPod(String pod) {
                this.pod = pod;
            }

            @JsonProperty("pod")
            private String pod;

            // Gettery/settery
        }

        // Dodaj pozostałe wewnętrzne klasy dla pozostałych obiektów JSON-a
    }

    public static class City {
        @JsonProperty("id")
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Coordinates getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }

        public int getTimezone() {
            return timezone;
        }

        public void setTimezone(int timezone) {
            this.timezone = timezone;
        }

        public long getSunrise() {
            return sunrise;
        }

        public void setSunrise(long sunrise) {
            this.sunrise = sunrise;
        }

        public long getSunset() {
            return sunset;
        }

        public void setSunset(long sunset) {
            this.sunset = sunset;
        }

        @JsonProperty("name")
        private String name;

        @JsonProperty("coord")
        private Coordinates coordinates;

        @JsonProperty("country")
        private String country;

        @JsonProperty("population")
        private int population;

        @JsonProperty("timezone")
        private int timezone;

        @JsonProperty("sunrise")
        private long sunrise;

        @JsonProperty("sunset")
        private long sunset;

        // Gettery/settery

        public static class Coordinates {
            @JsonProperty("lat")
            private double latitude;

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            @JsonProperty("lon")
            private double longitude;

            // Gettery/settery
        }
    }
}
