package com.corporation8793.dementia.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.chat.openai_dto.request.ChatGptContentImage;
import com.corporation8793.dementia.chat.openai_dto.request.ChatGptMsg;
import com.corporation8793.dementia.chat.openai_dto.request.ChatGptRequest;
import com.corporation8793.dementia.chat.openai_dto.request.ChatImage;
import com.corporation8793.dementia.chat.openai_dto.response.ChatGptResponse;
import com.google.android.material.appbar.AppBarLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {
    private String GPT_KEY ;
    RecyclerView chatting;
    ChatAdapter adapter;

    EditText text_input_box;
    Button send_btn, close_btn;

    ArrayList<ChatModel> chat_list = new ArrayList<>();

    GptService service;
    HashMap<String, String> headers = new HashMap<>();

    ConstraintLayout chatting_section;
    Button mic_btn; // 음성인식 버튼

    AppBarLayout top_section;
    boolean isOpen = false;


    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");

    Map<Integer, String> day_week = new HashMap<Integer, String>() {{
        put(1, "일요일");
        put(2, "월요일");
        put(3, "화요일");
        put(4, "수요일");
        put(5, "목요일");
        put(6, "금요일");
        put(7, "토요일");
    }};


    public static final int DATE = 0;
    public static final int DAY_OF_WEEK = 1;

    public static final int TIME = 2;


    /*TODO : 1. 마지막으로 채팅한 메세지 날짜와 현재 채팅 메세지의 날짜가 다르면 날짜 데이터 추가해줘야함
             2. 메세지 Room DB 저장
             3. chat-gpt api에게 응답 다 받을 때까지 입력 못 하게 막기
             4. google 내장 stt api 연동
     */

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
        close_btn = findViewById(R.id.close_btn);
        mic_btn = findViewById(R.id.mic_btn);

        top_section = findViewById(R.id.top_section);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = (get_height() / 1280) * 130;
        top_section.setLayoutParams(params);

//        Toolbar toolbar = findViewById (R.id.toolbar);
//        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정

        chatting_section = findViewById(R.id.chatting_section);
        chatting = (RecyclerView)findViewById(R.id.chatting_view);
        chatting.setHasFixedSize(true); // 변경하지 않음 -> 항목의 높이가 바뀌지 않아야 비용이 적게 드므로 성능이 좋음

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); // 리사이클러뷰의 레이아웃을 정해줄 레이아웃 매니저
        chatting.setLayoutManager(layoutManager); // 리사이클러뷰에 리니어 레이아웃 매니저를 사용함

        chat_list.add(new ChatModel(2,getTime(DATE) + " " + getTime(DAY_OF_WEEK), "none"));
        chat_list.add(new ChatModel(0,"안녕", getTime(TIME)));
        chat_list.add(new ChatModel(0,"반가워!", getTime(TIME)));
        chat_list.add(new ChatModel(1,"나도 반가워!", getTime(TIME)));
        chat_list.add(new ChatModel(1,"기분이 어때?", getTime(TIME)));

        adapter = new ChatAdapter(chat_list); // chatArrayList를 어댑터로 연결, 회원의 이메일도 넘김
        chatting.setAdapter(adapter); // 리사이클뷰에 어댑터를 설정
        chatting.addItemDecoration(new ChatItemDecoration(10));

        chatting_section.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            chatting_section.getWindowVisibleDisplayFrame(rect);

            int rootViewHeight = chatting_section.getRootView().getHeight();
            int heightDiff = rootViewHeight - rect.height();
            isOpen = heightDiff > rootViewHeight * 0.25;
        });

        chatting.addOnLayoutChangeListener(onLayoutChangeListener);

        chatting.getViewTreeObserver().addOnScrollChangedListener(() -> {
            if(isScrollable() && isOpen){
                setStackFromEnd();
                chatting.removeOnLayoutChangeListener(onLayoutChangeListener);
            }
        });

        close_btn.setOnClickListener(v->{
            finish();
        });



        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
            }
        });


        send_btn.setOnClickListener(v->{
            if (text_input_box.getText().toString().trim().equals("")){

            }else{
                chat_list.add(new ChatModel(1,text_input_box.getText().toString(),getTime(TIME)));
                adapter.notifyItemInserted(chat_list.size()-1);
                send_text_msg(text_input_box.getText().toString());
                text_input_box.setText("");
                chatting.smoothScrollToPosition(chat_list.size()-1);
            }
        });








        Log.e("GPT_KEY", GPT_KEY);


    }


    private String getTime(int type){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        Calendar cal = Calendar.getInstance();
        if (type == DATE){

            return mFormat.format(mDate);
        }else if (type == DAY_OF_WEEK){
            cal.setTime(mDate);
            return day_week.get(cal.get(Calendar.DAY_OF_WEEK));
        }else if (type == TIME){
            int hour = cal.get ( cal.HOUR_OF_DAY ) ;
            int min = cal.get ( cal.MINUTE );
            int AmPm = cal.get ( cal.AM_PM );
            String stringAmPm[] = {"오전","오후"};

            return stringAmPm[AmPm]+" "+timeFormat.format(mDate);
        }

        return "";
    }



    int get_height(){
        Display display = getWindowManager().getDefaultDisplay();  // in Activity
        /* getActivity().getWindowManager().getDefaultDisplay() */ // in Fragment
        Point size = new Point();
        display.getRealSize(size); // or getSize(size)
        int height = size.y;
        return  height;
    }

    View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if (bottom < oldBottom){
                chatting.scrollBy(0 , oldBottom - bottom);
            }
        }
    };


    boolean isScrollable(){
        return chatting.canScrollVertically(1) || chatting.canScrollVertically(-1);
    }

    /**
     * StackFromEnd 설정
     * */
    void setStackFromEnd() {
        ((LinearLayoutManager)chatting.getLayoutManager()).setStackFromEnd(true);
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

                    chat_list.add(new ChatModel(0,list.body().choices.get(0).msg.content.toString(), getTime(TIME)));
                    adapter.notifyItemInserted(chat_list.size()-1);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    void send_text_msg(String msg){

        ChatGptMsg msg_img = new ChatGptMsg("user",msg);  // request 메세지 만들기

        List<ChatGptMsg> msgs = new ArrayList<>();
        msgs.add(msg_img);  // request 메세지를 List에 담기


        ChatGptRequest request = create_request(msgs);
        new Thread(() -> {
            try {
                Log.e("hello", "in");
                Call<ChatGptResponse> repos = service.listAnswer(headers, request);
                Response<ChatGptResponse> list = repos.execute();
                if (list.isSuccessful()) {

                    chat_list.add(new ChatModel(0, list.body().choices.get(0).msg.content.toString(), getTime(TIME)));
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