package com.example.quizthis.Model.Response;

public class NewQuizsetResponse {
    private String quizsetid;
    private String title;
    private String description;
    private String error;

    public String getQuizsetid() {
        return quizsetid;
    }

    public void setQuizsetid(String quizsetid) {
        this.quizsetid = quizsetid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public NewQuizsetResponse(String quizsetid, String title, String description, String error) {
        this.quizsetid = quizsetid;
        this.title = title;
        this.description = description;
        this.error = error;
    }
}
