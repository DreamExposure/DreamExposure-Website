package org.dreamexposure.website.api.v1.endpoints;

import de.triology.recaptchav2java.ReCaptcha;
import org.dreamexposure.website.account.AccountHandler;
import org.dreamexposure.website.crypto.Authentication;
import org.dreamexposure.website.database.DatabaseManager;
import org.dreamexposure.website.objects.SiteSettings;
import org.dreamexposure.website.objects.crypto.AuthenticationState;
import org.dreamexposure.website.objects.user.User;
import org.dreamexposure.website.utils.EmailHandler;
import org.dreamexposure.website.utils.Generator;
import org.dreamexposure.website.utils.Logger;
import org.dreamexposure.website.utils.ResponseUtils;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ThrowableNotThrown")
@RestController
@RequestMapping("/api/v1/account")
public class AccountEndpoint {

    @PostMapping(value = "/register")
    public static String register(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        //Authenticate...
        AuthenticationState authState = Authentication.authenticate(request, "/api/v1/account/register");
        if (!authState.isSuccess()) {
            response.setStatus(authState.getStatus());
            response.setContentType("application/json");
            return authState.toJson();
        }

        //Okay, now handle actual request.
        JSONObject body = new JSONObject(requestBody);
        if (body.has("username") && body.has("email") && body.has("password") && body.has("gcap")) {
            if (new ReCaptcha(SiteSettings.RECAP_KEY.get()).isValid(body.getString("gcap"))) {
                String username = body.getString("username");
                String email = body.getString("email");
                if (!DatabaseManager.getManager().usernameOrEmailTaken(username, email)) {
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    String hash = encoder.encode(body.getString("password"));

                    DatabaseManager.getManager().addNewUser(username, email, hash);
                    User account = DatabaseManager.getManager().getUserFromEmail(email);

                    //Send confirmation email!!!
                    EmailHandler.getHandler().sendEmailConfirm(email, Generator.generateEmailConfirmationLink(account));

                    Map<String, Object> m = new HashMap<>();
                    m.put("year", LocalDate.now().getYear());
                    m.put("loggedIn", true);
                    m.put("account", account);
                    AccountHandler.getHandler().addAccount(m, request);

                    response.setContentType("application/json");
                    response.setStatus(200);
                    return ResponseUtils.getJsonResponseMessage("Success!");
                } else {
                    response.setStatus(400);
                    return "Email/Username Invalid!";
                }
            } else {
                response.setStatus(400);
                return "Failed to verify ReCAPTCHA!";
            }
        } else {
            response.setStatus(400);
            return "Invalid Request!";
        }
    }

    @PostMapping(value = "/login", produces = "application/json")
    public static String login(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        //Authenticate...
        AuthenticationState authState = Authentication.authenticate(request, "/api/v1/account/login");
        if (!authState.isSuccess()) {
            response.setStatus(authState.getStatus());
            response.setContentType("application/json");
            return authState.toJson();
        }

        //Okay, now handle actual request.
        JSONObject body = new JSONObject(requestBody);
        if (body.has("email") && body.has("password") && body.has("gcap")) {
            if (new ReCaptcha(SiteSettings.RECAP_KEY.get()).isValid(body.getString("gcap"))) {
                String email = body.getString("email");
                if (DatabaseManager.getManager().validLogin(email, body.getString("password"))) {

                    User account = DatabaseManager.getManager().getUserFromEmail(email);

                    Map<String, Object> m = new HashMap<>();
                    m.put("year", LocalDate.now().getYear());
                    m.put("loggedIn", true);
                    m.put("account", account);
                    AccountHandler.getHandler().addAccount(m, request);

                    Logger.getLogger().api("User logged into account: " + account.getUsername(), request.getRemoteAddr());

                    response.setContentType("application/json");
                    response.setStatus(200);
                    return ResponseUtils.getJsonResponseMessage("Success!");
                } else {
                    response.setStatus(400);
                    return "Email/Password Invalid!";
                }
            } else {
                response.setStatus(400);
                return "Failed to verify ReCAPTCHA!";
            }
        } else {
            response.setStatus(400);
            return "Invalid Request!";
        }
    }

}