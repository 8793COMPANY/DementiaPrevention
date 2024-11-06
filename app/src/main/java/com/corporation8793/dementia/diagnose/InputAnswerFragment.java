package com.corporation8793.dementia.diagnose;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.data.Question;
import com.corporation8793.dementia.databinding.FragmentInputAnswerBinding;
import com.corporation8793.dementia.util.DisplayFontSize;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputAnswerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputAnswerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentInputAnswerBinding binding;

    String question_text;
    int question_img;
    String answer_question = "";

    public InputAnswerFragment() {
        // Required empty public constructor
    }

    public InputAnswerFragment(Question question, int question_img) {
        if (question.getType().equals("사칙연산 문제")) {
            this.question_text = "다음 문제의 정답이 무엇입니까?";

            answer_question = question.getScore3().trim();
        } else if (question.getType().equals("언어추론 문제")) {
            this.question_text = question.getContents().split("/")[0].trim();

            answer_question = question.getScore1().split(":")[0];
        }

        this.question_img = question_img;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputAnswerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InputAnswerFragment newInstance(String param1, String param2) {
        InputAnswerFragment fragment = new InputAnswerFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input_answer, container, false);

        binding.questionText.setText(question_text);
        binding.questionText.setTextSize(DisplayFontSize.font_size_x_44);
        binding.questionImg.setBackgroundResource(question_img);

        binding.editAnswer.setTextSize(DisplayFontSize.font_size_x_40);
        binding.editAnswer.setPadding((int) DisplayFontSize.font_size_x_50, 0, 0, 0);

        binding.editAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.editAnswer.getText().toString().trim().isEmpty()) {
                    ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                } else {
                    ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.editAnswer.getText().toString().trim().isEmpty()) {
                    ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                } else {
                    ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.editAnswer.getText().toString().trim().isEmpty()) {
                    ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                } else {
                    ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
                }
            }
        });

        ((QuestionnaireActivity)getActivity()).speakText(question_text);

//        return inflater.inflate(R.layout.fragment_input_answer, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.e("testt", "onDestroyView()");
        Log.e("testt", "onDestroyView()" + answer_question);
        Log.e("testt", "onDestroyView()" + binding.editAnswer.getText().toString().trim());

        if (binding.editAnswer.getText().toString().trim().equals(answer_question)) {
            ((QuestionnaireActivity)getActivity()).total_score = ((QuestionnaireActivity)getActivity()).total_score + 1;
        }
    }
}