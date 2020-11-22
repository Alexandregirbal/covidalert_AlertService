package polytech.covidalert.controllers;

import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.nio.charset.StandardCharsets;

@RestController
@CrossOrigin
@RequestMapping("/covid")
public class CovidDeclarationController {

    @PostMapping(value = "/declaration", produces = "application/json")
    public Object declareCovid(@RequestBody final String userEmail) throws IOException {
        System.out.println(userEmail + " declares themself covid+.");
        HttpClient client = HttpClient.newHttpClient();
        Object response = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8091/covid/declaration"))
                    .POST(HttpRequest.BodyPublishers.ofString(userEmail))
                    .build();

            HttpResponse<String> res = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println(res.body());
            response = res.body();
        } catch (Exception ex) {
            System.out.println("Exception while sending covid declaration: " + ex);
        }
        return response;
    }

}
