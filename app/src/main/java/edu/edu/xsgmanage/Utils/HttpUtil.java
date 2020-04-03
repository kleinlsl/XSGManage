package edu.edu.xsgmanage.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import edu.edu.xsgmanage.constant.Constans;
import edu.edu.xsgmanage.constant.StaticResult;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

    public static void myHttpPostParameters(String url, RequestBody fromBody, final Context context){
        url= Constans.BASEURL+url;
        /*
        url:子路由
        fromBody:Post请求体,不可出现中文
                eg：
                    RequestBody fromBody=new FormBody.Builder()
                            .add("action","user_login")
                            .add("user_name","5001170049")
                            .add("user_pass","5001170049")
                            .add("user_id","student")
                            .build();
         */
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(fromBody)//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                System.out.println(s);
                StaticResult.result=s;
                SharedPreferences sharedPreferences=context.getSharedPreferences("net_Response",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("user_login_result",s);
                editor.commit();
            }
        });
    }

    public static void myHttpPostGetUser(String url, RequestBody fromBody, final Context context){
        url= Constans.BASEURL+url;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(fromBody)//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                System.out.println(s);
                StaticResult.result=s;
                SharedPreferences sharedPreferences=context.getSharedPreferences("net_Response",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("find_user_result",s);
                editor.commit();
            }
        });
    }
}
