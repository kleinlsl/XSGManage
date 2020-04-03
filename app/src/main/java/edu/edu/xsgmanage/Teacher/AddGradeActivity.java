package edu.edu.xsgmanage.Teacher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import edu.edu.xsgmanage.R;
import edu.edu.xsgmanage.constant.Constans;
import edu.edu.xsgmanage.domain.Message;
import edu.edu.xsgmanage.domain.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddGradeActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText gradeUserName,gradeCourseId,gradeValue;
    private Button buttonComit,exit;
    private User user;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grade);
        gson=new Gson();
        getJsonToUser();
        init();
    }

    private void init() {
        gradeUserName=(EditText)findViewById(R.id.Ed_grade_user_name);
        gradeCourseId=(EditText) findViewById(R.id.Ed_grade_course_id);
        gradeValue=(EditText)findViewById(R.id.Ed_grade_value);
        buttonComit=(Button)findViewById(R.id.button_updata);
        exit=(Button)findViewById(R.id.button_gb);

        //        设置监听器
        buttonComit.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    private void getJsonToUser() {
//        解析user数据
        //取出数据,第一个参数是写入是的键，第二个参数是如果没有获取到数据就默认返回的值。
        SharedPreferences sharedPreferences=getSharedPreferences("my_Info", Context.MODE_PRIVATE);
        String my_Info=sharedPreferences.getString("my_Info","");
        if(!my_Info.equals("")){
            user=gson.fromJson(my_Info, User.class);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_updata:
                checkInfo();
                break;
            case R.id.button_gb:
                Toast.makeText(getApplicationContext(),"即将返回", Toast.LENGTH_SHORT).show();
                AddGradeActivity.this.finish();
                break;
        }
    }

    private void checkInfo() {
        if(gradeUserName.getText().toString().trim().equals("")
                ||gradeCourseId.getText().toString().trim().equals("")
                ||gradeValue.getText().toString().trim().equals("")){
            Toast.makeText(this.getApplicationContext(),"请将信息填写完整",Toast.LENGTH_SHORT).show();
        }
        else {
            AlertDialog.Builder dialog=new AlertDialog.Builder(this);
            dialog.setTitle("确认添加");
            dialog.setMessage("确认给："+gradeUserName.getText().toString()+"的课程："+gradeCourseId.getText().toString()+"添加成绩："+gradeValue.getText().toString()+";");
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    requestNet("grade/");
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        }
    }

    private void requestNet(String url) {

        Toast.makeText(getApplicationContext(),"进入网络请求", Toast.LENGTH_SHORT).show();
        url= Constans.BASEURL+url;
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody fromBody=RequestBody.create(Constans.FORM_CONTENT_TYPE,"" +
                "action=add_grade_stu" +
                "&user_name="+gradeUserName.getText().toString().trim()+"" +
                "&course_id="+gradeCourseId.getText().toString().trim()+"" +
                "&grade_value="+gradeValue.getText().toString().trim());
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
                requestSuccess(response.body().string());
            }
        });
    }
    private void requestSuccess(String result) {

        final Message message = gson.fromJson(result, Message.class);
        System.out.println("捕获响应进行判断，是否成功");
        if("success".equals(message.msg)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"添加成功", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"添加失败，服务器响应："+message.getMsg(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
