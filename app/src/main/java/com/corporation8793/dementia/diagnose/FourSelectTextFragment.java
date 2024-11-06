package com.corporation8793.dementia.diagnose;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.data.Question;
import com.corporation8793.dementia.databinding.FragmentFourSelectTextBinding;
import com.corporation8793.dementia.util.DisplayFontSize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FourSelectTextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourSelectTextFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentFourSelectTextBinding binding;

    String question_text, question_one, question_two, question_three, question_four;
    int question_image;
    String answer_question = "";

    List<String> question_all = new ArrayList<>();
//    List<String> question_random = new ArrayList<>();
    String question_remove_space = "";

    int current_pos = 0;

    public FourSelectTextFragment() {
        // Required empty public constructor
    }

    public FourSelectTextFragment(Question question, int question_image) {
        // 문제 순서 랜덤 섞기
        question_all.add(question.getScore1().split(":")[0]);
        question_remove_space = question.getScore3().replaceAll(" ", "");
        question_all.add(question_remove_space.split("/")[0].trim());
        question_all.add(question_remove_space.split("/")[1].trim());
        question_all.add(question_remove_space.split("/")[2].trim());

        Log.e("test", "question_all : " + question_all);
        Collections.shuffle(question_all);
        Log.e("test", "question_all : " + question_all);

        this.question_text = question.getContents();
        this.question_one = question_all.get(0);
        this.question_two = question_all.get(1);
        this.question_three = question_all.get(2);
        this.question_four = question_all.get(3);
        this.question_image = question_image;

        answer_question = question.getScore1().split(":")[0];
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FourSelectTextFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FourSelectTextFragment newInstance(String param1, String param2) {
        FourSelectTextFragment fragment = new FourSelectTextFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_four_select_text, container, false);

        binding.questionImg.setBackgroundResource(question_image);
        binding.questionText.setText(question_text);
        binding.questionText.setTextSize(DisplayFontSize.font_size_x_44);

        FourSelectTextViewModel viewModel = new ViewModelProvider(this).get(FourSelectTextViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.setQuestionOne(question_one);
        viewModel.setQuestionTwo(question_two);
        viewModel.setQuestionThree(question_three);
        viewModel.setQuestionFour(question_four);

        binding.choice1.choiceText.setTextSize(DisplayFontSize.font_size_x_32);
        binding.choice2.choiceText.setTextSize(DisplayFontSize.font_size_x_32);
        binding.choice3.choiceText.setTextSize(DisplayFontSize.font_size_x_32);
        binding.choice4.choiceText.setTextSize(DisplayFontSize.font_size_x_32);

        binding.choice1.choice.setOnClickListener(v->{
            Log.e("~~","choice1 click");
            if(binding.choice1.getChoice()){
                current_pos = 0;

                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                ((QuestionnaireActivity)getActivity()).current_answer = "";
                binding.choice1.setChoice(false);
                binding.choice1.choice.setSelected(false);

                if (binding.choice1.choiceText.getText().toString().equals(answer_question)) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                }
            }else{
                if (current_pos == 2) {
                    if (binding.choice2.choiceText.getText().toString().equals(answer_question)) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 3) {
                    if (binding.choice3.choiceText.getText().toString().equals(answer_question)) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 4) {
                    if (binding.choice4.choiceText.getText().toString().equals(answer_question)) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                }
                current_pos = 1;

                choice_init();
                binding.choice1.setChoice(true);
                binding.choice1.choice.setSelected(true);
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
                ((QuestionnaireActivity)getActivity()).current_answer = binding.choice1.getChoiceText();

                if (binding.choice1.choiceText.getText().toString().equals(answer_question)) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score + 1;
                }
            }
        });

        binding.choice2.choice.setOnClickListener(v->{
            Log.e("~~","choice2 click"+ binding.choice2.getChoiceText());
            if(binding.choice2.getChoice()){
                current_pos = 0;

                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                ((QuestionnaireActivity)getActivity()).current_answer = "";

                binding.choice2.setChoice(false);
                binding.choice2.choice.setSelected(false);

                if (binding.choice2.choiceText.getText().toString().equals(answer_question)) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                }
            }else{
                if (current_pos == 1) {
                    if (binding.choice1.choiceText.getText().toString().equals(answer_question)) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 3) {
                    if (binding.choice3.choiceText.getText().toString().equals(answer_question)) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 4) {
                    if (binding.choice4.choiceText.getText().toString().equals(answer_question)) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                }
                current_pos = 2;

                choice_init();
                binding.choice2.setChoice(true);
                binding.choice2.choice.setSelected(true);
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
                ((QuestionnaireActivity)getActivity()).setCurrentAnswer(binding.choice2.getChoiceText());

                if (binding.choice2.choiceText.getText().toString().equals(answer_question)) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score + 1;
                }
            }

            Log.e("activity","choice2 click"+((QuestionnaireActivity)getActivity()).current_answer);
        });

        binding.choice3.choice.setOnClickListener(v->{
            Log.e("~~","choice3 click");
            Log.e("~~","choice3 click"+ binding.choice3.getChoiceText());
            if(binding.choice3.getChoice()){
                current_pos = 0;

                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                binding.choice3.setChoice(false);
                binding.choice3.choice.setSelected(false);
                ((QuestionnaireActivity)getActivity()).current_answer = "";

                if (binding.choice3.choiceText.getText().toString().equals(answer_question)) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                }
            }else{
                if (current_pos == 1) {
                    if (binding.choice1.choiceText.getText().toString().equals(answer_question)) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 2) {
                    if (binding.choice2.choiceText.getText().toString().equals(answer_question)) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 4) {
                    if (binding.choice4.choiceText.getText().toString().equals(answer_question)) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                }
                current_pos = 3;

                choice_init();
                binding.choice3.setChoice(true);
                binding.choice3.choice.setSelected(true);
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
                ((QuestionnaireActivity)getActivity()).setCurrentAnswer(binding.choice3.getChoiceText());

                if (binding.choice3.choiceText.getText().toString().equals(answer_question)) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score + 1;
                }
            }
        });

        binding.choice4.choice.setOnClickListener(v->{
            Log.e("~~","choice4 click");
            Log.e("~~","choice4 click"+ binding.choice4.getChoiceText());
            if(binding.choice4.getChoice()){
                current_pos = 0;

                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                binding.choice4.setChoice(false);
                binding.choice4.choice.setSelected(false);
                ((QuestionnaireActivity)getActivity()).current_answer = "";

                if (binding.choice4.choiceText.getText().toString().equals(answer_question)) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                }
            }else{
                if (current_pos == 1) {
                    if (binding.choice1.choiceText.getText().toString().equals(answer_question)) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 2) {
                    if (binding.choice2.choiceText.getText().toString().equals(answer_question)) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 3) {
                    if (binding.choice3.choiceText.getText().toString().equals(answer_question)) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                }
                current_pos = 4;

                choice_init();
                binding.choice4.setChoice(true);
                binding.choice4.choice.setSelected(true);
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
                ((QuestionnaireActivity)getActivity()).setCurrentAnswer(binding.choice4.getChoiceText());

                if (binding.choice4.choiceText.getText().toString().equals(answer_question)) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score + 1;
                }
            }
        });

        ((QuestionnaireActivity)getActivity()).speakText(question_text);

//        return inflater.inflate(R.layout.fragment_four_select_text, container, false);
        return binding.getRoot();
    }

    void choice_init() {
        binding.choice1.setChoice(false);
        binding.choice2.setChoice(false);
        binding.choice3.setChoice(false);
        binding.choice4.setChoice(false);

        binding.choice1.choice.setSelected(false);
        binding.choice2.choice.setSelected(false);
        binding.choice3.choice.setSelected(false);
        binding.choice4.choice.setSelected(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}