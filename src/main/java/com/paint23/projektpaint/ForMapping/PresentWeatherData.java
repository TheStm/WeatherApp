package com.paint23.projektpaint.ForMapping;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PresentWeatherData {
    @JsonProperty("coord")
    private Coordinates coordinates;

    @JsonProperty("weather")
    private Weather[] weather;

    @JsonProperty("main")
    private MainData mainData;

    @JsonProperty("wind")
    private WindData windData;

    @JsonProperty("rain")
    private RainData rainData;

    @JsonProperty("clouds")
    private CloudsData cloudsData;

    private int visibility;

    private String base;

    private long dt;

    @JsonProperty("sys")
    private SysData sysData;

    private int timezone;

    private int id;
    private String name;
    private int cod;

    // Gettery i settery dla pól

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public MainData getMainData() {
        return mainData;
    }

    public void setMainData(MainData mainData) {
        this.mainData = mainData;
    }

    public WindData getWindData() {
        return windData;
    }

    public void setWindData(WindData windData) {
        this.windData = windData;
    }

    public RainData getRainData() {
        return rainData;
    }

    public void setRainData(RainData rainData) {
        this.rainData = rainData;
    }

    public CloudsData getCloudsData() {
        return cloudsData;
    }

    public void setCloudsData(CloudsData cloudsData) {
        this.cloudsData = cloudsData;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public SysData getSysData() {
        return sysData;
    }

    public void setSysData(SysData sysData) {
        this.sysData = sysData;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

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

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public static class Coordinates {
        private double lon;
        private double lat;

        // Gettery i settery dla pól

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }

    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

        // Gettery i settery dla pól

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
    }

    public static class MainData {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int humidity;
        private int sea_level;
        private int grnd_level;

        // Gettery i settery dla pól

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getFeels_like() {
            return feels_like;
        }

        public void setFeels_like(double feels_like) {
            this.feels_like = feels_like;
        }

        public double getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(double temp_min) {
            this.temp_min = temp_min;
        }

        public double getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(double temp_max) {
            this.temp_max = temp_max;
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public int getSea_level() {
            return sea_level;
        }

        public void setSea_level(int sea_level) {
            this.sea_level = sea_level;
        }

        public int getGrnd_level() {
            return grnd_level;
        }

        public void setGrnd_level(int grnd_level) {
            this.grnd_level = grnd_level;
        }
    }

    public static class WindData {
        private double speed;
        private int deg;
        private double gust;

        // Gettery i settery dla pól

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
    }

    public static class RainData {
        @JsonProperty("1h")
        private double rain1h;

        // Gettery i settery dla pól

        public double getRain1h() {
            return rain1h;
        }

        public void setRain1h(double rain1h) {
            this.rain1h = rain1h;
        }
    }

    public static class CloudsData {
        private int all;

        // Gettery i settery dla pól

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }
    }

    public static class SysData {
        private int type;
        private int id;
        private String country;
        private long sunrise;
        private long sunset;

        // Gettery i settery dla pól

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
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
    }
}
