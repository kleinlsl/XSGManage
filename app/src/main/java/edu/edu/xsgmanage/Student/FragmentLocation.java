package edu.edu.xsgmanage.Student;


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
    private View view;
    private TextView fragmentTextView;
    public FragmentLocation(String fragmentText) {
        this.fragmentText=fragmentText;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_stu_location,container,false);
        return view;
    }

}
