package com.corporation8793.dementia.chat.openai_dto.response;

import com.corporation8793.dementia.chat.openai_dto.request.ChatGptMsg;
import com.google.gson.annotations.SerializedName;

public class ChatGptResponseChoice {

    @SerializedName("message") public ChatGptMsg msg;
    @SerializedName("index") public int index;
    @SerializedName("logprobs") public Object longprobs;
    @SerializedName("finish_reason") public String finish_reason;
    public ChatGptResponseChoice( int index,ChatGptMsg msg, Object longprobs, String finish_reason){
        this.msg = msg;
        this.index = index;
        this.longprobs = longprobs;
        this.finish_reason = finish_reason;
    }

    public ChatGptMsg getMsg() {
        return msg;
    }

    public void setMsg(ChatGptMsg msg) {
        this.msg = msg;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Object getLongprobs() {
        return longprobs;
    }

    public void setLongprobs(Object longprobs) {
        this.longprobs = longprobs;
    }

    public String getFinish_reason() {
        return finish_reason;
    }

    public void setFinish_reason(String finish_reason) {
        this.finish_reason = finish_reason;
    }


}
