package polytech.covidalert.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONObject;
import polytech.covidalert.exception.ResourceAlreadyExistsException;
import polytech.covidalert.exception.ResourceNotFoundException;
import polytech.covidalert.models.SendWarning;
import polytech.covidalert.models.SendWarningRepository;
import polytech.covidalert.models.User;
import polytech.covidalert.models.UserRepository;
import polytech.covidalert.services.EmailSender;

@RestController
@CrossOrigin
@RequestMapping("/covid")
public class CovidDeclarationController {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private SendWarningRepository sendWarningRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/declaration", produces = "application/json")
    public Object declareCovid(@RequestBody final String userEmail) throws IOException {
        System.out.println(userEmail + " declares themself covid+.");
        HttpClient client = HttpClient.newHttpClient();
        ArrayList<String> response = new ArrayList<>();
        //"http://localhost:8091/covid/declaration"
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8091/covid/declaration"))
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(userEmail))
                    .build();

            HttpResponse<String> res = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
///
            Object obj = JSONValue.parse(res.body());
            JSONArray jsonArray=(JSONArray)obj;
            System.out.println(jsonArray);
            SendWarning sendWarning = new SendWarning();
            User userInfected = userRepository.findByEmail(userEmail);
            if ( userInfected == null){
                throw new ResourceNotFoundException(HttpStatus.INTERNAL_SERVER_ERROR, "User with email " +userEmail+ " not found.");
            }
            long user_id = userInfected.getUser_id();
            sendWarning.setUserIdInfected(user_id);
            for(Object e : jsonArray){
                User userContacted = userRepository.findByEmail(e.toString());
                if ( userContacted == null){
                    throw new ResourceNotFoundException(HttpStatus.INTERNAL_SERVER_ERROR, "User with email " +e.toString()+ " not found.");
                }
                System.out.println(userEmail + "-->" + e);
                emailSender.sendEmail((String)e);
                sendWarning.setUserIdContacted(userContacted.getUser_id());
                sendWarning.setWarning_date(new Date());
                sendWarningRepository.saveAndFlush(sendWarning);
            }
            return res.body();
        } catch (Exception ex) {
            System.out.println("Exception while sending covid declaration: " + ex);
        }
        return response;
    }

}
