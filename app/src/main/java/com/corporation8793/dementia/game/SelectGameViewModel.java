package com.corporation8793.dementia.game;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectGameViewModel extends ViewModel {
    private MutableLiveData<String> scoreWhacAMole = new MutableLiveData<>();
    private MutableLiveData<String> scoreOtherNumber = new MutableLiveData<>();
    private MutableLiveData<String> scoreFindDifferentColor = new MutableLiveData<>();
    private MutableLiveData<String> scoreFindSameColorAndText = new MutableLiveData<>();
    private MutableLiveData<String> scoreQuiz = new MutableLiveData<>();
    private MutableLiveData<String> scoreFindSameThing = new MutableLiveData<>();

    public LiveData<String> getScoreWhacAMole() {
        return scoreWhacAMole;
    }

    public void setScoreWhacAMole(String text) {
        scoreWhacAMole.setValue(text);
    }

    public LiveData<String> getScoreOtherNumber() {
        return scoreOtherNumber;
    }

    public void setScoreOtherNumber(String text) {
        scoreOtherNumber.setValue(text);
    }

    public LiveData<String> getScoreFindDifferentColor() {
        return scoreFindDifferentColor;
    }

    public void setScoreFindDifferentColor(String text) {
        scoreFindDifferentColor.setValue(text);
    }

    public LiveData<String> getScoreFindSameColorAndText() {
        return scoreFindSameColorAndText;
    }

    public void setScoreFindSameColorAndText(String text) {
        scoreFindSameColorAndText.setValue(text);
    }

    public LiveData<String> getScoreQuiz() {
        return scoreQuiz;
    }

    public void setScoreQuiz(String text) {
        scoreQuiz.setValue(text);
    }

    public LiveData<String> getScoreFindSameThing() {
        return scoreFindSameThing;
    }

    public void setScoreFindSameThing(String text) {
        scoreFindSameThing.setValue(text);
    }
}
