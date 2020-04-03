package edu.edu.xsgmanage.Student;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import edu.edu.xsgmanage.R;
import edu.edu.xsgmanage.constant.Constans;
import edu.edu.xsgmanage.domain.Grade;
import edu.edu.xsgmanage.domain.Message;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GradeAdapter extends BaseAdapter {

    private List<Map<String,Object>> datas;
    private Context context;
    private Activity homeActivity;
    public GradeAdapter(List<Map<String,Object>> maps,Context context,Activity homeActivity){
        this.datas=maps;
        this.context=context;
        this.homeActivity=homeActivity;
    }
    /**
     * 返回item的个数
     * @return
     */
    @Override
    public int getCount() {
        return datas.size();
    }
    /**
     * 返回每一个item对象
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }
    /**
     * 返回每一个item的id
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view==null){
            view= LayoutInflater.from(context).
                    inflate(R.layout.my_grade_list,null);
            holder=new ViewHolder();
            holder.Bu_grade=view.findViewById(R.id.Bu_grade);
            holder.tv_lst_course=view.findViewById(R.id.tv_lst_course);
            holder.tv_lst_value=view.findViewById(R.id.tv_lst_value);
            holder.tv_lst_xue=view.findViewById(R.id.tv_lst_xue);
            holder.Bu_grade.setOnClickListener(new View.OnClickListener() {
                private void requestNet(String url, final String gradeComplainResult){
                    url= Constans.BASEURL+url;
                    OkHttpClient okHttpClient = new OkHttpClient();
                    RequestBody fromBody=RequestBody.create(Constans.FORM_CONTENT_TYPE,"action=updata_grade_complain&pk="+holder.grade.getPk()+"&grade_complain="+gradeComplainResult);
                    final Request request = new Request.Builder()
                            .url(url)
                            .post(fromBody)//默认就是GET请求，可以不写
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            homeActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context,"请求服务器失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String updata_grade_complain=response.body().string();
                            Gson gson=new Gson();
                            Message message=gson.fromJson(updata_grade_complain,Message.class);
                            if(message.getMsg().equals("success")){
                                homeActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context,"请求成功", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                            else {
                                homeActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context,"请求服务器失败", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }
                    });
                }

                private void showFialog() {
                    LayoutInflater factory = LayoutInflater.from(context);
                    final View textEntryView = factory.inflate(R.layout.grade_complain_layout, null);
                    final EditText grade_complain = (EditText) textEntryView.findViewById(R.id.grade_complain);
                    if(holder.grade.getFields().getGrade_complain()!=null){
                        grade_complain.setText(holder.grade.getFields().getGrade_complain().toString());
                    }
                    AlertDialog.Builder ad1;
                    ad1 = new AlertDialog.Builder(homeActivity);
                    ad1.setTitle("提出申诉:");
                    ad1.setIcon(android.R.drawable.ic_dialog_info);
                    ad1.setView(textEntryView);
                    ad1.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int i) {
                            final String gradeComplainResult=grade_complain.getText().toString().trim();
                            Log.i("111111", gradeComplainResult);
                            if(!gradeComplainResult.equals("")){
                                homeActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        requestNet("grade/",gradeComplainResult);
                                    }
                                });

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
                    Toast.makeText(context,"你点击了",Toast.LENGTH_SHORT).show();
                    showFialog();
                }
            });
            view.setTag(holder);
        }
        else {
            holder=(ViewHolder)view.getTag();
        }
        holder.grade=(Grade) datas.get(i).get("grade");
        holder.tv_lst_course.setText(holder.grade.getCourse_info().getFields().getCourse_name());
        holder.tv_lst_value.setText(holder.grade.getFields().getGrade_value());
        holder.tv_lst_xue.setText(holder.grade.getCourse_info().getFields().getSemester());
        holder.nextActivity= (Class<Activity>) datas.get(i).get("nextActivity");
        return view;
    }
    static class ViewHolder{
        LinearLayout Bu_grade;
        TextView tv_lst_course;
        TextView tv_lst_value;
        TextView tv_lst_xue;
        Grade grade;
        Class<Activity> nextActivity;
    }
}
