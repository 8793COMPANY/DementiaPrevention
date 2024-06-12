package com.corporation8793.dementia.chat.openai_dto.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatGptMsg {

    @SerializedName("role") public String role;
    @SerializedName("content") public Object content = null;




//    @SerializedName("content") public ChatGptContentImage content_img = null;

    public ChatGptMsg(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public ChatGptMsg(String role, List<ChatGptContentImage> content_img) {
        this.role = role;
        this.content = content_img;
    }


    //chat






}
