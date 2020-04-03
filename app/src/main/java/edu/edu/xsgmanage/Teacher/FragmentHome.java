package edu.edu.xsgmanage.Teacher;


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
import edu.edu.xsgmanage.Student.HomeAdapter;

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
        view=inflater.inflate(R.layout.fragment_tea_home,container,false);
        if(view!=null){
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
        map.put("names","修改成绩");
        map.put("nextActivity",UpdataGradeActivity.class);
        list.add(map);
        map=new HashMap<>();
        map.put("imageId",R.drawable.chaxun);
        map.put("names","添加成绩");
        map.put("nextActivity",AddGradeActivity.class);
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
