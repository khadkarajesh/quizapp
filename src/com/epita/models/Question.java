package com.epita.models;

public class Question {
    public static final int DEFAULT_DIFFICULTY = 2;

    private Integer id;
    //Defaults to 2
    private Integer difficulty = DEFAULT_DIFFICULTY;
    private String question;


    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", difficulty=" + difficulty +
                ", question='" + question + '\'' +
                '}';
    }
}
