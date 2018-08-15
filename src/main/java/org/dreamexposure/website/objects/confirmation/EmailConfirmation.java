package org.dreamexposure.website.objects.confirmation;


import java.util.UUID;

public class EmailConfirmation {
    private UUID userId;
    private String code;

    //Getters
    public UUID getUserId() {
        return userId;
    }

    public String getCode() {
        return code;
    }

    //Setters
    public void setUserId(UUID _id) {
        userId = _id;
    }

    public void setCode(String _code) {
        code = _code;
    }
}
