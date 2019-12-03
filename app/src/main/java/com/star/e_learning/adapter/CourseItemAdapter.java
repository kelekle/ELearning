package com.star.e_learning.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.star.e_learning.R;
import com.star.e_learning.bean.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wwjun.wang on 2015/8/13.
 */
public class CourseItemAdapter extends RecyclerView.Adapter<CourseItemAdapter.ViewHolder> {

    private List<Course> entities;
    private Context context;
    private ImageLoader mImageloader;
    private DisplayImageOptions options;

    private OnItemClickListener onItemClickListener;

    /**
     * 设置RecyclerView某个的监听
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public CourseItemAdapter(Context context) {
        Log.d("mytag", "constructor");
        this.context = context;
        this.entities = new ArrayList<>();
        this.mImageloader = ImageLoader.getInstance();
        this.options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    public void addList(List<Course> items) {
        this.entities.addAll(items);
        notifyDataSetChanged();
        Log.d("mytag", "add");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("mytag", "create");
        View convertView = LayoutInflater.from(context).inflate(R.layout.main_news_item, parent, false);
        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d("mytag", "bind");
        Course entity = entities.get(position);
        holder.tv_title.setText(entity.getTitle());
        switch (entity.getType()){
            case 1:
                holder.course_tag.setText("语文");
                break;
            case 2:
                holder.course_tag.setText("数学");
                break;
            case 3:
                holder.course_tag.setText("英语");
                break;
            case 4:
                holder.course_tag.setText("物理");
                break;
            case 5:
                holder.course_tag.setText("化学");
                break;
        }
        holder.course_teacher.setText("by "+entity.getTeacher());
        holder.course_date.setText("开课时间: "+entity.getDate());
//      mImageloader.displayImage(entity.getImage(), viewHolder.iv_title, options);
        holder.iv_title.setImageResource(R.mipmap.english_course);
        if (onItemClickListener != null) {
            holder.iv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在TextView的地方进行监听点击事件，并且实现接口
                    onItemClickListener.onItemClick(position);
                }
            });
        }
        Log.d("mytag", entity.getDate() +" "+entity.getDescription());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        Log.d("mytag", "count");
        return entities.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{

        TextView course_teacher;
        TextView course_tag;
        TextView course_date;
        TextView tv_title;
        ImageView iv_title;

        protected ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = (TextView)itemView.findViewById(R.id.tv_title);
            iv_title = (ImageView) itemView.findViewById(R.id.iv_title);
            course_teacher = itemView.findViewById(R.id.course_teacher);
            course_tag = itemView.findViewById(R.id.course_type);
            course_date = itemView.findViewById(R.id.course_date);
        }
    }

}
