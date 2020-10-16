package com.thustop_00;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thustop_00.databinding.FragmentMainBinding;
import com.thustop_00.databinding.FragmentRequestTownServiceBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestTownServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestTownServiceFragment extends FragmentBase {
    FragmentRequestTownServiceBinding _binding;


    public RequestTownServiceFragment() {
        // Required empty public constructor
    }


    public static RequestTownServiceFragment newInstance() {
        RequestTownServiceFragment fragment = new RequestTownServiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentRequestTownServiceBinding.inflate(inflater);
        _binding.setRequestTownFrag(this);
        _listener.setToolbar(true, true, false);
        _listener.setTitle("우리 동네 서비스 요청");
        _listener.showActionBar(true);
        return _binding.getRoot();

    }

    public void onRequestClick(View view){
        _listener.setFragment(DoneFragment.newInstance(getString(R.string.tv_request_done), getString(R.string.tv_request_continue),true));
    }
}