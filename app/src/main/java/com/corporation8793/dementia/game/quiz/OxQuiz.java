package com.corporation8793.dementia.game.quiz;

public class OxQuiz {
    int index;
    int drawable; // 문제 그림 (drawable 폴더에서 가져옴)
    String content; // 문제 내용
    String correct_answer; // 정답

    public OxQuiz(int index, int drawable, String content, String correct_answer) {
        this.index = index;
        this.drawable = drawable;
        this.content = content;
        this.correct_answer = correct_answer;
    }

    // Getter 메서드

    public int getIndex() { return index; }

    public int getDrawable() { return drawable; }

    public String getQuestion() { return content; }

    public String getAnswer() { return correct_answer; }
}
