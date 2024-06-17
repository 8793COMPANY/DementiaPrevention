package com.corporation8793.dementia.diagnose;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.databinding.FragmentThreeChoiceBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThreeChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThreeChoiceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentThreeChoiceBinding binding;

    String question_number, question_text;

    int question_icon;

    public ThreeChoiceFragment() {
        // Required empty public constructor
    }

    public ThreeChoiceFragment(String question_number, Diagnose diagnose) {
        // Required empty public constructor
        this.question_number = question_number;
        this.question_text = diagnose.title;
        this.question_icon = diagnose.drawable;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment threeChoiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThreeChoiceFragment newInstance(String param1, String param2) {
        ThreeChoiceFragment fragment = new ThreeChoiceFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_three_choice, container, false);


        binding.questionImg.setBackgroundResource(question_icon);
        binding.questionNumber.setText("Q"+question_number+".");
        binding.questionText.setText(question_text);




        binding.choice1.choice.setOnClickListener(v->{
            Log.e("~~","choice1 click");
            if(binding.choice1.getChoice()){
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                binding.choice1.setChoice(false);
                binding.choice1.choice.setSelected(false);
            }else{
                choice_init();
                binding.choice1.setChoice(true);
                binding.choice1.choice.setSelected(true);
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
            }
        });

        binding.choice2.choice.setOnClickListener(v->{
            Log.e("~~","choice2 click");
            if(binding.choice2.getChoice()){
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                binding.choice2.setChoice(false);
                binding.choice2.choice.setSelected(false);
            }else{
                choice_init();
                binding.choice2.setChoice(true);
                binding.choice2.choice.setSelected(true);
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
            }
        });


        binding.choice3.choice.setOnClickListener(v->{
            Log.e("~~","choice3 click");
            if(binding.choice3.getChoice()){
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(false);
                binding.choice3.setChoice(false);
                binding.choice3.choice.setSelected(false);
            }else{
                choice_init();
                binding.choice3.setChoice(true);
                binding.choice3.choice.setSelected(true);
                ((QuestionnaireActivity)getActivity()).next_end_btn.setEnabled(true);
            }
        });

        ((QuestionnaireActivity)getActivity()).speakText(question_text);

        return binding.getRoot();
    }

    void choice_init(){
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