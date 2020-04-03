package edu.edu.xsgmanage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText Ed_userName,Ed_userPass,Ed_messageName,Ed_messageAge,Ed_messagePhone;
    RadioButton Ed_messageSex;
    Button button_regiser,exit;
    User reUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        Ed_userName=(EditText)findViewById(R.id.Ed_re_user_name);
        Ed_userPass=(EditText)findViewById(R.id.Ed_re_user_pass);
        Ed_messageName=(EditText)findViewById(R.id.Ed_re_message_name);
        Ed_messageAge =(EditText)findViewById(R.id.Ed_re_message_age);
        Ed_messagePhone=(EditText)findViewById(R.id.Ed_re_message_phone);
        Ed_messageSex=(RadioButton)findViewById(R.id.RB_message_sex_nan);
        Ed_messageSex.setChecked(true);
        button_regiser=(Button)findViewById(R.id.button_regiser);
        exit=(Button) findViewById(R.id.button_gb);


        button_regiser.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_regiser:
                Toast.makeText(getApplicationContext(),"进入注册事件", Toast.LENGTH_SHORT).show();
                regiserUser();
                break;
            case R.id.button_gb:
                AlertDialog.Builder dialog=new AlertDialog.Builder(RegisterActivity.this);
                dialog.setTitle("退出对话框");
                dialog.setMessage("确认退出");
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RegisterActivity.this.finish();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
                break;
        }
    }

    private void regiserUser() {
        reUser=new User();
        User.Fields fields=new User.Fields();
        if(Ed_userName.getText().toString().trim().equals("")
                ||"".equals(Ed_messageName.getText().toString().trim())
                ||"".equals(Ed_userPass.getText().toString().trim())
                ||"".equals(Ed_messageAge.getText().toString().trim())
                ||"".equals(Ed_messagePhone.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"请填写完整", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Ed_messageSex.isChecked()){
            fields.setMessage_sex("男");
        }
        else {
            fields.setMessage_sex("女");
        }
        reUser.setModel("app.User");
        reUser.setPk(Ed_userName.getText().toString().trim());
        fields.setUser_id("学生");
        fields.setMessage_name(Ed_messageName.getText().toString().trim());
        fields.setUser_passs(Ed_userPass.getText().toString().trim());
        fields.setMessage_age(Integer.parseInt(Ed_messageAge.getText().toString().trim()));
        fields.setMessage_phone(Ed_messagePhone.getText().toString().trim());
        reUser.setFields(fields);
        requestNet("user/");
    }

    private void requestNet(String url) {
        url= Constans.BASEURL+url;
        OkHttpClient okHttpClient = new OkHttpClient();
        Gson gson=new Gson();
        String requestData=gson.toJson(reUser);
        RequestBody fromBody=RequestBody.create(Constans.FORM_CONTENT_TYPE,"action=add_user&user_data="+requestData);
        Request request = new Request.Builder()
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
                Gson gson=new Gson();
                Message message = gson.fromJson(response.body().string(), Message.class);
                System.out.println("捕获响应进行判断，是否成功");
                if("success".equals(message.msg)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            进行跳转
                            Bundle bundle=new Bundle();
                            Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"该用户已存在", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
