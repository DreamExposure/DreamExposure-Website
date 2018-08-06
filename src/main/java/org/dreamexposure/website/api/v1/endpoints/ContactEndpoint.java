package org.dreamexposure.website.api.v1.endpoints;

import de.triology.recaptchav2java.ReCaptcha;
import org.dreamexposure.website.objects.SiteSettings;
import org.dreamexposure.website.utils.EmailHandler;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class ContactEndpoint {
    public static String handle(Request request, Response response) {
        JSONObject body = new JSONObject(request.body());
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
                        response.status(400);
                        response.body("Bad Request");
                        break;
                }
            } else {
                response.status(400);
                response.body("Failed to verify ReCaptcha!");
            }
        } else {
            response.status(400);
            response.body("Bad Request");
        }
        return response.body();
    }

    private static String general(Response response, JSONObject body) {
        String subject = body.getString("subject");
        String message = body.getString("message");
        String name = body.getString("name");
        String email = body.getString("email");


        EmailHandler.getHandler().sendContactEmail(subject, message, name, email);

        response.status(200);
        response.body("Success!");

        return response.body();
    }

    private static String apply(Response response, JSONObject body) {
        String subject = body.getString("subject");
        String message = body.getString("message");
        String name = body.getString("name");
        String email = body.getString("email");

        String position = body.getString("position");
        String resume = body.getString("resume");


        EmailHandler.getHandler().sendJobApplicationEmail(subject, message, name, email, position, resume);

        response.status(200);
        response.body("Success!");

        return response.body();
    }

    private static String quote(Response response, JSONObject body) {
        String subject = body.getString("subject");
        String message = body.getString("message");
        String name = body.getString("name");
        String email = body.getString("email");

        String budget = body.getString("budget");
        String timeFrame = body.getString("time-frame");

        EmailHandler.getHandler().sendQuoteEmail(subject, message, name, email, timeFrame, budget);

        response.status(200);
        response.body("Success!");

        return response.body();
    }
}