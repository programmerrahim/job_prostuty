package com.ruksana.model;

public class Question {
    private String question, optionA, optionB, optionC, optionD, correctAnswer, explanation;
    private String selectedAnswer; // To store the user's selected answer
    private boolean hasBeenAnswered; // To track if the question has been answered

    public Question(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer, String explanation) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.selectedAnswer = null;
        this.hasBeenAnswered = false;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
        this.hasBeenAnswered = true;
    }

    public boolean hasBeenAnswered() {
        return hasBeenAnswered;
    }
}