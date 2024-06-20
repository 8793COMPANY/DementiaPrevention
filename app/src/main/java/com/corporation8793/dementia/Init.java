package com.corporation8793.dementia;

import static android.speech.tts.TextToSpeech.ERROR;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class Init {
    static public TextToSpeech textToSpeech;

    static public void settingTTS(Context context){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    // 언어를 선택한다.
                    Log.e("error",status+"");
                    Log.e("error","not error");
                    textToSpeech.setLanguage(Locale.KOREAN);
                }else {
                    Log.e("error","error");
                }
            }
        });
    }

    static public void destroyTTS(){
        if (textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }

}
