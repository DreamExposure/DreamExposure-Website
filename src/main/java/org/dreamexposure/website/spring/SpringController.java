package org.dreamexposure.website.spring;

import org.dreamexposure.website.account.AccountHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@SuppressWarnings("unchecked")
@Controller
public class SpringController {
    //Main pages
    @RequestMapping(value = {"/", "/home"})
    public String home(Map<String, Object> model, HttpServletRequest req) {
        model.putAll(AccountHandler.getHandler().getAccount(req));
        return "index";
    }

    @RequestMapping("/about")
    public String about(Map<String, Object> model, HttpServletRequest req) {
        model.clear();
        model.putAll(AccountHandler.getHandler().getAccount(req));
        return "about";
    }

    @RequestMapping("/contact")
    public String commands(Map<String, Object> model, HttpServletRequest req) {
        model.clear();
        model.putAll(AccountHandler.getHandler().getAccount(req));
        return "contact";
    }

    //Policy pages
    @RequestMapping("/policy/privacy")
    public String privacyPolicy(Map<String, Object> model, HttpServletRequest req) {
        model.clear();
        model.putAll(AccountHandler.getHandler().getAccount(req));
        return "policy/privacy";
    }

    @RequestMapping("/policy/tos")
    public String termsOfService(Map<String, Object> model, HttpServletRequest req) {
        model.clear();
        model.putAll(AccountHandler.getHandler().getAccount(req));
        return "policy/terms";
    }

    //Plugin Pages
    @RequestMapping("/apply")
    public String apply(Map<String, Object> model, HttpServletRequest req) {
        model.clear();
        model.putAll(AccountHandler.getHandler().getAccount(req));
        return "jobs/apply";
    }

    @RequestMapping("/quote")
    public String quote(Map<String, Object> model, HttpServletRequest req) {
        model.clear();
        model.putAll(AccountHandler.getHandler().getAccount(req));
        return "jobs/quote";
    }

    //Job pages
    @RequestMapping("/plugins/perworldchatplus")
    public String pluginPerWorldChatPlus(Map<String, Object> model, HttpServletRequest req) {
        model.clear();
        model.putAll(AccountHandler.getHandler().getAccount(req));
        return "plugins/perworldchatplus";
    }

    @RequestMapping("/plugins/insane-warps")
    public String pluginInsaneWarps(Map<String, Object> model, HttpServletRequest req) {
        model.clear();
        model.putAll(AccountHandler.getHandler().getAccount(req));
        return "plugins/insane-warps";
    }

    @RequestMapping("/plugins/novalib")
    public String pluginNovaLib(Map<String, Object> model, HttpServletRequest req) {
        model.clear();
        model.putAll(AccountHandler.getHandler().getAccount(req));
        return "plugins/novalib";
    }

    //Account pages
    @RequestMapping("/account")
    public String accountMain(Map<String, Object> model, HttpServletRequest req) {
        model.clear();
        model.putAll(AccountHandler.getHandler().getAccount(req));
        return "account/account";
    }

    @RequestMapping("/account/register")
    public String accountRegister(Map<String, Object> model, HttpServletRequest req) {
        if (AccountHandler.getHandler().hasAccount(req)) return "account/account";
        model.clear();
        model.putAll(AccountHandler.getHandler().getAccount(req));
        return "account/register";
    }

    @RequestMapping("/account/login")
    public String accountLogin(Map<String, Object> model, HttpServletRequest req) {
        if (AccountHandler.getHandler().hasAccount(req)) return "account/account";
        model.clear();
        model.putAll(AccountHandler.getHandler().getAccount(req));
        return "account/login";
    }

    @RequestMapping("/account/logout")
    public static String logout(HttpServletRequest request, HttpServletResponse response) {
        AccountHandler.getHandler().removeAccount(request);
        try {
            response.sendRedirect("/");
        } catch (Exception ignore) {
        }
        return "redirect:/";
    }
}