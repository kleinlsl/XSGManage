package edu.edu.xsgmanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.edu.xsgmanage.constant.Constans;
import edu.edu.xsgmanage.domain.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyClassActivity extends AppCompatActivity {
    //定义组件
    ListView listView=null;
    List<User> userList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);
        setTitle("我的班级");
        initView();
    }

    private void initView() {
        listView=(ListView)findViewById(R.id.lst_contacts);

        requestNet("user/");
    }

    private void requestNet(String url) {
        url= Constans.BASEURL+url;
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody fromBody=RequestBody.create(Constans.FORM_CONTENT_TYPE,"action=find_all_stu");
        final Request request = new Request.Builder()
                .url(url)
                .post(fromBody)//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String requestResult=response.body().string();
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("net_Response",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("find_all_stu_result",requestResult);
                editor.commit();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"请求成功", Toast.LENGTH_SHORT).show();
                        getClassUsers();
                    }
                });
            }
        });
    }

    private void getClassUsers() {

//        获取请求到的信息
        SharedPreferences sharedPreferences=this.getSharedPreferences("net_Response", Context.MODE_PRIVATE);
        String find_all_stu_result=sharedPreferences.getString("find_all_stu_result","");
        if(find_all_stu_result.equals("")){
            Toast.makeText(getApplicationContext(),"请求失败", Toast.LENGTH_SHORT).show();
        }
        else {
            Gson gson=new Gson();
            userList=gson.fromJson(find_all_stu_result,new TypeToken<List<User>>(){}.getType());
            setClassList();
        }
    }

    private void setClassList() {
        if(userList!=null){
            List<Map<String,Object>> mContactData=new ArrayList<Map<String, Object>>();
            for(User user:userList){
                Map<String,Object> map=new HashMap<String, Object>() ;
                map.put("message_name",user.getFields().message_name);
                map.put("message_phone",user.getFields().message_phone);
                mContactData.add(map);
            }
            //定义数据源
            String[] from={"message_name","message_phone"};
            //定义布局控件ID
            int[] to={R.id.tv_lst_message_name,R.id.tv_lst_message_phone};
            SimpleAdapter listItemAdapter=new SimpleAdapter(MyClassActivity.this,mContactData,R.layout.my_class_list,from,to);
            //添加并显示
            listView.setAdapter(listItemAdapter);
        }
    }
}
