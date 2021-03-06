package com.thustop.thestop;

import android.content.Context;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.thustop.thestop.widgets.NotoTextView;

public class FragmentBase extends Fragment {
    protected OnFragmentInteractionListener _listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            _listener = (OnFragmentInteractionListener) context;
            _listener.setOnBackPressedListener(null);
            _listener.lockDrawer(true);
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }

    /* This method colors specific words in TextView*/
    public void colorText(NotoTextView textView, int strAddress, int color) {
        String str = textView.getText().toString();
        String coloredStr = getString(strAddress);
        /* Instantiate spannable to color view's string*/
        Spannable span = (Spannable)textView.getText();
        /* Find position of start and end of coloredStr on str */
        int index_s=str.indexOf(coloredStr);
        int index_e = index_s+coloredStr.length();
        /* Color the view's string with upper variables */
        span.setSpan(new ForegroundColorSpan(color),index_s,index_e, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
