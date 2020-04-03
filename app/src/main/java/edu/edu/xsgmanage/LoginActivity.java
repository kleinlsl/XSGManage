package edu.edu.xsgmanage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.io.IOException;
import edu.edu.xsgmanage.Student.StuMainActivity;
import edu.edu.xsgmanage.Teacher.TeaMainActivity;
import edu.edu.xsgmanage.Utils.HttpUtil;
import edu.edu.xsgmanage.Utils.reMeUtil;
import edu.edu.xsgmanage.constant.Constans;
import edu.edu.xsgmanage.domain.Message;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    private EditText username,password;
    private LinearLayout Linear_netAddress;
    private CheckBox reMe;
    private RadioButton reStu,reTea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i("LoginActivity","onCreate()");
        initView();
    }

    /***
     *初始化布局
     *@param
     *@return void
     *@author
     *created at 2019/3/19 23:13
     */
    private void initView() {
        Intent intentdata=this.getIntent();
//        组件初始化
        username=(EditText)findViewById(R.id.EditText_login_username);
        password=(EditText)findViewById(R.id.EditText_login_passwd);
        reMe=(CheckBox)findViewById(R.id.re_Me);
        reStu=(RadioButton)findViewById(R.id.re_stu);
        reTea=(RadioButton)findViewById(R.id.re_tea);
        Button b_login=(Button)findViewById(R.id.button_login);
        Button b_register=(Button)findViewById(R.id.button_go_register);
        Linear_netAddress=(LinearLayout) findViewById(R.id.Linear_netAddress);

//      设置文本框内图标大小  https://www.cnblogs.com/hehe520/p/6329960.html
//        https://blog.csdn.net/qq_17290407/article/details/75393951
        Drawable passwddrawableLeft = getResources().getDrawable(R.drawable.passwdicon);
        Drawable usernamedrawableLeft=getResources().getDrawable(R.drawable.usernameicon);
        usernamedrawableLeft.setBounds(0,0,100,100);
        passwddrawableLeft.setBounds(0,0,100,100);
        username.setCompoundDrawables(usernamedrawableLeft,null,null,null);
        password.setCompoundDrawables(passwddrawableLeft,null,null,null);
        password.setCompoundDrawablePadding(4);            //暂时可用可不用

        reStu.setChecked(true);
        reMe.setChecked(true);

//        获取记住的的登陆信息
        SharedPreferences sharedPreferences=this.getSharedPreferences("login_Info", Context.MODE_PRIVATE);
        String name=sharedPreferences.getString("userName","");
        String pass=sharedPreferences.getString("userPass","");
//       清除所有的网络请求数据
        sharedPreferences=this.getSharedPreferences("net_Response",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();

        //取出数据,第一个参数是写入是的键，第二个参数是如果没有获取到数据就默认返回的值。
        username.setText(name);
        password.setText(pass);
        b_login.setOnClickListener(LoginActivity.this);
        b_register.setOnClickListener(LoginActivity.this);
        Linear_netAddress.setOnClickListener(LoginActivity.this);
        
//        显示弹框
        showFialog();
    }

    private void showFialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.get_net_address_layout, null);
        final EditText netAddress = (EditText) textEntryView.findViewById(R.id.net_address);
        SharedPreferences sharedPreferences=this.getSharedPreferences("net_Address", Context.MODE_PRIVATE);
        String net_Address_Result=sharedPreferences.getString("net_Address_Result","");
        if(!net_Address_Result.equals("")){
            netAddress.setText(net_Address_Result);
        }
        else {
            netAddress.setText(Constans.BASEURL);
        }
        AlertDialog.Builder ad1 = new AlertDialog.Builder(LoginActivity.this);
        ad1.setTitle("配置服务器:");
        ad1.setIcon(android.R.drawable.ic_dialog_info);
        ad1.setView(textEntryView);
        ad1.setPositiveButton("是", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                Log.i("111111", netAddress.getText().toString());
                String netAddressResult=netAddress.getText().toString().trim();
                Toast.makeText(getApplicationContext(),netAddressResult, Toast.LENGTH_SHORT).show();

                if(!netAddressResult.equals("")){
                    Constans.BASEURL=netAddressResult;
                }

            }
        });
        ad1.setNegativeButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        ad1.show();// 显示对话框
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_login:
                try {
                    sendRequestWithOkHttp();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_go_register:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.Linear_netAddress:
                showFialog();
                break;
        }
    }

    private void sendRequestWithOkHttp() throws IOException {
        final String username_value=username.getText().toString().trim();
        final String passwd_value=password.getText().toString().trim();
        if(reMe.isChecked()){
            reMeUtil.loginInfo(username_value,passwd_value,this);
        }
        if(reStu.isChecked()){
            RequestBody fromBody=new FormBody.Builder()
                    .add("action","user_login")
                    .add("user_name",username_value)
                    .add("user_pass",passwd_value)
                    .add("user_id","student")
                    .build();
//            HttpUtil.myHttpPostParameters("user/",fromBody,this);
            requestNet("user/",fromBody);
        }
        if(reTea.isChecked()){
            RequestBody fromBody=new FormBody.Builder()
                    .add("action","user_login")
                    .add("user_name",username_value)
                    .add("user_pass",passwd_value)
                    .add("user_id","teacher")
                    .build();
//            HttpUtil.myHttpPostParameters("user/",fromBody,this);
            requestNet("user/",fromBody);
        }
    }

    private void requestNet(String url,RequestBody fromBody) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+"user/")
                .post(fromBody)//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"登陆失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson=new Gson();
                Message message = gson.fromJson(response.body().string(), Message.class);
                if(message!= null && message.msg.equals("success")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //保存net_Address
                            SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("net_Address", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("net_Address_Result",Constans.BASEURL);
                            editor.commit();

                            //        预先请求当前用户信息
                            RequestBody fromBody=new FormBody.Builder()
                                    .add("action","find_user")
                                    .add("user_name",username.getText().toString().trim())
                                    .build();
                            HttpUtil.myHttpPostGetUser("user/",fromBody,getApplicationContext());
                        }
                    });
                    if(reStu.isChecked()){
                        runOnUiThread(new Runnable() {     //更新ui线程
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"学生登陆成功", Toast.LENGTH_SHORT).show();
                                //页面跳转
                                Intent intent1 = new Intent(LoginActivity.this, StuMainActivity.class);
                                startActivity(intent1);
                            }
                        });
                    }
                    if(reTea.isChecked()){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"教师登陆成功", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(LoginActivity.this, TeaMainActivity.class);
                                startActivity(intent1);
                            }
                        });
                    }
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"登陆失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

//    private void checkLogin(String result) {
//        Gson gson=new Gson();
//        Message message = gson.fromJson(result, Message.class);
//
//        if(message!=null&&message.msg.equals("success")){
//            //                    未找到，提示未找到
//            AlertDialog.Builder dialog=new AlertDialog.Builder(LoginActivity.this);
//            dialog.setTitle("success");
//            dialog.setMessage("成功");
//            dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                }
//            });
//            dialog.show();
//        }
//    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }
}
