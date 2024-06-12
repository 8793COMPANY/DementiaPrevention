package com.corporation8793.dementia.chat;



import static android.provider.Settings.Secure.getString;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.chat.openai_dto.request.ChatGptRequest;
import com.corporation8793.dementia.chat.openai_dto.response.ChatGptResponse;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GptService {



    @Headers({"Content-Type: application/json"
            })
    @POST("v1/chat/completions")
    Call<ChatGptResponse> listAnswer(@HeaderMap Map<String, String> headers, @Body ChatGptRequest post);
}
