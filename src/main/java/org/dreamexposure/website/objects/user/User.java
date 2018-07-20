package org.dreamexposure.website.objects.user;

import java.util.UUID;

public class User {
    private final UUID id;
    private String username;
    private String email;
    private boolean emailConfirmed;
    private boolean admin;

    public User(UUID _id) {
        id = _id;
    }

    //Getters
    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public boolean isAdmin() {
        return admin;
    }

    //Setters
    public void setUsername(String _username) {
        username = _username;
    }

    public void setEmail(String _email) {
        email = _email;
    }

    public void setEmailConfirmed(Boolean _confirmed) {
        emailConfirmed = _confirmed;
    }

    public void setAdmin(boolean _admin) {
        admin = _admin;
    }
}
