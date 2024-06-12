package com.corporation8793.dementia.chat.openai_dto.request;

import com.google.gson.annotations.SerializedName;

public class ChatImage {
    public ChatImage(String url) {
        this.url = url;
    }

    @SerializedName("url") public String url;
}
