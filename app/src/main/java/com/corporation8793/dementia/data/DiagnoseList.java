package com.corporation8793.dementia.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DiagnoseList {
    @PrimaryKey(autoGenerate = true)
    public int did;

    public String date;
    public String resultText;
    public int resultScore;

//    public DiagnoseList() {}

    public DiagnoseList(String date, String resultText, int resultScore) {
        this.date = date;
        this.resultText = resultText;
        this.resultScore = resultScore;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public int getResultScore() {
        return resultScore;
    }

    public void setResultScore(int resultScore) {
        this.resultScore = resultScore;
    }
}
