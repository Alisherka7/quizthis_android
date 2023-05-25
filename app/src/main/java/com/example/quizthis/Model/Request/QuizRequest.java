package com.example.quizthis.Model.Request;

public class QuizRequest {
    private Integer quizsetid;
    private String question;
    private String answer;

    public QuizRequest(Integer quizsetid, String question, String answer) {
        this.quizsetid = quizsetid;
        this.question = question;
        this.answer = answer;
    }

    public Integer getQuizsetid() {
        return quizsetid;
    }

    public void setQuizsetid(Integer quizsetid) {
        this.quizsetid = quizsetid;
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
}
