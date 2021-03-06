package com.thustop.thestop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.thustop.databinding.FragmentAddRouteTimeSetBinding;
import com.thustop.thestop.model.Address;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRouteTimeSetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRouteTimeSetFragment extends FragmentBase implements MainActivity.onBackPressedListener {
    FragmentAddRouteTimeSetBinding binding;

    int h, min;
    String noon;
    final Calendar cal = Calendar.getInstance();
    Address startLocation, endLocation;
    private static final DecimalFormat FORMATTER = new DecimalFormat("00");

    public AddRouteTimeSetFragment() {
        // Required empty public constructor
    }
    /*
    public static AddRouteTimeSetFragment newInstance(String param1, String param2) {
        AddRouteTimeSetFragment fragment = new AddRouteTimeSetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    public static AddRouteTimeSetFragment newInstance(Address startLocation, Address endLocation) {
        AddRouteTimeSetFragment fragment = new AddRouteTimeSetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.startLocation = startLocation;
        fragment.endLocation = endLocation;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddRouteTimeSetBinding.inflate(inflater);
        binding.setAddRouteTimefrag(this);
        //binding.tvTimePicker.setText((+String.valueOf(h)+"시 "+String.valueOf(min)+"분");)
        setCurTime();
        binding.tvStart.setText(startLocation.getAddress());
        binding.tvEnd.setText(endLocation.getAddress());

        _listener.setToolbarStyle(_listener.WHITE_BACK, "");
        _listener.setOnBackPressedListener(this);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    public void onTimeSetClick(View view) {
        CustomTimePickerDialog timePickerDialog = new CustomTimePickerDialog(getActivity());
        timePickerDialog.setDialogListener(new CustomTimePickerDialog.CustomTimePickerDialogListener() {
            @Override
            public void onOkClick(int hour, int minute, String n) {
                h = hour;
                min = minute;
                noon = n;
                binding.tvTimePicker.setText(noon+"              "+String.valueOf(h)+"              "+String.valueOf(FORMATTER.format(min)));
            }


        });
        timePickerDialog.show();

    }

    private void setCurTime(){
        String n;
        if(cal.get(Calendar.AM_PM) == 0) {
            n = "오전";
        } else {
            n = "오후";
        }
        int h = cal.get(Calendar.HOUR);
        int m = (cal.get(Calendar.MINUTE)/10)*10;
        binding.tvTimePicker.setText(n+"              "+String.valueOf(h)+"              "+String.valueOf(FORMATTER.format(m)));

    }

    @Override
    public void onBack() {
        _listener.addFragmentNotBackStack(AddRouteMapFragment.newInstance(startLocation, endLocation));
    }


}