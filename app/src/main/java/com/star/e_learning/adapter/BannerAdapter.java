package com.star.e_learning.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.star.e_learning.R;
import com.star.e_learning.bean.Course;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

    private List<Course> topCoursesEntities;
    private Context context;
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

    public BannerAdapter(Context context, List<Course> topCourses){
        this.context = context;
        this.topCoursesEntities = topCourses;
    }

    public void addList(List<Course> items) {
        this.topCoursesEntities.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.kanner_content_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.iv_title.setImageResource(R.mipmap.math_course);
        if (onItemClickListener != null) {
            holder.iv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在TextView的地方进行监听点击事件，并且实现接口
                    onItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return topCoursesEntities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_title;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            iv_title = itemView.findViewById(R.id.iv_title);
        }
    }

}
