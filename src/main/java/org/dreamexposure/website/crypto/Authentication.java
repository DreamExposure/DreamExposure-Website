package org.dreamexposure.website.crypto;

import org.dreamexposure.website.account.AccountHandler;
import org.dreamexposure.website.database.DatabaseManager;
import org.dreamexposure.website.objects.api.APIAccount;
import org.dreamexposure.website.objects.crypto.AuthenticationState;
import org.dreamexposure.website.utils.Logger;

import javax.servlet.http.HttpServletRequest;

public class Authentication {
    public static AuthenticationState authenticate(HttpServletRequest request, String path) {
        if (!request.getMethod().equalsIgnoreCase("POST")) {
            Logger.getLogger().api("Denied '" + request.getMethod() + "' access", request.getRemoteAddr());
            return new AuthenticationState(false).setStatus(405).setReason("Method not allowed");
        }
        //Check authorization
        if (AccountHandler.getHandler().hasAccount(request)) {
            //User is logged in from website, no API key needed
            Logger.getLogger().api("API Call from website", request.getRemoteAddr());

            return new AuthenticationState(true).setStatus(200).setReason("Success");
        } else {
            //Requires "Authorization Header
            if (request.getHeader("Authorization") != null) {
                String key = request.getHeader("Authorization");

                //This if safe to do since we are using NoCaptcha ReCaptcha on these pages which verifies domain source.
                if (key.equals("REGISTER_ACCOUNT") && path.equals("/api/v1/account/register")) {
                    Logger.getLogger().api("User registering account.", request.getRemoteAddr());
                    return new AuthenticationState(true).setStatus(200).setReason("Success");
                } else if (key.equals("LOGIN_ACCOUNT") && path.equals("/api/v1/account/login")) {
                    Logger.getLogger().api("user logging into account.", request.getRemoteAddr());
                    return new AuthenticationState(true).setStatus(200).setReason("Success");
                } else if (key.equals("CONTACT") && path.equals("/api/v1/contact")) {
                    Logger.getLogger().api("User sending contact email", request.getRemoteAddr());
                    return new AuthenticationState(true).setStatus(200).setReason("Success");
                } else {
                    APIAccount acc = DatabaseManager.getManager().getAPIAccount(key);
                    if (acc != null) {
                        if (acc.isBlocked()) {
                            Logger.getLogger().api("Attempted to use blocked API Key: " + acc.getAPIKey(), request.getRemoteAddr());

                            return new AuthenticationState(false).setStatus(401).setReason("Unauthorized");
                        } else {
                            //Everything checks out!
                            acc.setUses(acc.getUses() + 1);
                            DatabaseManager.getManager().updateAPIAccount(acc);

                            return new AuthenticationState(true).setStatus(200).setReason("Success");
                        }
                    } else {
                        Logger.getLogger().api("Attempted to use invalid API Key: " + key, request.getRemoteAddr());
                        return new AuthenticationState(false).setStatus(401).setReason("Unauthorized");
                    }
                }
            } else {
                Logger.getLogger().api("Attempted to use API without authorization header", request.getRemoteAddr());
                return new AuthenticationState(false).setStatus(400).setReason("Bad Request");
            }
        }
    }
}