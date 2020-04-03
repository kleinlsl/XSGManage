package edu.edu.xsgmanage.Student;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import edu.edu.xsgmanage.MyClassActivity;
import edu.edu.xsgmanage.MyInfoActivity;
import edu.edu.xsgmanage.MyUpdataPassActivity;
import edu.edu.xsgmanage.R;
import edu.edu.xsgmanage.domain.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUser extends Fragment implements View.OnClickListener{
    private String fragmentText;
    private LinearLayout myMessage,myClass,myUpdataPass;
    private ImageView myMessageImage;
    private TextView myUsername;
    private View view;
    private TextView fragmentTextView;
    private User user;
    public FragmentUser(String fragmentText) {
        this.fragmentText=fragmentText;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_stu_user,container,false);
//        StuMainActivity stuMainActivity=(StuMainActivity)getActivity();

        if(view!=null){
            getJsonToUser();
            init();
        }
        return view;
    }
    private void getJsonToUser() {
        Gson gson=new Gson();
        SharedPreferences sharedPreferences=view.getContext().getSharedPreferences("my_Info", Context.MODE_PRIVATE);
        String my_Info=sharedPreferences.getString("my_Info","");
        //取出数据,第一个参数是写入是的键，第二个参数是如果没有获取到数据就默认返回的值。
        if(!my_Info.equals("")){
            user=gson.fromJson(my_Info, User.class);
        }
    }
    private void init() {
//        初始化组件
        myMessage=(LinearLayout)view.findViewById(R.id.my_message);
        myClass=(LinearLayout) view.findViewById(R.id.my_class);
        myUpdataPass=(LinearLayout) view.findViewById(R.id.my_updata_pass);
        myMessageImage=(ImageView)view.findViewById(R.id.message_image);
        myUsername=(TextView)view.findViewById(R.id.user_name);

//        设置监听器
        myMessage.setOnClickListener(this);
        myClass.setOnClickListener(this);
        myUpdataPass.setOnClickListener(this);
        myMessageImage.setOnClickListener(this);
        myUsername.setOnClickListener(this);

//        添加用户名
        myUsername.setText(user.getPk());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_message :
                enterMyMessage();
                break;
            case R.id.user_name:
                enterMyMessage();
                break;
            case R.id.message_image:
                enterMyMessage();
                break;
            case R.id.my_updata_pass:
                enterUpdataPass();
                break;
            case R.id.my_class:
                enterMyClass();
                break;
        }
    }

    private void enterMyMessage() {
        Toast.makeText(view.getContext(),"进入个人信息", Toast.LENGTH_SHORT).show();
        //页面跳转
        Intent intent1 = new Intent(view.getContext(), MyInfoActivity.class);
        startActivity(intent1);
    }
    private void enterUpdataPass() {
        Toast.makeText(view.getContext(),"进入修改密码", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(view.getContext(), MyUpdataPassActivity.class);
        startActivity(intent1);
    }
    private void enterMyClass() {
        Toast.makeText(view.getContext(),"进入我的班级", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(view.getContext(), MyClassActivity.class);
        startActivity(intent1);
    }
}
