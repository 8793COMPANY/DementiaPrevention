package com.corporation8793.dementia.chat.openai_dto.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatGptResponse {
    @SerializedName("id") public String id;
    @SerializedName("object") public String object;
    @SerializedName("created") public int created;
    @SerializedName("model") public String model;
    @SerializedName("choices") public List<ChatGptResponseChoice> choices;
    @SerializedName("usage") public ChatGptResponseUsage usage;
    public ChatGptResponse(String id, String object, int created, String model, List<ChatGptResponseChoice> choices, ChatGptResponseUsage usage){
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
        this.usage = usage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChatGptResponseChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<ChatGptResponseChoice> choices) {
        this.choices = choices;
    }

    public ChatGptResponseUsage getUsage() {
        return usage;
    }

    public void setUsage(ChatGptResponseUsage usage) {
        this.usage = usage;
    }


}
