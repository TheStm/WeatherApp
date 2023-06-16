package com.paint23.projektpaint;

import com.paint23.projektpaint.ForMapping.PresentWeatherData;
import com.paint23.projektpaint.ForMapping.ForecastWeatherData;
import com.paint23.projektpaint.email.token.ConfirmationTokenService;
import com.paint23.projektpaint.login.LoginRequest;
import com.paint23.projektpaint.login.LoginService;
import com.paint23.projektpaint.model.QuickStart;
import com.paint23.projektpaint.registration.RegistrationRequest;
import com.paint23.projektpaint.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.paint23.projektpaint.user.User;

import com.google.gson.Gson;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * This is the main controller class for handling city-related requests and actions.
 */
@Controller
public class CityController {
    private QuickStart quickStart = new QuickStart();
    @Autowired
    private ClientService clientService;

    private LoginService loginService = new LoginService(quickStart);
    private RegistrationService registrationService = new RegistrationService(quickStart);

    private ConfirmationTokenService confirmationTokenService = new ConfirmationTokenService(quickStart);

    /**
     * Handles the request for the start page.
     *
     * @return Redirects to the login page.
     */
    @GetMapping("/")
    public String startPage() {
        return "redirect:/login";
    }

    /**
     * Handles the request for the welcome page.
     *
     * @return The welcome page.
     */
    @GetMapping("/welcome")
    public String welcomePage() {
        return "welcome";
    }

    /**
     * Handles the request for the city form.
     *
     * @return The city form page.
     */
    @GetMapping("/city")
    public String cityForm() {
        return "city";
    }

