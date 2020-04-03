package edu.edu.xsgmanage;

//import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;

import edu.edu.xsgmanage.constant.Constans;
import edu.edu.xsgmanage.domain.Message;
import edu.edu.xsgmanage.domain.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyUpdataPassActivity extends AppCompatActivity  implements View.OnClickListener {

    EditText Ed_userPass,Ed_new_userPass1,Ed_new_userPass2,Ed_messageName;
    TextView Ed_userName;
    Button button_updata,exit;
    private User user;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_updata_pass);
        setTitle("修改密码");
        gson=new Gson();
        getJsonToUser();
        initView();
        //为组件赋值
        initValue();
    }

    private void initValue() {
        Ed_userName.setText(user.getPk());
        Ed_userPass.setText(user.getFields().getUser_passs());
        Ed_messageName.setText(user.getFields().getMessage_name());
    }

    private void initView() {
        Ed_messageName=(EditText) findViewById(R.id.Ed_re_message_name);
        Ed_userPass=(EditText)findViewById(R.id.Ed_re_user_pass);
        Ed_new_userPass1=(EditText)findViewById(R.id.Ed_new_user_pass1);
        Ed_new_userPass2=(EditText)findViewById(R.id.Ed_new_user_pass2);
        Ed_userName=(TextView)findViewById(R.id.user_name);
        button_updata=(Button)findViewById(R.id.button_updata);
        exit=(Button)findViewById(R.id.button_gb);

        //        设置监听事件
        button_updata.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    private void getJsonToUser() {
        SharedPreferences sharedPreferences=this.getSharedPreferences("my_Info", Context.MODE_PRIVATE);
        String my_Info=sharedPreferences.getString("my_Info","");
        //取出数据,第一个参数是写入是的键，第二个参数是如果没有获取到数据就默认返回的值。
        if(!my_Info.equals("")){
            user=gson.fromJson(my_Info, User.class);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.button_updata:
                checkUpdata();
                break;
            case R.id.button_gb:
                Toast.makeText(getApplicationContext(),"即将返回", Toast.LENGTH_SHORT).show();
                MyUpdataPassActivity.this.finish();
                break;
        }
    }

    private void checkUpdata(){
        User.Fields fields=user.getFields();
        if(Ed_userPass.getText().toString().equals(user.getFields().getUser_passs())
                &&
                (Ed_new_userPass1.getText().toString().equals(Ed_new_userPass2.getText().toString().trim()))
                &&!Ed_new_userPass1.getText().toString().trim().equals("")){
            AlertDialog.Builder dialog;
            dialog=new AlertDialog.Builder(MyUpdataPassActivity.this);
            dialog.setTitle("确认对话框");
            dialog.setMessage("确认修改");
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    updataUser();
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        }
        else {
            Toast.makeText(getApplicationContext(),"请填写正确的信息", Toast.LENGTH_SHORT).show();
        }
    }
    private void updataUser() {
        Toast.makeText(getApplicationContext(),"进入修改信息事件", Toast.LENGTH_SHORT).show();
        User.Fields fields=user.getFields();
        fields.setUser_passs(Ed_new_userPass1.getText().toString().trim());
        user.setFields(fields);
        requestNet("user/");
    }

    private void requestNet(String url) {
        Toast.makeText(getApplicationContext(),"进入网络请求", Toast.LENGTH_SHORT).show();
        url= Constans.BASEURL+url;
        OkHttpClient okHttpClient = new OkHttpClient();
//         gson=new Gson();
        String requestData=gson.toJson(user);
        RequestBody fromBody=RequestBody.create(Constans.FORM_CONTENT_TYPE,"action=updata_user&user_data="+requestData);
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
        Message message = gson.fromJson(result, Message.class);
        System.out.println("捕获响应进行判断，是否成功");
        if("success".equals(message.msg)){
            //更新本地缓存
//            gson=new Gson();
            String userData=gson.toJson(user);
            SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("my_Info",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("my_Info",userData);
            editor.commit();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"修改密码成功,即将关闭该页面", Toast.LENGTH_SHORT).show();
//                            进行跳转
                    MyUpdataPassActivity.this.finish();
                }
            });
        }
        else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"用户不存在", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
