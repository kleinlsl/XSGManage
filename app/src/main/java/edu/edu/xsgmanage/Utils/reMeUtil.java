package edu.edu.xsgmanage.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class reMeUtil {

    public static void loginInfo(String userName,String userPass,Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("login_Info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("userName",userName);
        editor.putString("userPass",userPass);
        editor.commit();
    }
}
