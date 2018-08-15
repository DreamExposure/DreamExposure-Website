package org.dreamexposure.website.account;

import org.dreamexposure.website.objects.SiteSettings;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings({"unchecked", "RedundantCast"})
public class AccountHandler {
    private static AccountHandler instance;
    private static Timer timer;

    private HashMap<String, Map> accounts = new HashMap<>();

    //Instance handling
    private AccountHandler() {
    } //Prevent initialization

    public static AccountHandler getHandler() {
        if (instance == null) {
            instance = new AccountHandler();
        }
        return instance;
    }

    public void init() {
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                removeTimedOutAccounts();
            }
        }, 60 * 30 * 1000);
    }

    public void shutdown() {
        if (timer != null) {
            timer.cancel();
        }
    }

    //Boolean/checkers
    public boolean hasAccount(HttpServletRequest request) {
        return accounts.containsKey((String) request.getSession(true).getAttribute("account"));
    }

    //Getters
    public Map getAccount(HttpServletRequest request) {
        if (accounts.containsKey((String) request.getSession(true).getAttribute("account"))) {
            Map m = accounts.get((String) request.getSession(true).getAttribute("account"));
            m.remove("lastUse");
            m.put("lastUse", System.currentTimeMillis());
            return m;
        } else {
            //Not logged in...
            Map m = new HashMap();
            m.put("loggedIn", false);
            m.put("year", LocalDate.now().getYear());
            return m;
        }
    }

    public Map findAccount(String userId) {
        for (Map m : accounts.values()) {
            if (m.containsKey("id")) {
                if (m.get("id").equals(userId)) {
                    m.remove("lastUse");
                    m.put("lastUse", System.currentTimeMillis());
                    return m;
                }
            }
        }
        return null;
    }

    public int accountCount() {
        return accounts.size();
    }

    //Functions
    public void addAccount(Map m, HttpServletRequest request) {
        accounts.remove((String) request.getSession(true).getAttribute("account"));
        m.remove("lastUse");
        m.put("lastUse", System.currentTimeMillis());
        accounts.put((String) request.getSession(true).getAttribute("account"), m);
    }


    public void removeAccount(HttpServletRequest request) {
        accounts.remove((String) request.getSession(true).getAttribute("account"));
    }

    private void removeTimedOutAccounts() {
        long limit = Long.valueOf(SiteSettings.TIME_OUT.get());
        final HashMap<String, Map> acc = accounts;
        for (String id : acc.keySet()) {
            Map m = acc.get(id);
            long lastUse = (long) m.get("lastUse");
            if (System.currentTimeMillis() - lastUse > limit) {
                //Timed out, remove account info and require sign in.
                acc.remove(id);
            }
        }
    }
}
