package com.corporation8793.dementia.diagnose;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.data.Question;
import com.corporation8793.dementia.databinding.FragmentThreeSelectBinding;
import com.corporation8793.dementia.util.DisplayFontSize;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThreeSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThreeSelectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentThreeSelectBinding binding;

    String question_text, question_one, question_two, question_three;
    int question_image;

    Question question;
    int score_num, current_pos;

    public ThreeSelectFragment() {
        // Required empty public constructor
    }

    public ThreeSelectFragment(Question question, int question_image) {
        this.question_text = question.getContents();
        this.question_one = question.getScore1().split(":")[0];
        this.question_two = question.getScore2().split(":")[0];
        this.question_three = question.getScore3().split(":")[0];
        this.question_image = question_image;

        this.question = question;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThreeSelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThreeSelectFragment newInstance(String param1, String param2) {
        ThreeSelectFragment fragment = new ThreeSelectFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_three_select, container, false);

        binding.questionImg.setBackgroundResource(question_image);
        binding.questionText.setText(question_text);
        binding.questionText.setTextSize(DisplayFontSize.font_size_x_44);

        ThreeSelectViewModel viewModel = new ViewModelProvider(this).get(ThreeSelectViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.setQuestionOne(question_one);
        viewModel.setQuestionTwo(question_two);
        viewModel.setQuestionThree(question_three);

        binding.choice1.choiceText.setTextSize(DisplayFontSize.font_size_x_32);
        binding.choice2.choiceText.setTextSize(DisplayFontSize.font_size_x_32);
        binding.choice3.choiceText.setTextSize(DisplayFontSize.font_size_x_32);

        current_pos = 0;

        binding.choice1.choice.setOnClickListener(v->{
            Log.e("~~","choice1 click");
            if(binding.choice1.getChoice()){
                Log.e("~~","choice1 click off");
                current_pos = 0;

                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                ((QuestionnaireActivity)getActivity()).current_answer = "";

                score_num = Integer.parseInt(question.getScore1().split(":")[1].trim());
                Log.e("test", "score_num : " + score_num);
                ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - score_num;

                binding.choice1.setChoice(false);
                binding.choice1.choice.setSelected(false);
            }else{
                Log.e("~~","choice1 click on");

                if (current_pos == 2) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score -
                            Integer.parseInt(question.getScore2().split(":")[1].trim());
                } else if (current_pos == 3) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score -
                            Integer.parseInt(question.getScore3().split(":")[1].trim());
                }
                current_pos = 1;

                choice_init();
                binding.choice1.setChoice(true);
                binding.choice1.choice.setSelected(true);
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
                ((QuestionnaireActivity)getActivity()).current_answer = binding.choice1.getChoiceText();

                score_num = Integer.parseInt(question.getScore1().split(":")[1].trim());
                Log.e("test", "score_num : " + score_num);
                ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score + score_num;
            }
        });

        binding.choice2.choice.setOnClickListener(v->{
            Log.e("~~","choice2 click"+ binding.choice2.getChoiceText());
            if(binding.choice2.getChoice()){
                current_pos = 0;

                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                ((QuestionnaireActivity)getActivity()).current_answer = "";

                score_num = Integer.parseInt(question.getScore2().split(":")[1].trim());
                Log.e("test", "score_num : " + score_num);
                ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - score_num;

                binding.choice2.setChoice(false);
                binding.choice2.choice.setSelected(false);
            }else{
                if (current_pos == 1) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score -
                            Integer.parseInt(question.getScore1().split(":")[1].trim());
                } else if (current_pos == 3) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score -
                            Integer.parseInt(question.getScore3().split(":")[1].trim());
                }
                current_pos = 2;

                choice_init();
                binding.choice2.setChoice(true);
                binding.choice2.choice.setSelected(true);
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
                ((QuestionnaireActivity)getActivity()).setCurrentAnswer(binding.choice2.getChoiceText());

                score_num = Integer.parseInt(question.getScore2().split(":")[1].trim());
                Log.e("test", "score_num : " + score_num);
                ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score + score_num;
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

                score_num = Integer.parseInt(question.getScore3().split(":")[1].trim());
                Log.e("test", "score_num : " + score_num);
                ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score - score_num;
            }else{
                if (current_pos == 1) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score -
                            Integer.parseInt(question.getScore1().split(":")[1].trim());
                } else if (current_pos == 2) {
                    ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score -
                            Integer.parseInt(question.getScore2().split(":")[1].trim());
                }
                current_pos = 3;

                choice_init();
                binding.choice3.setChoice(true);
                binding.choice3.choice.setSelected(true);
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
                ((QuestionnaireActivity)getActivity()).setCurrentAnswer(binding.choice3.getChoiceText());

                score_num = Integer.parseInt(question.getScore3().split(":")[1].trim());
                Log.e("test", "score_num : " + score_num);
                ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score + score_num;
            }
        });

        ((QuestionnaireActivity)getActivity()).speakText(question_text);

//        return inflater.inflate(R.layout.fragment_three_select, container, false);
        return binding.getRoot();
    }

    void choice_init() {
        binding.choice1.setChoice(false);
        binding.choice2.setChoice(false);
        binding.choice3.setChoice(false);

        binding.choice1.choice.setSelected(false);
        binding.choice2.choice.setSelected(false);
        binding.choice3.choice.setSelected(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
