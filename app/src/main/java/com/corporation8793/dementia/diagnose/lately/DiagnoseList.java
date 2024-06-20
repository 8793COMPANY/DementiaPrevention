package com.corporation8793.dementia.diagnose.lately;

public class DiagnoseList {
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    String date;
    String result;

    public DiagnoseList(String date, String result) {
        this.date = date;
        this.result = result;
    }
}
