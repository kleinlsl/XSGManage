package edu.edu.xsgmanage.Teacher;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import edu.edu.xsgmanage.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLocation extends Fragment {

    private String fragmentText;

    private TextView fragmentTextView;
    public FragmentLocation(String fragmentText) {
        this.fragmentText=fragmentText;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tea_loction,container,false);
        return view;
    }

}
