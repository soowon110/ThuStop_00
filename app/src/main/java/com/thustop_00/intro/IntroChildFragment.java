package com.thustop_00.intro;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.thustop_00.FragmentBase;
import com.thustop_00.R;
import com.thustop_00.databinding.FragmentIntroChildBinding;
import com.thustop_00.widgets.NotoTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntroChildFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroChildFragment extends FragmentBase {
    private FragmentIntroChildBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String text;
    private int res;
    private int position;


    public IntroChildFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment IntroChildFragment.
     */

    public static IntroChildFragment newInstance(String text, int res, int position) {
        IntroChildFragment fragment = new IntroChildFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, text);
        args.putInt(ARG_PARAM2, res);
        args.putInt(ARG_PARAM3, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            text = getArguments().getString(ARG_PARAM1);
            res = getArguments().getInt(ARG_PARAM2);
            position = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_intro_child, container, false);
        binding.tvIntroPage.setText(text);
        binding.ivIntroPage.setImageResource(res);
        switch (position) {
            case 0 : colorText(binding.tvIntroPage, R.string.tv_intro_page1_green ,"#64bb74");
            case 1 : break;
            default: colorText(binding.tvIntroPage, R.string.tv_intro_page3_green1, "#64bb74");
                     colorText(binding.tvIntroPage, R.string.tv_intro_page3_green2, "#64bb74");
        }
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    public void colorText(NotoTextView textView, int strAddress, String color) {
        /* Get the string of the view to be colored */
        String str = textView.getText().toString();
        /* Get string to be colored from address */
        String coloredStr = getString(strAddress);
        /* Instantiate spannable to color view's string*/
        Spannable span = (Spannable)textView.getText();
        /* Find position of start and end of coloredStr on str */
        int index_s=str.indexOf(coloredStr);
        int index_e = index_s+coloredStr.length();
        /* Color the view's string with upper variables */
        span.setSpan(new ForegroundColorSpan(Color.parseColor(color)),index_s,index_e, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}