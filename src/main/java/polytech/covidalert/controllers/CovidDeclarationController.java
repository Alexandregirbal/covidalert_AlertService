package polytech.covidalert.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import polytech.covidalert.services.EmailSender;

@RestController
@CrossOrigin
@RequestMapping("/covid")
public class CovidDeclarationController {

    @Autowired
    private EmailSender emailSender;

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
            for(Object e : jsonArray){
                System.out.println(e);
                emailSender.sendEmail((String)e);
            }
            return res.body();
        } catch (Exception ex) {
            System.out.println("Exception while sending covid declaration: " + ex);
        }
        return response;
    }

}
