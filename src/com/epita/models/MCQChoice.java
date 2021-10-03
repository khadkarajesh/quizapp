package com.epita.models;

public class MCQChoice {
    private String choice;
    private boolean valid;
    private int id;
    private int questionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "MCQChoice{" +
                "choice='" + choice + '\'' +
                ", valid=" + valid +
                ", id=" + id +
                ", questionId=" + questionId +
                '}';
    }
}
