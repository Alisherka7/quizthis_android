package com.example.quizthis.Model.Authentication;

public class LoginResponse {
    public String getToken() {
        return token;
    }

    public LoginResponse(String token, String error) {
        this.token = token;
        this.error = error;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String token;
    private String error;
}
