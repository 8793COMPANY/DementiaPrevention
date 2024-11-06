package com.corporation8793.dementia.diagnose;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.data.Question;
import com.corporation8793.dementia.databinding.FragmentFourSelectImageBinding;
import com.corporation8793.dementia.util.DisplayFontSize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FourSelectImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourSelectImageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentFourSelectImageBinding binding;

    Context context;

    String question_text, question_num;
    int question_image, question_one, question_two, question_three, question_four;
    String imageName = "";
    String addImage = "";
    int answer_image, add_image;
    int current_pos = 0;

    List<Integer> question_all = new ArrayList<>();

    FourSelectImageViewModel viewModel;

    public FourSelectImageFragment() {
        // Required empty public constructor
    }

    public FourSelectImageFragment(Question question, int question_image, Context context) {
        this.context = context;
        this.question_text = question.getContents();
        this.question_num = question.getNum();
        this.question_image = question_image;

        Log.e("test", question_num);

        // 여기서 타입마다 그림 넣어서 답 정해줘야함
        switch (this.question_num) {
            case "364": case "367":
                imageName = "picture_inference_" + question_num + "_4";
                Log.e("test", imageName);

                answer_image = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
                Log.e("test", answer_image+"");

                break;
            case "365":
                imageName = "picture_inference_" + question_num + "_1";
                Log.e("test", imageName);

                answer_image = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
                Log.e("test", answer_image+"");

                break;
            case "366": case "368":
                imageName = "picture_inference_" + question_num + "_3";
                Log.e("test", imageName);

                answer_image = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
                Log.e("test", answer_image+"");

                break;
        }

        for (int i = 1; i <= 4; i++) {
            addImage = "picture_inference_" + question_num + "_" + i;
            add_image = context.getResources().getIdentifier(addImage, "drawable", context.getPackageName());
            question_all.add(add_image);
        }

        Log.e("test", "question_all : " + question_all);
        Collections.shuffle(question_all);
        Log.e("test", "question_all : " + question_all);

        this.question_one = question_all.get(0);
        this.question_two = question_all.get(1);
        this.question_three = question_all.get(2);
        this.question_four = question_all.get(3);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FourSelectImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FourSelectImageFragment newInstance(String param1, String param2) {
        FourSelectImageFragment fragment = new FourSelectImageFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_four_select_image, container, false);

        binding.questionImg.setImageResource(question_image);
        binding.questionText.setText(question_text);
        binding.questionText.setTextSize(DisplayFontSize.font_size_x_44);

        viewModel = new ViewModelProvider(this).get(FourSelectImageViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        Log.e("test", "점수 : " + ((QuestionnaireActivity)getActivity()).total_score);

        binding.choice1.choice.setOnClickListener(v->{
            Log.e("~~","choice1 click");
            if(binding.choice1.getChoice()){
                current_pos = 0;

                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                ((QuestionnaireActivity)getActivity()).current_answer = "";
                binding.choice1.setChoice(false);
                binding.choice1.choice.setSelected(false);

                if (binding.choice1.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                }
            }else{
                if (current_pos == 2) {
                    if (binding.choice2.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 3) {
                    if (binding.choice3.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 4) {
                    if (binding.choice4.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                }
                current_pos = 1;

                choice_init();
                binding.choice1.setChoice(true);
                binding.choice1.choice.setSelected(true);
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
//                ((QuestionnaireActivity)getActivity()).current_answer = binding.choice1.getChoiceImg();

                if (binding.choice1.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                    Log.e("~~","choice1 click good");
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score + 1;
                }
            }
        });

        binding.choice2.choice.setOnClickListener(v->{
            Log.e("~~","choice2 click"+ binding.choice2.getChoiceImg());
            if(binding.choice2.getChoice()){
                current_pos = 0;

                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                ((QuestionnaireActivity)getActivity()).current_answer = "";

                binding.choice2.setChoice(false);
                binding.choice2.choice.setSelected(false);

                if (binding.choice2.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                }
            }else{
                if (current_pos == 1) {
                    if (binding.choice1.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 3) {
                    if (binding.choice3.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 4) {
                    if (binding.choice4.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                }
                current_pos = 2;

                choice_init();
                binding.choice2.setChoice(true);
                binding.choice2.choice.setSelected(true);
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
//                ((QuestionnaireActivity)getActivity()).setCurrentAnswer(binding.choice2.getChoiceImg());

                if (binding.choice2.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                    Log.e("~~","choice2 click good");
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score + 1;
                }
            }

            Log.e("activity","choice2 click"+((QuestionnaireActivity)getActivity()).current_answer);
        });

        binding.choice3.choice.setOnClickListener(v->{
            Log.e("~~","choice3 click");
            Log.e("~~","choice3 click"+ binding.choice3.getChoiceImg());
            if(binding.choice3.getChoice()){
                current_pos = 0;

                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                binding.choice3.setChoice(false);
                binding.choice3.choice.setSelected(false);
                ((QuestionnaireActivity)getActivity()).current_answer = "";

                if (binding.choice3.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                }
            }else{
                if (current_pos == 1) {
                    if (binding.choice1.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 2) {
                    if (binding.choice2.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 4) {
                    if (binding.choice4.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                }
                current_pos = 3;

                choice_init();
                binding.choice3.setChoice(true);
                binding.choice3.choice.setSelected(true);
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
//                ((QuestionnaireActivity)getActivity()).setCurrentAnswer(binding.choice3.getChoiceImg());

                if (binding.choice3.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                    Log.e("~~","choice3 click good");
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score + 1;
                }
            }
        });

        binding.choice4.choice.setOnClickListener(v->{
            Log.e("~~","choice4 click");
            Log.e("~~","choice4 click"+ binding.choice4.getChoiceImg());

            if(binding.choice4.getChoice()){
                current_pos = 0;

                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                binding.choice4.setChoice(false);
                binding.choice4.choice.setSelected(false);
                ((QuestionnaireActivity)getActivity()).current_answer = "";

                if (binding.choice4.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                }
            }else{
                if (current_pos == 1) {
                    if (binding.choice1.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 2) {
                    if (binding.choice2.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                } else if (current_pos == 3) {
                    if (binding.choice3.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                        ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - 1;
                    }
                }
                current_pos = 4;

                choice_init();
                binding.choice4.setChoice(true);
                binding.choice4.choice.setSelected(true);
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
//                ((QuestionnaireActivity)getActivity()).setCurrentAnswer(binding.choice4.getChoiceImg());

                if (binding.choice4.choiceImg.getTag().toString().equals(String.valueOf(answer_image))) {
                    Log.e("~~","choice4 click good");
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score + 1;
                }
            }
        });

        ((QuestionnaireActivity)getActivity()).speakText(question_text);

//        return inflater.inflate(R.layout.fragment_four_select_image, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getQuestionOne().observe(getViewLifecycleOwner(), resId -> {
            Log.e("ImageUpdate", "Resource ID: " + resId);
            if (resId != null) {
                binding.choice1.choiceImg.setImageResource(resId);
                binding.choice1.choiceImg.setTag(resId);
            }
        });

        viewModel.getQuestionTwo().observe(getViewLifecycleOwner(), resId -> {
            Log.e("ImageUpdate", "Resource ID: " + resId);
            if (resId != null) {
                binding.choice2.choiceImg.setImageResource(resId);
                binding.choice2.choiceImg.setTag(resId);
            }
        });

        viewModel.getQuestionThree().observe(getViewLifecycleOwner(), resId -> {
            Log.e("ImageUpdate", "Resource ID: " + resId);
            if (resId != null) {
                binding.choice3.choiceImg.setImageResource(resId);
                binding.choice3.choiceImg.setTag(resId);
            }
        });

        viewModel.getQuestionFour().observe(getViewLifecycleOwner(), resId -> {
            Log.e("ImageUpdate", "Resource ID: " + resId);
            if (resId != null) {
                binding.choice4.choiceImg.setImageResource(resId);
                binding.choice4.choiceImg.setTag(resId);
            }
        });

        viewModel.setQuestionOne(question_one);
        viewModel.setQuestionTwo(question_two);
        viewModel.setQuestionThree(question_three);
        viewModel.setQuestionFour(question_four);
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

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

        Log.e("test", "점수! : " + ((QuestionnaireActivity)getActivity()).total_score);
    }
}