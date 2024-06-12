package com.corporation8793.dementia.chat.openai_dto.request;

import com.google.gson.annotations.SerializedName;

public class ChatGptContentImage {

    @SerializedName("type") public String type;
    @SerializedName("text") public String text = null;
    @SerializedName("image_url") public ChatImage image_url = null;

    public ChatGptContentImage(String type, String text, ChatImage image_url) {
        this.type = type;
        this.text = text;
        this.image_url = image_url;
    }






 
}
