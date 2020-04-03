package edu.edu.xsgmanage.Student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import edu.edu.xsgmanage.R;
import edu.edu.xsgmanage.constant.Constans;
import edu.edu.xsgmanage.domain.News;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewsAdapter extends BaseAdapter {
    private List<Map<String,Object>> datas;
    private Context context;
    public NewsAdapter(List<Map<String,Object>> maps,Context context){
        this.datas=maps;
        this.context=context;
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

    /**
     * 文艺式：这种写法非常好，即利用了listView的缓存机制，又避免了重复的findViewById
     *
     * 1、创建内部类
     * 2、判断convertView是否为空
     * 3、通过setTag方法将viewHolder与convertView建立关系绑定
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view==null){

            view= LayoutInflater.from(context).
                    inflate(R.layout.my_message_list,null);
            holder=new ViewHolder();

            holder.Bu_message=(LinearLayout)view.findViewById(R.id.Bu_message);
            holder.message=(TextView)view.findViewById(R.id.tv_lst_message);
            holder.sender=(TextView)view.findViewById(R.id.tv_lst_sender);
            holder.Bu_message.setOnClickListener(new View.OnClickListener() {
                public void requestNet(String url){
                    url= Constans.BASEURL+url;
                    OkHttpClient okHttpClient = new OkHttpClient();
                    RequestBody fromBody=RequestBody.create(Constans.FORM_CONTENT_TYPE,
                            "action=updata_message_state" +
                                    "&pk="+Integer.toString(holder.news.getPk())+"" +
                                    "&read="+holder.news.getFields().getRead());
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
                            String result=response.body().string();
                        }
                    });
                }
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context,"你点击了",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder dialog=new AlertDialog.Builder(context);
                    dialog.setTitle("消息详情");
                    dialog.setMessage(holder.news.getFields().getMessage().toString()+"\n时间："+holder.news.getFields().getAdd_time());
                    dialog.setPositiveButton("确认已读", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context,"即将请求改变消息状态",Toast.LENGTH_SHORT).show();
                            requestNet("message/");
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context,"已取消",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();
                }
            });
            view.setTag(holder);
        }
        else {
            holder=(ViewHolder)view.getTag();
        }

        holder.sender.setText(datas.get(i).get("sender_name").toString());
        holder.message.setText(datas.get(i).get("message").toString());
        holder.news=(News) datas.get(i).get("news");
        return view;
    }
    static class ViewHolder{
        LinearLayout Bu_message;
        TextView sender;
        TextView message;
        News news;
    }
}
