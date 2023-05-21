package com.example.quizthis.Model.Response;

public class QuizsetResponse {

    private Integer quizsetid;
    private String title;
    private String description;
    private String error;

    public QuizsetResponse(Integer quizsetid, String title, String description, String error) {
        this.quizsetid = quizsetid;
        this.title = title;
        this.description = description;
        this.error = error;
    }

    public Integer getQuizsetid() {
        return quizsetid;
    }

    public void setQuizsetid(Integer quizsetid) {
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
}
