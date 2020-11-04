package com.thustop_00;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentRequestTownServiceBinding.inflate(inflater);
        _binding.setRequestTownFrag(this);

        _listener.setToolbarStyle(true, true);
        _listener.setTitle(false, "우리 동네 서비스 요청");
        _listener.showToolbarVisibility(true);

        return _binding.getRoot();
    }

    public void onRequestClick(View view) {
        _listener.setFragment(DoneFragment.newInstance(getString(R.string.tv_request_done), getString(R.string.tv_request_continue), true));
    }
    // TODO 말 안듣는 모서리 자식 둥글게 해야함
    public void onRegionSelectClick(View view) {
        CustomRegionSelectorDialog regionSelectorDialog = new CustomRegionSelectorDialog(getContext());
        regionSelectorDialog.setDialogListener(new CustomRegionSelectorDialog.RegionSelectorListener() {
            @Override
            public void onSelect(String state, String city) {
                _binding.tvTownSel.setText(String.format("%s %s", state, city));
                _binding.tvTownSel.setTextColor(getResources().getColor(R.color.TextBlack));
            }
        });
        regionSelectorDialog.show();
        //regionSelectorDialog.getWindow().setBackgroundDrawable(ColorDrawable.);
    }
}