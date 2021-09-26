package com.epita.models;

public class MCQAnswer {
    MCQChoice mcqChoice;
    Quiz quiz;
    Student student;

    public MCQChoice getMcqChoice() {
        return mcqChoice;
    }

    public void setMcqChoice(MCQChoice mcqChoice) {
        this.mcqChoice = mcqChoice;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
