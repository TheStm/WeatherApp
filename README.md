# Weather App - Project Documentation


Supervisor: Dr.  Piotr Bobiński

1. Introduction

The goal of our project was to create a web application that allows users to check the current weather and forecast for the upcoming days for a selected city. The application uses the OpenWeather API to retrieve weather data and the Institute of Meteorology and Water Management (IMGW) API to display meteorological statistics for a selected weather station within a specified time range.

2. Technologies Used

The application was implemented as a web application, utilizing the HTTPS protocol and user authentication through email registration and login. Data retrieved from the IMGW API and user data are stored in a MongoDB database hosted on AWS cloud.

The backend of the application was developed using the Spring Boot framework, while the frontend relies on the Bootstrap framework. The Bokeh framework, written in Python, was used for data visualization from the IMGW API.

3. User Interface

The user interface consists of several views, including:

User registration and login views
Email address verification view
Welcome page with a link to the project repository
User data view
Current weather and forecast view for the selected city
Meteorological data view for Polish weather stations
4. Interaction with OpenWeather API

The application retrieves weather data from the OpenWeather service API. The data is transmitted in JSON format and contains weather information based on the provided geographical coordinates. However, for user convenience, the application allows entering a city name, which is then converted into geographical coordinates using the dedicated OpenWeather API. Subsequently, a request is sent to the OpenWeather API based on the received coordinates, and the JSON response is parsed. Relevant attributes are added to the model and displayed in the view. If the API does not find any city with the provided name, the user will be redirected back to the search page.

5. Database Connection

The application communicates with the MongoDB database using the QuickStart class object.
