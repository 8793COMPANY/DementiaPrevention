package com.corporation8793.dementia.chat.openai_dto.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatGptRequest {

    @SerializedName("model") private String model;

    @SerializedName("temperature") private double temperature;
    @SerializedName("max_tokens") private int maxToken;
    @SerializedName("messages") private List<ChatGptMsg> messages;

    public ChatGptRequest(String model, int maxToken, List<ChatGptMsg> messages) {
        this.model = model;
        this.maxToken = maxToken;
        this.messages = messages;
    }



    public int getMaxToken() {
        return maxToken;
    }

    public void setMaxToken(int maxToken) {
        this.maxToken = maxToken;
    }

    public List<ChatGptMsg> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatGptMsg> messages) {
        this.messages = messages;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }













}
