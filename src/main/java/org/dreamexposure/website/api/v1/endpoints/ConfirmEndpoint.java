package org.dreamexposure.website.api.v1.endpoints;

import org.dreamexposure.website.database.DatabaseManager;
import org.dreamexposure.website.objects.user.Confirmation;
import org.dreamexposure.website.objects.user.User;
import org.dreamexposure.website.utils.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("confirm")
public class ConfirmEndpoint {
    @GetMapping("/email")
    public static String confirmEmail(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> queryParams) {
        if (queryParams.containsKey("code")) {
            String code = queryParams.get("code");
            Confirmation con = DatabaseManager.getManager().getConfirmationInfo(code);
            if (con != null) {
                User user = DatabaseManager.getManager().getUserFromId(con.getUserId());
                user.setEmailConfirmed(true);
                DatabaseManager.getManager().removeConfirmationInfo(code);
                DatabaseManager.getManager().updateUser(user);

                Logger.getLogger().api("Confirmed user email: " + user.getId(), request.getRemoteAddr());

                //Success... redirect to account page.
                try {
                    response.sendRedirect("/account");
                    return "redirect:/account";
                } catch (Exception ignore) {
                    return "redirect:/account";
                }
            } else {
                response.setStatus(400);
                return "Invalid Code!";
            }
        } else {
            response.setStatus(400);
            return "Invalid Request!";
        }
    }
}
