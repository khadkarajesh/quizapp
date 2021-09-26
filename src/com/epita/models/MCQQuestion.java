package com.epita.models;

import java.util.ArrayList;

public class MCQQuestion extends Question {
    private ArrayList<MCQChoice> choices;

    public ArrayList<MCQChoice> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<MCQChoice> choices) {
        this.choices = choices;
    }
}
