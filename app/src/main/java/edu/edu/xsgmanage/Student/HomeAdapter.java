package edu.edu.xsgmanage.Student;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class HomeAdapter extends BaseAdapter {
    private List<Map<String,Object>> datas;
    private Context context;
    private Activity homeActivity;
    public HomeAdapter(List<Map<String,Object>> maps,Context context,Activity homeActivity){
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
                    inflate(R.layout.item_main,null);
            holder=new ViewHolder();
            holder.iv_icon=view.findViewById(R.id.iv_icon);
            holder.tv_name=view.findViewById(R.id.tv_name);

            holder.iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"你点击了",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(homeActivity.getApplication(),holder.nextActivity);
                    homeActivity.startActivity(intent1);
                }
            });
            view.setTag(holder);
        }
        else {
            holder=(ViewHolder)view.getTag();
        }

        holder.iv_icon.setImageResource((int)datas.get(i).get("imageId"));
        holder.tv_name.setText(datas.get(i).get("names").toString());
        holder.nextActivity= (Class<Activity>) datas.get(i).get("nextActivity");
        return view;
    }
    static class ViewHolder{
        ImageView iv_icon;
        TextView  tv_name;
        Class<Activity> nextActivity;
    }
}
