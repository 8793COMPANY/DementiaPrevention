package com.corporation8793.dementia.diagnose;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FourSelectTextViewModel extends ViewModel {
    private MutableLiveData<String> questionOne = new MutableLiveData<>();
    private MutableLiveData<String> questionTwo = new MutableLiveData<>();
    private MutableLiveData<String> questionThree = new MutableLiveData<>();
    private MutableLiveData<String> questionFour = new MutableLiveData<>();

    // 다른 선택지들도 동일하게 LiveData로 처리
    public LiveData<String> getQuestionOne() {
        return questionOne;
    }

    public void setQuestionOne(String text) {
        questionOne.setValue(text);
    }

    public LiveData<String> getQuestionTwo() {
        return questionTwo;
    }

    public void setQuestionTwo(String text) {
        questionTwo.setValue(text);
    }

    public LiveData<String> getQuestionThree() {
        return questionThree;
    }

    public void setQuestionThree(String text) {
        questionThree.setValue(text);
    }

    public LiveData<String> getQuestionFour() {
        return questionFour;
    }

    public void setQuestionFour(String text) {
        questionFour.setValue(text);
    }
}
