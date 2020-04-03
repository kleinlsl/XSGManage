package edu.edu.xsgmanage.Student;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.edu.xsgmanage.R;
import edu.edu.xsgmanage.domain.News;
import edu.edu.xsgmanage.domain.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNews extends Fragment {

    private ListView listMessage;
    private NewsAdapter newsAdapter;
    private List<Map<String,Object>> list;
    private List<News> newsList;
    private View view;
    private String fragmentText;
    private User user;
    private TextView fragmentTextView;
    public FragmentNews(String fragmentText) {
        this.fragmentText=fragmentText;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_stu_news,container,false);
        if(view!=null){
            getJsonToUser();
            init();
//            Toast.makeText(view.getContext(),"FragmentNews", Toast.LENGTH_SHORT).show();
            initValue();
        }
        return view;
    }

    public void initValue() {
        //        解析News数据
        Gson gson=new Gson();
        SharedPreferences  sharedPreferences=view.getContext().getSharedPreferences("net_Response", Context.MODE_PRIVATE);
        String find_message_stu_result=sharedPreferences.getString("find_message_stu_result","");

        list=new ArrayList<>();
        if(!find_message_stu_result.equals("")){
            newsList=gson.fromJson(find_message_stu_result, new TypeToken<List<News>>(){}.getType());
        }
        Map<String,Object> map;
        if(newsList!=null){
            for(News news:newsList){
                map=new HashMap<>();
                map.put("sender_name",news.getFields().getSender());
                map.put("message",news.getFields().getMessage());
                map.put("news",news);
                list.add(map);
            }
            newsAdapter=new NewsAdapter(list,getContext());
            listMessage.setAdapter(newsAdapter);
        }
        else {
            Toast.makeText(view.getContext(),"消息对象为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void getJsonToUser() {
        Gson gson=new Gson();
//        解析user数据
        //取出数据,第一个参数是写入是的键，第二个参数是如果没有获取到数据就默认返回的值。
        SharedPreferences sharedPreferences=view.getContext().getSharedPreferences("my_Info", Context.MODE_PRIVATE);
        String my_Info=sharedPreferences.getString("my_Info","");
        if(!my_Info.equals("")){
            user=gson.fromJson(my_Info, User.class);
        }
    }
    private void init() {
//        初始化组件
        listMessage=(ListView)view.findViewById(R.id.lst_message);

    }

}
