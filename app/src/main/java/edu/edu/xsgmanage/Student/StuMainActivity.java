package edu.edu.xsgmanage.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import edu.edu.xsgmanage.R;
import edu.edu.xsgmanage.Utils.HttpUtil;
import edu.edu.xsgmanage.constant.Constans;
import edu.edu.xsgmanage.domain.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StuMainActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout homeLinear;

    LinearLayout locationLinear;

    LinearLayout newsLinear;

    LinearLayout userLinear;

    FragmentHome fragmentHome;
    FragmentLocation fragmentLocation;
    FragmentNews fragmentNews;
    FragmentUser fragmentUser;
    private FragmentManager mfragmentManger;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_main);
        //隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        getJsonToUser();
        initView();
        Intent intent=this.getIntent();
        String username=intent.getStringExtra("userName");

    }

    private void getJsonToUser() {
        Gson gson=new Gson();
        SharedPreferences sharedPreferences=this.getSharedPreferences("net_Response", Context.MODE_PRIVATE);
        String find_user_result=sharedPreferences.getString("find_user_result","");
        //取出数据,第一个参数是写入是的键，第二个参数是如果没有获取到数据就默认返回的值。
        if(!find_user_result.equals("")){
            List<User> list=gson.fromJson(find_user_result, new TypeToken<List<User>>(){}.getType());
            user=list.get(0);
            String my_info=gson.toJson(user,User.class);
            sharedPreferences=this.getApplicationContext().getSharedPreferences("my_Info",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("my_Info",my_info);
            editor.commit();
        }
    }

    private void initView() {
        homeLinear= (LinearLayout) findViewById(R.id.home);
        locationLinear= (LinearLayout) findViewById(R.id.location);
        newsLinear= (LinearLayout) findViewById(R.id.linear_polymer);
        userLinear= (LinearLayout) findViewById(R.id.linear_user);
        homeLinear.setOnClickListener(this);
        locationLinear.setOnClickListener(this);
        newsLinear.setOnClickListener(this);
        userLinear.setOnClickListener(this);
        mfragmentManger = getSupportFragmentManager();
        homeLinear.performClick();
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = mfragmentManger.beginTransaction();//只能是局部变量，不能为全局变量，否则不能重复commit
        //FragmentTransaction只能使用一次
        hideAllFragment(fragmentTransaction);
        switch (view.getId()){
            case R.id.home:
                setAllFalse();
                homeLinear.setSelected(true);

                if (fragmentHome==null){
                    fragmentHome=new FragmentHome("Home");
                    fragmentTransaction.add(R.id.fragment_frame,fragmentHome);
                }else{
                    fragmentTransaction.show(fragmentHome);
                }
                break;
            case R.id.location:
                setAllFalse();
                locationLinear.setSelected(true);
                if(fragmentLocation==null){
                    fragmentLocation=new FragmentLocation("List");
                    fragmentTransaction.add(R.id.fragment_frame,fragmentLocation);
                }else {
                    fragmentTransaction.show(fragmentLocation);
                }
                break;
            case R.id.linear_polymer:
//                Toast.makeText(getApplicationContext(),"请求未读消息", Toast.LENGTH_SHORT).show();

                setAllFalse();
                newsLinear.setSelected(true);
                if(fragmentNews==null){
                    fragmentNews=new FragmentNews("Polymer");
                    fragmentTransaction.add(R.id.fragment_frame,fragmentNews);
                    requestNet("message/");
                }else {
                    fragmentTransaction.show(fragmentNews);
                    requestNet("message/");
                }
                break;
            case R.id.linear_user:
                setAllFalse();
                userLinear.setSelected(true);
                if(fragmentUser==null){
                    fragmentUser=new FragmentUser("User");
                    fragmentTransaction.add(R.id.fragment_frame,fragmentUser);
                }else {
                    fragmentTransaction.show(fragmentUser);
                }
                break;
        }
        fragmentTransaction.commit();//记得必须要commit,否则没有效果
    }

    private void requestNet(String url) {
        url= Constans.BASEURL+url;
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody fromBody=RequestBody.create(Constans.FORM_CONTENT_TYPE,"action=find_message_stu&receive="+user.getPk()+"&read=0");
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
                        Toast.makeText(getApplicationContext(),"请求消息列表失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String find_message_stu_result=response.body().string();
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("net_Response",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("find_message_stu_result",find_message_stu_result);
                editor.commit();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getApplicationContext(),"数据以刷新", Toast.LENGTH_SHORT).show();
                        fragmentNews.initValue();
                    }
                });
            }
        });
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if(fragmentHome!=null){
            fragmentTransaction.hide(fragmentHome);
        }
        if(fragmentLocation!=null){
            fragmentTransaction.hide(fragmentLocation);
        }
        if(fragmentNews!=null){
            fragmentTransaction.hide(fragmentNews);
        }
        if(fragmentUser!=null){
            fragmentTransaction.hide(fragmentUser);
        }

    }

    private void setAllFalse() {
        homeLinear.setSelected(false);
        locationLinear.setSelected(false);
        newsLinear.setSelected(false);
        userLinear.setSelected(false);
    }
}
