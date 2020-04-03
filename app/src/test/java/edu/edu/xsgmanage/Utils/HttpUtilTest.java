package edu.edu.xsgmanage.Utils;

import android.content.Context;

import org.junit.Test;

import okhttp3.FormBody;
import okhttp3.RequestBody;

import static org.junit.Assert.*;

public class HttpUtilTest {

    @Test
    public void myHttpPostParameters() {
        RequestBody fromBody=new FormBody.Builder()
                .add("action","user_login")
                .add("user_name","5001170049")
                .add("user_pass","5001170049")
                .add("user_id","student")
                .build();
//        String result=HttpUtil.myHttpPostParameters("user/",fromBody);
//        System.out.println(result);
    }
}