package org.dreamexposure.website.api.v1.endpoints;

import de.triology.recaptchav2java.ReCaptcha;
import org.dreamexposure.website.crypto.Authentication;
import org.dreamexposure.website.objects.SiteSettings;
import org.dreamexposure.website.objects.crypto.AuthenticationState;
import org.dreamexposure.website.utils.EmailHandler;
import org.dreamexposure.website.utils.ResponseUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactEndpoint {
    @PostMapping(value = "", produces = "application/json")
    public static String handle(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        //Authenticate...
        AuthenticationState authState = Authentication.authenticate(request, "/api/v1/contact");
        if (!authState.isSuccess()) {
            response.setStatus(authState.getStatus());
            response.setContentType("application/json");
            return authState.toJson();
        }

        //Okay, now handle actual request.
        JSONObject body = new JSONObject(requestBody);
        if (body.has("subject") && body.has("message") && body.has("name") && body.has("email") && body.has("gcap") && body.has("type")) {
            if (new ReCaptcha(SiteSettings.RECAP_KEY.get()).isValid(body.getString("gcap"))) {
                switch (body.getString("type").toLowerCase()) {
                    case "general":
                        return general(response, body);
                    case "job-application":
                        return apply(response, body);
                    case "quote":
                        return quote(response, body);
                    default:
                        response.setStatus(400);
                        return "Invalid Request!";
                }
            } else {
                response.setStatus(400);
                return "Failed to verify Google ReCAPTCHA!";
            }
        } else {
            response.setStatus(400);
            return "Invalid Request!";
        }
    }

    private static String general(HttpServletResponse response, JSONObject body) {
        String subject = body.getString("subject");
        String message = body.getString("message");
        String name = body.getString("name");
        String email = body.getString("email");


        EmailHandler.getHandler().sendContactEmail(subject, message, name, email);

        response.setContentType("application/json");
        response.setStatus(200);
        return ResponseUtils.getJsonResponseMessage("Success!");
    }

    private static String apply(HttpServletResponse response, JSONObject body) {
        String subject = body.getString("subject");
        String message = body.getString("message");
        String name = body.getString("name");
        String email = body.getString("email");

        String position = body.getString("position");
        String resume = body.getString("resume");


        EmailHandler.getHandler().sendJobApplicationEmail(subject, message, name, email, position, resume);

        response.setContentType("application/json");
        response.setStatus(200);
        return ResponseUtils.getJsonResponseMessage("Success!");
    }

    private static String quote(HttpServletResponse response, JSONObject body) {
        String subject = body.getString("subject");
        String message = body.getString("message");
        String name = body.getString("name");
        String email = body.getString("email");

        String budget = body.getString("budget");
        String timeFrame = body.getString("time-frame");

        EmailHandler.getHandler().sendQuoteEmail(subject, message, name, email, timeFrame, budget);

        response.setContentType("application/json");
        response.setStatus(200);
        return ResponseUtils.getJsonResponseMessage("Success!");
    }
}