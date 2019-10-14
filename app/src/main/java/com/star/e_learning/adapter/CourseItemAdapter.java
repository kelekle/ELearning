package com.star.e_learning.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.star.e_learning.R;
import com.star.e_learning.bean.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wwjun.wang on 2015/8/13.
 */
public class CourseItemAdapter extends BaseAdapter {
    private List<Course> entities;
    private Context context;
    private ImageLoader mImageloader;
    private DisplayImageOptions options;

    public CourseItemAdapter(Context context) {
        this.context = context;
        this.entities = new ArrayList<>();
        mImageloader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    public void addList(List<Course> items) {
        this.entities.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public Object getItem(int position) {
        return entities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.main_news_item, parent, false);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.iv_title = (ImageView) convertView.findViewById(R.id.iv_title);
            viewHolder.course_teacher = convertView.findViewById(R.id.course_teacher);
            viewHolder.course_tag = convertView.findViewById(R.id.course_type);
            viewHolder.course_date = convertView.findViewById(R.id.course_date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        String readSeq = PreUtils.getStringFromDefault(context, "read", "");
//        if (readSeq.contains(entities.get(position).getId() + "")) {
//            viewHolder.tv_title.setTextColor(context.getResources().getColor(R.color.clicked_tv_textcolor));
//        } else {
            viewHolder.tv_title.setTextColor(context.getResources().getColor(android.R.color.black));
//        }
        ((LinearLayout) viewHolder.iv_title.getParent().getParent().getParent()).setBackgroundColor(context.getResources().getColor(R.color.white));
//        viewHolder.tv_topic.setTextColor(context.getResources().getColor(R.color.title_bar_search_text_color));
        Course entity = entities.get(position);
//        if (entity.getType() == Constant.TOPIC) {
////            ((FrameLayout) viewHolder.tv_topic.getParent()).setBackgroundColor(Color.TRANSPARENT);
//            viewHolder.tv_title.setVisibility(View.GONE);
//            viewHolder.iv_title.setVisibility(View.GONE);
////            viewHolder.tv_topic.setVisibility(View.VISIBLE);
////            viewHolder.tv_topic.setText(entity.getTitle());
//        } else {
//            ((FrameLayout) viewHolder.tv_topic.getParent()).setBackgroundResource(R.drawable.item_background_selector_dark);
//            viewHolder.tv_topic.setVisibility(View.GONE);
//            viewHolder.tv_title.setVisibility(View.VISIBLE);
//            viewHolder.iv_title.setVisibility(View.VISIBLE);
            viewHolder.tv_title.setText(entity.getTitle());
            switch (entity.getType()){
                case 1:
                    viewHolder.course_tag.setText("语文");
                    break;
                case 2:
                    viewHolder.course_tag.setText("数学");
                    break;
                case 3:
                    viewHolder.course_tag.setText("英语");
                    break;
                case 4:
                    viewHolder.course_tag.setText("物理");
                    break;
                case 5:
                    viewHolder.course_tag.setText("化学");
                    break;
            }
            viewHolder.course_teacher.setText("by "+entity.getTeacher());
            viewHolder.course_date.setText("开课时间: "+entity.getDate());
//            mImageloader.displayImage(entity.getImage(), viewHolder.iv_title, options);
        viewHolder.iv_title.setImageResource(R.mipmap.english_course);
//        }
        return convertView;
    }


    public static class ViewHolder {
        TextView course_teacher;
        TextView course_tag;
        TextView course_date;
        TextView tv_title;
        ImageView iv_title;
    }

}
