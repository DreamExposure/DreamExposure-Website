package org.dreamexposure.website.utils;

import org.dreamexposure.novautils.crypto.KeyGenerator;
import org.dreamexposure.website.database.DatabaseManager;
import org.dreamexposure.website.objects.user.User;

public class Generator {
    public static String generateEmailConfirmationLink(User user) {
        String code = KeyGenerator.csRandomAlphaNumericString(32);

        //Save to database
        DatabaseManager.getManager().addPendingConfirmation(user, code);

        return "https://www.dreamexposure.org/confirm/email?code=" + code;
    }
}
