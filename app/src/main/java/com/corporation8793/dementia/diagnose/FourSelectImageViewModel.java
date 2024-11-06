package com.corporation8793.dementia.diagnose;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FourSelectImageViewModel extends ViewModel  {
    private MutableLiveData<Integer> questionOne = new MutableLiveData<>();
    private MutableLiveData<Integer> questionTwo = new MutableLiveData<>();
    private MutableLiveData<Integer> questionThree = new MutableLiveData<>();
    private MutableLiveData<Integer> questionFour = new MutableLiveData<>();

    // 다른 선택지들도 동일하게 LiveData로 처리
    public LiveData<Integer> getQuestionOne() {
        return questionOne;
    }

    public void setQuestionOne(int image) {
        questionOne.setValue(image);
    }

    public LiveData<Integer> getQuestionTwo() {
        return questionTwo;
    }

    public void setQuestionTwo(int image) {
        questionTwo.setValue(image);
    }

    public LiveData<Integer> getQuestionThree() {
        return questionThree;
    }

    public void setQuestionThree(int image) {
        questionThree.setValue(image);
    }

    public LiveData<Integer> getQuestionFour() {
        return questionFour;
    }

    public void setQuestionFour(int image) {
        questionFour.setValue(image);
    }
}
