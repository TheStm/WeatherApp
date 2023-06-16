package com.paint23.projektpaint.model;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.paint23.projektpaint.email.token.ConfirmationToken;
import com.paint23.projektpaint.user.User;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The QuickStart class contains methods for importing data from CSV files into the MongoDB database and database operations.
 * @author agwiazda
 */
public class QuickStart {
    MongoDatabase database;
    MongoCollection<Document> collectionClients;
    MongoCollection<Document> collectionHistoricalWeatherData;
    String connectionString = "mongodb+srv://paint2023:d5mCt0UvW8B1BlUD@cluster0.z8chstk.mongodb.net/?retryWrites=true&w=majority";
    ServerApi serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build();
    MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .serverApi(serverApi)
            .build();
    MongoClient mongoClient = MongoClients.create(settings);
    /**
     * QuickStart class constructor. Creates a connection to the MongoDB database and initialises the collections.
     *
     * @throws MongoException if an error occurs while establishing a connection to the database
     */
    public QuickStart ()throws MongoException{
        CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase database = mongoClient.getDatabase("testdb").withCodecRegistry(pojoCodecRegistry);
        this.database = mongoClient.getDatabase("paint2023l").withCodecRegistry(pojoCodecRegistry); ;
        this.collectionClients = this.database.getCollection("clients");
        this.collectionHistoricalWeatherData = this.database.getCollection("historicalWeatherData");
    }
    /**
     * The importCSVData() method imports data from CSV files into the database.
     *
     * @throws IOException              if an error occurs while reading files
     * @throws CsvValidationException   if a CSV file validation error occurs
     */
    @EventListener(ApplicationReadyEvent.class)
    public void importCSVData()  throws IOException, CsvValidationException {
        String folderPath = "C:/Users/olagw/Documents/WeatherDataCSVFiles";
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".csv")) {
                    CSVReader reader = new CSVReader(new FileReader(file));
                    String[] row;
                    while ((row = reader.readNext()) != null) {
                        if (row[6].equals("8")) {
                            row[5] = null;
                        }
                        if (row[8].equals("8")) {
                            row[7] = null;
                        }
                        if (row[10].equals("8")) {
                            row[9] = null;
                        }
                        if (row[12].equals("8")) {
                            row[11] = null;
                        }
                        Document document = new Document("Kod stacji", row[0])
                                .append("Nazwa stacji", row[1])
                                .append("Rok", row[2])
                                .append("Miesiąc", row[3])
                                .append("Dzień", row[4])
                                .append("Średnia dobowa temperatura  [°C]", row[5])
                                .append("Średnia dobowa wilgotność względna [%]", row[7])
                                .append("Średnia dobowa prędkość wiatru [m/s]", row[9])
                                .append("Średnie dobowe zachmurzenie ogólne [oktanty]", row[11]);
                        this.collectionHistoricalWeatherData.insertOne(document);
                    }
                }
            }
        }
    }
    /**
     * The getHistoryRecord method retrieves records from the historicalWeatherData collection based on the given key and value.
     *
     * @param key      the key by which the record is to be searched
     * @param value    the value by which the record is to be searched
     * @param how_many number of records to be retrieved ('first' - first record, 'all' - all records, integer - the specified number of records)
     * @return JSON containing the records found or a message indicating that there are no records in the database
     */
    public String getHistoryRecord(String key, String value, String how_many) {
        Document query = new Document(key, value);
        String result = "";
        if (how_many.equals("first")) {
            Document document = this.collectionHistoricalWeatherData.find(query).first();
            result = document.toJson();
            return result;
        } else if (how_many.equals("all")) {
            StringBuilder jsonBuilder = new StringBuilder();
            for (Document doc : this.collectionHistoricalWeatherData.find(query)) {
                String json = doc.toJson();
                jsonBuilder.append(json);
            }
            result = jsonBuilder.toString();
            return result;
        } else if (Integer.parseInt(how_many) >= 1 && Integer.parseInt(how_many) <= this.collectionHistoricalWeatherData.countDocuments(query)) {
            StringBuilder jsonBuilder = new StringBuilder();
            for (Document doc : this.collectionHistoricalWeatherData.find(query).limit(Integer.parseInt(how_many))) {
                String json = doc.toJson();
                jsonBuilder.append(json);
            }
            result = jsonBuilder.toString();
            return result;
        }
        return "Nie znaleziono rekordów w bazie.";
    }
    /**
     * The getHistoryRecordByDate method retrieves records from the historicalWeatherData collection based on the date specified.
     *
     * @param key the key by which the record is to be searched.
     * @param value the value by which the record is to be searched.
     * @param valueYear the year for which the records are to be retrieved.
     * @param valueMonth month for which records should be retrieved.
     * @param valueDay the day for which the records should be retrieved.
     * @param how_many number of records to be retrieved ("first" - first record, "all" - all records, integer - the specified number of records).
     * @return JSON containing the found records or a message indicating that there are no records in the database.
     */
    public String getHistoryRecordByDate(String key, String value, String valueYear, String valueMonth, String valueDay, String how_many) {
        Document query = new Document();
        query.append(key, value);
        query.append("Rok", valueYear);
        query.append("Miesiąc", valueMonth);
        query.append("Dzień", valueDay);
        String result = "";
        if (how_many.equals("first")) {
            Document document = this.collectionHistoricalWeatherData.find(query).first();
            result = Objects.requireNonNull(document).toJson();
            return result;
        } else if (how_many.equals("all")) {
            StringBuilder jsonBuilder = new StringBuilder();
            for (Document doc : this.collectionHistoricalWeatherData.find(query)) {
                String json = doc.toJson();
                jsonBuilder.append(json);
            }
            result = jsonBuilder.toString();
            return result;
        } else if (Integer.parseInt(how_many) >= 1 && Integer.parseInt(how_many) <= this.collectionHistoricalWeatherData.countDocuments(query)) {
            StringBuilder jsonBuilder = new StringBuilder();
            for (Document doc : this.collectionHistoricalWeatherData.find(query).limit(Integer.parseInt(how_many))) {
                String json = doc.toJson();
                jsonBuilder.append(json);
            }
            result = jsonBuilder.toString();
            return result;
        }
        return "Nie znaleziono rekordów w bazie.";
    }
    /**
     * Retrieves historical weather records from the MongoDB collection based on the specified date interval.
     *
     * @param key The key used to search for the specific record.
     * @param value The value associated with the key to search for the specific record.
     * @param valueYearStart The start year of the date interval.
     * @param valueYearStop The end year of the date interval.
     * @param valueMonthStart The start month of the date interval.
     * @param valueMonthStop The end month of the date interval.
     * @param valueDayStart The start day of the date interval.
     * @param valueDayStop The end day of the date interval.
     * @param how_many Specifies the number of records to retrieve. Can be "first" (retrieve the first record), "all" (retrieve all matching records), or an integer specifying the maximum number of records to retrieve.
     * @return If records are found in the database, it returns a JSON string containing the retrieved records. If no records are found, it returns the string "Nie znaleziono rekordów w bazie." (No records found in the database).
     */
    public String getHistoryRecordByInterval(String key, String value, String valueYearStart, String valueYearStop,
                                             String valueMonthStart, String valueMonthStop, String valueDayStart,
                                             String valueDayStop, String how_many){
        BasicDBObject query = new BasicDBObject();
        String result = "";
        query.append(key,value);
        query.append("$and", List.of(
                new Document("Rok", new Document("$gte", valueYearStart).append("$lte", valueYearStop)),
                new Document("Miesiąc", new Document("$gte", valueMonthStart).append("$lte", valueMonthStop)),
                new Document("Dzień", new Document("$gte", valueDayStart).append("$lte", valueDayStop))
        ));

        if (how_many.equals("first")) {
            Document document = this.collectionHistoricalWeatherData.find(query).first();
            result = Objects.requireNonNull(document).toJson();
            return result;
        } else if (how_many.equals("all")) {
            StringBuilder jsonBuilder = new StringBuilder();
            for (Document doc : this.collectionHistoricalWeatherData.find(query)) {
                String json = doc.toJson();
                jsonBuilder.append(json);
            }
            result = jsonBuilder.toString();
            return result;
        } else if (Integer.parseInt(how_many) >= 1 && Integer.parseInt(how_many) <= this.collectionHistoricalWeatherData.countDocuments(query)) {
            StringBuilder jsonBuilder = new StringBuilder();
            for (Document doc : this.collectionHistoricalWeatherData.find(query).limit(Integer.parseInt(how_many))) {
                String json = doc.toJson();
                jsonBuilder.append(json);
            }
            result = jsonBuilder.toString();
            return result;
        }
        return "Nie znaleziono rekordów w bazie.";
    }
    /**
     * Retrieves historical weather records from the MongoDB collection based on the specified date interval.
     *
     * @param key The key used to search for the specific record.
     * @param value The value associated with the key to search for the specific record.
     * @param valueYearStart The start year of the date interval.
     * @param valueYearStop The end year of the date interval.
     * @param valueMonthStart The start month of the date interval.
     * @param valueMonthStop The end month of the date interval.
     * @param valueDayStart The start day of the date interval.
     * @param valueDayStop The end day of the date interval.
     * @param how_many Specifies the number of records to retrieve. Can be "first" (retrieve the first record), "all" (retrieve all matching records), or an integer specifying the maximum number of records to retrieve.
     * @return If records are found in the database, it returns a list of JSON strings containing the retrieved records. If no records are found, it returns an empty list.
     */
    public List<String> getHistoryRecordListByInterval(String key, String value, String valueYearStart, String valueYearStop,
                                             String valueMonthStart, String valueMonthStop, String valueDayStart,
                                             String valueDayStop, String how_many){
        BasicDBObject query = new BasicDBObject();
        List<String> result = new ArrayList<>();
        query.append(key,value);
        query.append("$and", List.of(
                new Document("Rok", new Document("$gte", valueYearStart).append("$lte", valueYearStop)),
                new Document("Miesiąc", new Document("$gte", valueMonthStart).append("$lte", valueMonthStop)),
                new Document("Dzień", new Document("$gte", valueDayStart).append("$lte", valueDayStop))
        ));

        if (how_many.equals("first")) {
            Document document = this.collectionHistoricalWeatherData.find(query).first();
            result.add(Objects.requireNonNull(document).toJson());
            return result;
        } else if (how_many.equals("all")) {
            for (Document doc : this.collectionHistoricalWeatherData.find(query)) {
                result.add(Objects.requireNonNull(doc.toJson()));
            }
            return result;
        } else if (Integer.parseInt(how_many) >= 1 && Integer.parseInt(how_many) <= this.collectionHistoricalWeatherData.countDocuments(query)) {
            for (Document doc : this.collectionHistoricalWeatherData.find(query).limit(Integer.parseInt(how_many))) {
                result.add(Objects.requireNonNull(doc.toJson()));
            }
            return result;
        }
        return result;
    }
    /**
     * Inserts a user record into the MongoDB collection.
     *
     * @param user The User object representing the user's information.
     */
    public void insertUserRecord(User user) {
        Document document = new Document("id", user.getId())
                .append("name", user.getName())
                .append("username", user.getUsername())
                .append("email", user.getEmail())
                .append("password", user.getPassword())
                .append("userRole", user.getUserRole())
                .append("locked", user.getLocked())
                .append("enabled", user.getEnabled())
                .append("tokens",user.getTokenList());
        this.collectionClients.insertOne(document);
    }
    /**
     * Appends a token to the token list of a specific user in the MongoDB collection.
     *
     * @param user The User object representing the user to whom the token will be appended.
     * @param appendToken The ConfirmationToken object representing the token to be appended.
     */
    public void appendToken(User user, ConfirmationToken appendToken) {
        Document query = new Document("username", user.getUsername());
        Document update = new Document("$push", new Document("tokens", appendToken));
        this.collectionClients.updateOne(query, update); // TODO ta linijka nie działa
    }
    /**
     * Retrieves a client record from the MongoDB collection based on the provided login value.
     *
     * @param value The login value to search for the client record.
     * @return If a record is found in the database, it returns a JSON string containing the retrieved record. If no record is found, it returns null.
     */
    public String getClientRecordByLogin(String value) {
        Document query = new Document("username", value);
        String result = "";
        Document document = this.collectionClients.find(query).first();
        if (document != null) {
            result = document.toJson();
        } else {
            result = null;
        }
        return result;
    }
    /**
     * Retrieves a client record from the MongoDB collection based on the provided email value.
     *
     * @param value The email value to search for the client record.
     * @return If a record is found in the database, it returns a JSON string containing the retrieved record. If no record is found, it returns null.
     */
    public String getClientRecordByEmail(String value) {
        Document query = new Document("email", value);
        String result = "";
        Document document = this.collectionClients.find(query).first();
        if (document != null) {
            result = document.toJson();
        } else {
            result = null;
        }
        return result;
    }
    /**
     * Retrieves a client record from the MongoDB collection based on the provided token.
     *
     * @param token The token to search for the client record.
     * @return If a record is found in the database, it returns a JSON string containing the retrieved record. If no record is found, it returns null.
     */
    public String findByToken(String token) {
        Document query = new Document("token.$.token", token);
        String result = "";
        Document document = this.collectionClients.find(query).first();
        if (document != null) {
            result = document.toJson();
        } else {
            result = null;
        }
        return result;
    }
    /**
     * Enables a user by updating the "enabled" field in the client record.
     *
     * @param username The username of the user to enable.
     */
    public void enableUser(String username) {
        this.collectionClients.findOneAndUpdate(new Document("username",username), Updates.set("enabled",true));
    }
    /**
     * Updates the token list of a user in the client record.
     *
     * @param user The User object representing the user whose token list will be updated.
     */
    public void updateUserTokens(User user) {
        this.collectionClients.findOneAndUpdate(new Document("username",user.getUsername()), Updates.set("token",user.getTokenList()));
    }
    /**
     * Updates the "confirmedAt" field of a token in the client record.
     *
     * @param user The User object representing the user.
     * @param token The token to update.
     * @param confirmedAt The new value for the "confirmedAt" field.
     */
    public void updateConfirmedAt(User user, String token, LocalDateTime confirmedAt) {
        Document query = new Document("username", user.getUsername())
                .append("token.$.token", user.findByTokenToken(token));
        Document update = new Document("$set", new Document("token.$.confirmedAt", confirmedAt));

        this.collectionClients.updateOne(query, update);
    }
    /**
     * Closes the MongoDB client connection.
     */
    public void closeClient () {
        try{
            System.out.println("Zamknięcie połączenia z bazą danych.");
        }finally {
            mongoClient.close();
        }
    }
}
