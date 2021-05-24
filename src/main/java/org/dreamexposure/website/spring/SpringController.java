package org.dreamexposure.website.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
@Controller
public class SpringController {
    private Map<String, Object> getModel() {
        Map m = new HashMap();
        m.put("loggedIn", false);
        m.put("year", LocalDate.now().getYear());
        return m;
    }

    //Main pages
    @RequestMapping(value = {"/", "/home"})
    public String home(Map<String, Object> model) {
        model.clear();
        model.putAll(getModel());
        return "index";
    }

    @RequestMapping("/about")
    public String about(Map<String, Object> model) {
        model.clear();
        model.putAll(getModel());
        return "about";
    }

    @RequestMapping("/products")
    public String products(Map<String, Object> model) {
        model.clear();
        model.putAll(getModel());
        return "products";
    }

    @RequestMapping("/store")
    public String store(Map<String, Object> model) {
        model.clear();
        model.putAll(getModel());
        return "store";
    }

    @RequestMapping("/contact")
    public String commands(Map<String, Object> model) {
        model.clear();
        model.putAll(getModel());
        return "contact";
    }

    //Policy pages
    @RequestMapping("/policy/privacy")
    public String privacyPolicy(Map<String, Object> model) {
        model.clear();
        model.putAll(getModel());
        return "policy/privacy";
    }

    @RequestMapping("/policy/tos")
    public String termsOfService(Map<String, Object> model) {
        model.clear();
        model.putAll(getModel());
        return "policy/terms";
    }

    @RequestMapping("/policy/refund")
    public String refundPolicy(Map<String, Object> model) {
        model.clear();
        model.putAll(getModel());
        return "policy/refund";
    }

    //Plugin Pages
    @RequestMapping("/apply")
    public String apply(Map<String, Object> model) {
        model.clear();
        model.putAll(getModel());
        return "jobs/apply";
    }

    @RequestMapping("/quote")
    public String quote(Map<String, Object> model) {
        model.clear();
        model.putAll(getModel());
        return "jobs/quote";
    }

    //Job pages
    @RequestMapping("/plugins/perworldchatplus")
    public String pluginPerWorldChatPlus(Map<String, Object> model) {
        model.clear();
        model.putAll(getModel());
        return "plugins/perworldchatplus";
    }

    @RequestMapping("/plugins/insanewarps")
    public String pluginInsaneWarps(Map<String, Object> model) {
        model.clear();
        model.putAll(getModel());
        return "plugins/insanewarps";
    }

    @RequestMapping("/plugins/novalib")
    public String pluginNovaLib(Map<String, Object> model) {
        model.clear();
        model.putAll(getModel());
        return "plugins/novalib";
    }
}
