package edu.edu.xsgmanage.Student;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.edu.xsgmanage.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {

    private GridView gridView;
    private HomeAdapter homeAdapter;
    private List<Map<String,Object>> list;
    private View view;
    private String fragmentText;

    private TextView fragmentTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_stu_home,container,false);
        if(view!=null){
//            getJsonToUser();
            init();
            Toast.makeText(view.getContext(),"FragmentNews", Toast.LENGTH_SHORT).show();
            initValue();
        }

        return view;
    }

    private void initValue() {
        list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("imageId",R.drawable.chaxun);
        map.put("names","我的成绩");
        map.put("nextActivity",GradeActivity.class);
        list.add(map);
        homeAdapter=new HomeAdapter(list,getContext(),getActivity());
        gridView.setAdapter(homeAdapter);
    }

    private void init() {
        gridView=(GridView)view.findViewById(R.id.gv_home);
    }

    public FragmentHome(String fragmentText) {
        this.fragmentText=fragmentText;
    }
}
