package com.corporation8793.dementia.chat.openai_dto.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatGptContent {
    @SerializedName("content") public String type;
    public List<ChatGptContentImage> image;

    public ChatGptContent(List<ChatGptContentImage> image) {
        this.image = image;
    }

    public ChatGptContent(String type) {
        this.type = type;
    }












 
}
