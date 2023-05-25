package com.example.quizthis.Model.Response;

public class QuizResponse {
    private Integer quizid;
    private String question;
    private String answer;
    private String error;

    public QuizResponse(Integer quizid, String question, String answer, String error) {
        this.quizid = quizid;
        this.question = question;
        this.answer = answer;
        this.error = error;
    }

    public Integer getQuizid() {
        return quizid;
    }

    public void setQuizid(Integer quizid) {
        this.quizid = quizid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