    /**
     * Handles the request for logging out.
     *
     * @return Redirects to the login page.
     */
    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/login";
    }

    /**
     * Handles the request for the login page.
     *
     * @return The login page.
     */
    @GetMapping("/login")
    public String getLoginPage() {

        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "register";
    }

    /**
     * Handles the login request.
     *
     * @param username            The username parameter.
     * @param password            The password parameter.
     * @param redirectAttributes  Redirect attributes for storing error messages.
     * @return Redirects to the appropriate page based on the login result.
     */
    @PostMapping("/login")
    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes) {
        LoginRequest loginRequest = new LoginRequest(username, password);


        try {
            // Sprawdzanie poprawności danych logowania
            boolean loggedIn = loginService.login(loginRequest); // ustaawione na true = poprawne zalogowanie

            if (loggedIn) {
                // Logowanie udane, przekierowanie na inną stronę
                return "redirect:/welcome";
            } else {

                return "redirect:/email_verification";
            }
        } catch (Exception e) {
            // Wystąpił błąd podczas logowania, przekazanie komunikatu błędu
            redirectAttributes.addFlashAttribute("error", e.getMessage());
             return "redirect:/login";
        }
    }

    /**
     * Handles the registration request.
     *
     * @param username            The username parameter.
     * @param password            The password parameter.
     * @param name                The name parameter.
     * @param email               The email parameter.
     * @param redirectAttributes  Redirect attributes for storing error messages.
     * @return Redirects to the appropriate page based on the registration result.
     */
    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("name") String name,
                        @RequestParam("email") String email,
                        RedirectAttributes redirectAttributes) {
        RegistrationRequest registrationRequest = new RegistrationRequest(name, username, email, password);
        registrationService.getUserService().setQuickStart(quickStart);
        try {

            Boolean registered = registrationService.register(registrationRequest);

        } catch (Exception e) {
            // Wystąpił błąd podczas rejestrowania, przekazanie komunikatu błędu
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
        return "redirect:/email_verification";
    }

    /**
     * Handles the request for email verification and returns the email verification page.
     */
    @GetMapping("/email_verification")
    public String emailVerification() {
        return "emailverification";
    }

    /**
     * Handles the email confirmation request with the provided token and redirects to the login page.
     */
    @RequestMapping("/confirm")
    public String emailConfirm(@RequestParam("token") String token) {
        confirmationTokenService.confirmToken(token);

        return "redirect: /login";
    }

    /**
     * Handles the request for the user's account page and adds user information to the model.
     */
    @GetMapping("/my_account")
    public String myAccount(Model model) {
        User user = loginService.getCurrentUser();
        if (Objects.isNull(user)) {
            return "welcome";
        }
        model.addAttribute("name", user.getName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "account";
    }


    /**
     * Handles the request for city weather data based on the provided city name.
     * Retrieves weather data from external APIs and adds it to the model.
     */
    @GetMapping("/result")
    public String cityNameResult(@RequestParam(value = "city", defaultValue = "Warszawa") String cityName, Model model) throws IOException{
        String answerStr = clientService.cityRestCall(cityName).block();
        Gson gson = new Gson();
        com.google.gson.JsonArray jsonArray = gson.fromJson(answerStr, com.google.gson.JsonArray.class);
        if (jsonArray.size() != 0) {
            com.google.gson.JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
            String name = jsonObject.get("name").getAsString();
            String longitude = jsonObject.get("lon").getAsString();
            String latitude = jsonObject.get("lat").getAsString();

            String presentAnswer = clientService.coordRestCall(longitude, latitude).block();
            String forecastAnswer = clientService.coordForescastRestCall(longitude, latitude).block();
            PresentWeatherData presentWeatherData = clientService.presentDataMapping(presentAnswer);
            ForecastWeatherData forecastWeatherData = clientService.forecastDataMapping(forecastAnswer);
            //System.out.println(presentWeatherData.getName() + " " + presentWeatherData.getMainData().getTemp() + " " + presentWeatherData.getWeather()[0].getDescription());
           String iconCode = presentWeatherData.getWeather()[0].getIcon();
            String iconLink = "https://openweathermap.org/img/wn/"+iconCode+"@4x.png";
            System.out.println(iconLink);
            model.addAttribute("iconLink", iconLink);
            model.addAttribute("answer", presentWeatherData);
            model.addAttribute("weatherDataList", forecastWeatherData.getWeatherDataList());
            model.addAttribute("lat", latitude);
            model.addAttribute("lng", longitude);
            return "result";
        }
        else {
            return ("city");
        }

    }
    /**
     * Handles the request for past weather data and adds station information to the model.
     */
    @GetMapping("/pastdata")
    public String pastData(Model model) {
        Station station = new Station();
        List<String> list = getstationList();
        model.addAttribute("stationList", list);
        model.addAttribute("station", station);
        return "pastdata";
    }

    /**
     * Handles the request for retrieving past weather data for a specific station within a date range.
     * Adds the retrieved data to the model.
     */
    @PostMapping("/getStation")
    public String getStation(@RequestParam("name") String name, @RequestParam("beginDate") String beginDate,
                             @RequestParam("endDate") String endDate, Model model) {
        String beginDay = beginDate.substring(0,2);
        String beginMonth = beginDate.substring(3,5);
        String beginYear = beginDate.substring(6,10);

        String endDay = endDate.substring(0,2);
        String endMonth = endDate.substring(3,5);
        String endYear = endDate.substring(6,10);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dateString1 = beginDate;
        String dateString2 = endDate;

        try {
            Date date1 = format.parse(dateString1);
            Date date2 = format.parse(dateString2);

            long diff = date2.getTime() - date1.getTime();
            long daysBetween = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            String daysBetweenS = Long.toString(daysBetween);
            List<String> dataList = quickStart.getHistoryRecordListByInterval("Nazwa stacji", name, beginYear,
                    endYear, beginMonth, endMonth, beginDay,
                    endDay, daysBetweenS);
            model.addAttribute("dataList", dataList);

            // return "pastdataresult"; <-- nie udało się zrobić
            return "pastdata";

        } catch (Exception e) {
            e.printStackTrace();
            return "pastdata";
        }
    }

    /**
     * Retrieves the list of stations.
     *
     * @return The list of stations.
     */
    public List<String> getstationList(){
        List<String> data = new ArrayList<>();
        data.add("BIAŁOWIEŻA");
        data.add("BIEBRZA-PIEŃCZYKÓWEK");
        data.add("BORUCINO");
        data.add("BORUSOWA");
        data.add("BRENNA");
        data.add("BUKOWINA TATRZAŃSKA");
        data.add("CEBER");
        data.add("CHORZELÓW");
        data.add("CHRZĄSTKOWO");
        data.add("CIESZANÓW");
        data.add("DOLINA PIĘCIU STAWÓW");
        data.add("DRONIOWICE");
        data.add("DYNÓW");
        data.add("GDAŃSK-RĘBIECHOWO");
        data.add("GOLENIÓW");
        data.add("GORZYŃ");
        data.add("JABŁONKA");
        data.add("JARCZEW");
        data.add("JAROCIN");
        data.add("JASTRZĘBIA");
        data.add("KOŁUDA WIELKA");
        data.add("KOMAŃCZA");
        data.add("KÓRNIK");
        data.add("KRAKÓW-OBSERWATORIUM");
        data.add("KROŚCIENKO");
        data.add("KRYNICA");
        data.add("ŁĄCKO");
        data.add("LALIKI");
        data.add("ŁAZY");
        data.add("LEGIONOWO");
        data.add("LGOTA GÓRNA");
        data.add("LIDZBARK WARMIŃSKI");
        data.add("LIMANOWA");
        data.add("MARIANOWO II");
        data.add("MIZERNA");
        data.add("MSZANA DOLNA");
        data.add("MUSZYNA");
        data.add("NIEDZICA");
        data.add("OLECKO");
        data.add("PIWNICZNA");
        data.add("POLANA CHOCHOŁOWSKA");
        data.add("PORONIN");
        data.add("PSZCZYNA");
        data.add("PTASZKOWA");
        data.add("PUCZNIEW");
        data.add("PUŁAWY");
        data.add("PUŁTUSK  ");
        data.add("RADZIECHOWY");
        data.add("RADZYŃ");
        data.add("RÓŻANYSTOK");
        data.add("SKIERNIEWICE");
        data.add("SMOLICE");
        data.add("SOLINA-JAWOR");
        data.add("STASZÓW");
        data.add("STRZYŻÓW");
        data.add("ŚWIĘTY KRZYŻ");
        data.add("SZEPIETOWO");
        data.add("TARNÓW");
        data.add("WARSZAWA-BIELANY");
        data.add("WARSZAWA-FILTRY");
        data.add("WARSZAWA OBERWATORIUM II");
        data.add("WIELICHOWO");
        data.add("ZAWAD");
        return data;
    }

    /**
     * Model class for a station.
     */
    public class Station{
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;
    }
}

