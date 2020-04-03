package edu.edu.xsgmanage.Teacher;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.io.IOException;

import edu.edu.xsgmanage.MyInfoActivity;
import edu.edu.xsgmanage.R;
import edu.edu.xsgmanage.constant.Constans;
import edu.edu.xsgmanage.domain.Message;
import edu.edu.xsgmanage.domain.News;
import edu.edu.xsgmanage.domain.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNews extends Fragment implements View.OnClickListener{

    private EditText receiveId;
    private EditText messageContent;
    private Button buttonSender;
    private View view;
    private User user;
    private String fragmentText;
    private News news;
    private Gson gson;
    private TextView fragmentTextView;
    public FragmentNews(String fragmentText) {
        this.fragmentText=fragmentText;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_tea_news,container,false);
        gson=new Gson();
        getJsonToUser();
        init();
        return view;
    }

    private void init() {
        receiveId=(EditText) view.findViewById(R.id.Ed_receive_user_id);
        messageContent=(EditText)view.findViewById(R.id.add_content);
        buttonSender=(Button) view.findViewById(R.id.button_sender);

//        设置监听器
        buttonSender.setOnClickListener(this);
    }

    private void getJsonToUser() {

//        解析user数据
        //取出数据,第一个参数是写入是的键，第二个参数是如果没有获取到数据就默认返回的值。
        SharedPreferences sharedPreferences=view.getContext().getSharedPreferences("my_Info", Context.MODE_PRIVATE);
        String my_Info=sharedPreferences.getString("my_Info","");
        if(!my_Info.equals("")){
            user=gson.fromJson(my_Info, User.class);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_sender:
                checkInfo();
                break;
        }
    }

    private void checkInfo() {
        if(receiveId.getText().toString().trim().equals("")){
            Toast.makeText(getActivity().getApplicationContext(),"请填写接收者",Toast.LENGTH_SHORT).show();
        }
        else {
            AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
            dialog.setTitle("确认发送");
            dialog.setMessage("确认发送消息："+messageContent.getText().toString()+";给："+receiveId.getText().toString());
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    enterSender("message/");
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

    private void enterSender(String url) {
        news=new News();
        News.Fields fields=new News.Fields();
        fields.setMessage(messageContent.getText().toString().trim());
        fields.setSender(user.getPk());
        fields.setReceive(receiveId.getText().toString());
        fields.setRead("0");
        news.setFields(fields);
        news.setModel("app.message");
        requestNet("message/");
    }

    private void requestNet(String url) {

        String requestData=gson.toJson(news);

        Toast.makeText(getActivity().getApplicationContext(),"进入网络请求", Toast.LENGTH_SHORT).show();
        url= Constans.BASEURL+url;
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody fromBody=RequestBody.create(Constans.FORM_CONTENT_TYPE,"" +
                "action=add_message" +
                "&message_data="+requestData);
        final Request request = new Request.Builder()
                .url(url)
                .post(fromBody)//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),"请求失败", Toast.LENGTH_SHORT).show();
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
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(),"发送成功", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(),"发送失败，请输入正确的接收者", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
