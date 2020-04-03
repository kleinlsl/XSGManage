package edu.edu.xsgmanage.Teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import edu.edu.xsgmanage.R;
import edu.edu.xsgmanage.domain.User;

public class TeaMainActivity extends AppCompatActivity implements View.OnClickListener{

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
        setContentView(R.layout.activity_tea_main);
        //隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        initView();
        Intent intent=this.getIntent();
        String username=intent.getStringExtra("userName");
        getJsonToUser();
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
                setAllFalse();
                newsLinear.setSelected(true);
                if(fragmentNews==null){
                    fragmentNews=new FragmentNews("Polymer");
                    fragmentTransaction.add(R.id.fragment_frame,fragmentNews);
                }else {
                    fragmentTransaction.show(fragmentNews);
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
