package com.epita.models;

import javax.sql.rowset.Joinable;
import java.util.Arrays;
import java.util.StringJoiner;

public class Question {
    public static final int DEFAULT_DIFFICULTY = 2;

    private Integer id;
    //Defaults to 2
    private Integer difficulty = DEFAULT_DIFFICULTY;
    private String[] topics;
    private int quizId;

    public String[] getTopics() {
        return topics;
    }

    public String getJoinedTopics() {
        StringJoiner joiner = new StringJoiner(",");
        for (String tag : topics) {
            joiner.add(tag);
        }
        return joiner.toString();
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

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

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String mapToStr() {
        return String.join(",", this.topics);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", difficulty=" + difficulty +
                ", topics=" + Arrays.toString(topics) +
                ", quizId=" + quizId +
                ", question='" + question + '\'' +
                '}';
    }
}
