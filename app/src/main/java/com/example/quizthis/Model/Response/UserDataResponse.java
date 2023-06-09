package com.example.quizthis.Model.Response;

public class UserDataResponse {
    private Integer userid;
    private String username;
    private String email;
    private String password;

    private Integer quizsets;

    public UserDataResponse(Integer userid, String username, String email, Integer quizsets, String password) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.quizsets = quizsets;
        this.password = password;
    }

    public Integer getQuizsets() {
        return quizsets;
    }

    public void setQuizsets(Integer quizsets) {
        this.quizsets = quizsets;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
