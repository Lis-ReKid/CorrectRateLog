package com.re_kid.lis.correctratelog.obj;

public class History {
    private final LearnedDate learnedDate;
    private final LearnedTime learnedTime;
    private final int correctNum;
    private final int entireNum;
    private final CorrectRate correctRate;

    public History(LearnedDate learnedDate, LearnedTime learnedTime,
                   int correctNum, int entireNum, CorrectRate correctRate) {
        this.learnedDate = learnedDate;
        this.learnedTime = learnedTime;
        this.correctNum = correctNum;
        this.entireNum = entireNum;
        this.correctRate = correctRate;
    }

    public LearnedDate getLearnedDate() {
        return learnedDate;
    }

    public LearnedTime getLearnedTime() {
        return learnedTime;
    }

    public int getCorrectNum() {
        return correctNum;
    }

    public int getEntireNum() {
        return entireNum;
    }

    public CorrectRate getCorrectRate() {
        return correctRate;
    }
}
