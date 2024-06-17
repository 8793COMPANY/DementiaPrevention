package com.corporation8793.dementia.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.chat.openai_dto.request.ChatGptContentImage;
import com.corporation8793.dementia.chat.openai_dto.request.ChatGptMsg;
import com.corporation8793.dementia.chat.openai_dto.request.ChatGptRequest;
import com.corporation8793.dementia.chat.openai_dto.request.ChatImage;
import com.corporation8793.dementia.chat.openai_dto.response.ChatGptResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {
    private String GPT_KEY ;
    RecyclerView chatting;
    ChatAdapter adapter;

    EditText text_input_box;
    Button send_btn;

    ArrayList<ChatModel> chat_list = new ArrayList<>();

    GptService service;
    HashMap<String, String> headers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        GPT_KEY =  getString(R.string.GPT_KEY);

        headers.put("Content-Type", "application/json");
        headers.put("Authorization", getString(R.string.GPT_KEY));

        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://api.openai.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        service = retrofit.create(GptService.class);

        text_input_box = findViewById(R.id.text_input_box);
        send_btn = findViewById(R.id.send_btn);


        chatting = (RecyclerView)findViewById(R.id.chatting_view);
        chatting.setHasFixedSize(true); // 변경하지 않음 -> 항목의 높이가 바뀌지 않아야 비용이 적게 드므로 성능이 좋음

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); // 리사이클러뷰의 레이아웃을 정해줄 레이아웃 매니저
        chatting.setLayoutManager(layoutManager); // 리사이클러뷰에 리니어 레이아웃 매니저를 사용함

        chat_list.add(new ChatModel(0,"안녕"));
        chat_list.add(new ChatModel(0,"반가워!"));
        chat_list.add(new ChatModel(1,"나도 반가워!"));
        chat_list.add(new ChatModel(1,"기분이 어때?"));

        adapter = new ChatAdapter(chat_list); // chatArrayList를 어댑터로 연결, 회원의 이메일도 넘김
        chatting.setAdapter(adapter); // 리사이클뷰에 어댑터를 설정
        chatting.addItemDecoration(new ChatItemDecoration(10));




        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
            }
        });


        send_btn.setOnClickListener(v->{
            if (text_input_box.getText().toString().trim().equals("")){

            }else{
                chat_list.add(new ChatModel(1,text_input_box.getText().toString()));
                adapter.notifyItemInserted(chat_list.size()-1);
                send_text_msg(text_input_box.getText().toString());
                text_input_box.setText("");
            }
        });


//

//
//
        //이미지 입력


//        ChatGptMsg msg = new ChatGptMsg("user", "안녕 오늘 기분 어때?");  //  chat

//        msgs.add(msg);  // chat 전용 request msg



        Log.e("GPT_KEY", GPT_KEY);


    }

    void send_img_msg(String msg, String img){
                                                                                    //msg
        ChatGptContentImage msg_con_img =new ChatGptContentImage("text", "안녕 이 이미지가 뭔지 알아?", null); // 질문
                                                                                                                // img
        ChatGptContentImage msg_con_img2 =new ChatGptContentImage("image_url", null, new ChatImage("https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Gfp-wisconsin-madison-the-nature-boardwalk.jpg/2560px-Gfp-wisconsin-madison-the-nature-boardwalk.jpg")); //이미지

        List<ChatGptContentImage> msg_imgs = new ArrayList<>();
        msg_imgs.add(msg_con_img);
        msg_imgs.add(msg_con_img2);


        ChatGptMsg msg_img = new ChatGptMsg("user",msg_imgs);       // image input

        List<ChatGptMsg> msgs = new ArrayList<>();
        msgs.add(msg_img);


        ChatGptRequest request = create_request(msgs);

        new Thread(() -> {
            try {
                Log.e("hello", "in");
                Call<ChatGptResponse> repos = service.listAnswer(headers, request);
                Response<ChatGptResponse> list = repos.execute();
                Log.e("list", list.code()+"");
                if (list.isSuccessful()) {
//                    Log.e("list", list.isSuccessful() + "");
//                    Log.e("list", list.message());
//                    Log.e("list id", list.body().id + "");
//                    Log.e("list object", list.body().object + "");
//                    Log.e("list created", list.body().created + "");
//                    Log.e("list model", list.body().model + "");
//                    Log.e("list finish_reason", list.body().created + "");
//                    Log.e("list index", list.body().choices.get(0).index + "");
//                    Log.e("list index", list.body().choices.get(0).msg.role + "");
//                    Log.e("list index", list.body().choices.get(0).msg.content + "");
//                    Log.e("list longprobs", list.body().choices.get(0).longprobs + "");
//                    Log.e("list finish_reason", list.body().choices.get(0).finish_reason + "");

                    chat_list.add(new ChatModel(0,list.body().choices.get(0).msg.content.toString()));
                    adapter.notifyItemInserted(chat_list.size()-1);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    void send_text_msg(String msg){

        ChatGptMsg msg_img = new ChatGptMsg("user",msg);

        List<ChatGptMsg> msgs = new ArrayList<>();
        msgs.add(msg_img);


        ChatGptRequest request = create_request(msgs);
        new Thread(() -> {
            try {
                Log.e("hello", "in");
                Call<ChatGptResponse> repos = service.listAnswer(headers, request);
                Response<ChatGptResponse> list = repos.execute();
                if (list.isSuccessful()) {
//                    Log.e("list", list.code() + "");
//                    Log.e("list", list.isSuccessful() + "");
//                    Log.e("list", list.message());
//                    Log.e("list id", list.body().id + "");
//                    Log.e("list object", list.body().object + "");
//                    Log.e("list created", list.body().created + "");
//                    Log.e("list model", list.body().model + "");
//                    Log.e("list finish_reason", list.body().created + "");
//                    Log.e("list index", list.body().choices.get(0).index + "");
//                    Log.e("list index", list.body().choices.get(0).msg.role + "");
//                    Log.e("list index", list.body().choices.get(0).msg.content + "");
//                    Log.e("list longprobs", list.body().choices.get(0).longprobs + "");
//                    Log.e("list finish_reason", list.body().choices.get(0).finish_reason + "");

                    chat_list.add(new ChatModel(0, list.body().choices.get(0).msg.content.toString()));
                    adapter.notifyItemInserted(chat_list.size() - 1);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }


     ChatGptRequest create_request(List<ChatGptMsg> msgs){
        ChatGptRequest request = new ChatGptRequest("gpt-4o",1000,msgs);

        return request;
    }
}