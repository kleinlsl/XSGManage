package edu.edu.xsgmanage.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.edu.xsgmanage.R;

import edu.edu.xsgmanage.constant.Constans;
import edu.edu.xsgmanage.domain.Grade;
import edu.edu.xsgmanage.domain.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdataGradeActivity extends AppCompatActivity {

    private ListView listGrade;
    private TeaGradeAdapter teaGradeAdapter;
    private List<Map<String,Object>> list;
    private List<Grade> gradeList;
    private TextView username;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_grade);
        setTitle("成绩页面");
        getJsonToUser();
        init();
    }
    private void getJsonToUser() {
        Gson gson=new Gson();
//        解析user数据
        //取出数据,第一个参数是写入是的键，第二个参数是如果没有获取到数据就默认返回的值。
        SharedPreferences sharedPreferences=this.getSharedPreferences("my_Info", Context.MODE_PRIVATE);
        String my_Info=sharedPreferences.getString("my_Info","");
        if(!my_Info.equals("")){
            user=gson.fromJson(my_Info, User.class);
        }
    }
    private void init() {
        listGrade=(ListView) findViewById(R.id.lst_grade);
        username=(TextView) findViewById(R.id.c_user_name);
        requestNet("grade/");
    }
    private void requestNet(String url) {
        url= Constans.BASEURL+url;
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody fromBody=RequestBody.create(Constans.FORM_CONTENT_TYPE,"action=find_grade_all_stu");
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
                        Toast.makeText(getApplicationContext(),"请求成绩列表失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String find_grade_stu_result=response.body().string();
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("net_Response",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("find_grade_stu_result",find_grade_stu_result);
                editor.commit();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getApplicationContext(),"数据以刷新", Toast.LENGTH_SHORT).show();
                        initValue();
                    }
                });
            }
        });
    }
    private void initValue() {
        username.setText(user.getFields().getMessage_name());
        //        解析News数据
        Gson gson=new Gson();
        SharedPreferences  sharedPreferences=this.getSharedPreferences("net_Response", Context.MODE_PRIVATE);
        String find_grade_stu_result=sharedPreferences.getString("find_grade_stu_result","");
        list=new ArrayList<>();
        if(!find_grade_stu_result.equals("")){
            gradeList=gson.fromJson(find_grade_stu_result, new TypeToken<List<Grade>>(){}.getType());
        }
        Map<String,Object> map;
        if(gradeList!=null){
            for(Grade grade:gradeList){
                if(grade.getCourse_info().getFields().getCourse_teacher().equals(user.getPk())){
                    map=new HashMap<>();
                    map.put("grade",grade);
                    map.put("nextActivity",null);
                    list.add(map);
                }
            }
            teaGradeAdapter=new TeaGradeAdapter(list,this.getApplicationContext(),this);
            listGrade.setAdapter(teaGradeAdapter);
        }
        else {
            Toast.makeText(this,"成绩对象为空", Toast.LENGTH_SHORT).show();
        }

    }

}
