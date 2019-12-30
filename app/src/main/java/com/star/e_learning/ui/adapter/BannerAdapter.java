package com.star.e_learning.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.star.e_learning.R;
import com.star.e_learning.api.ApiClient;
import com.star.e_learning.api.ApiInterface;
import com.star.e_learning.api.Utils;
import com.star.e_learning.bean.Course;
import com.star.e_learning.bean.Model;
import com.star.e_learning.bean.User;
import com.star.e_learning.ui.activity.HomeActivity;
import com.star.e_learning.ui.fragment.CourseFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public BannerAdapter(Context context){
        this.context = context;
        topCoursesEntities = new ArrayList<>();
    }

    public void resetList(List<Course> items){
        topCoursesEntities.clear();
        topCoursesEntities.addAll(items);
        notifyDataSetChanged();
    }

    public String getCourseID(int position){
        if(getItemViewType(position) == Model.COURSE_IMAGE_TYPE ||
                getItemViewType(position) == Model.COURSE_TEXT_TYPE ||
                getItemViewType(position) == Model.LECTURE_TYPE)
            return topCoursesEntities.get(position).getId();
        return "";
    }

    public String getTitle(int position){
        if(getItemViewType(position) == Model.COURSE_IMAGE_TYPE ||
                getItemViewType(position) == Model.COURSE_TEXT_TYPE ||
                getItemViewType(position) == Model.LECTURE_TYPE)
            return topCoursesEntities.get(position).getName();
        return "";
    }

    public String getCourseDescription(int position){
        if(getItemViewType(position) == Model.COURSE_IMAGE_TYPE ||
                getItemViewType(position) == Model.COURSE_TEXT_TYPE ||
                getItemViewType(position) == Model.LECTURE_TYPE)
            return topCoursesEntities.get(position).getDescription();
        return "";
    }

    public String getCourseAvatar(int position){
        if(getItemViewType(position) == Model.COURSE_IMAGE_TYPE ||
                getItemViewType(position) == Model.COURSE_TEXT_TYPE ||
                getItemViewType(position) == Model.LECTURE_TYPE)
            return topCoursesEntities.get(position).getAvatar();
        return "";
    }

    public int getSize(){
        return topCoursesEntities.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kanner_content_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if(getSize() <= 0)
            return;
        final int pos = position % getSize();
        Course course = topCoursesEntities.get(pos);
        if(course != null){
            holder.tv_title.setText(topCoursesEntities.get(pos).getName());
            final File photo = new File(context.getExternalFilesDir(null).getAbsolutePath()
                    + "/hotcourses/" + topCoursesEntities.get(pos).getAvatar().replace('\\','/'));
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.getCoursePhoto(topCoursesEntities.get(pos).getId());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                    if(response.isSuccessful() && response.body() != null){
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                Utils.writeResponseBodyToDisk(photo.getAbsolutePath(), response.body());
                                ((HomeActivity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(photo.exists()){
                                            Glide.with(context).load(Uri.fromFile(new File(photo.getAbsolutePath()))).into(holder.iv_title);
                                        }
                                    }
                                });
                                return null;
                            }
                        }.execute();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if(photo.exists()){
                        Glide.with(context).load(photo).into(holder.iv_title);
                    }
                }
            });
            if (onItemClickListener != null) {
                holder.iv_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //在TextView的地方进行监听点击事件，并且实现接口
                        onItemClickListener.onItemClick(pos % topCoursesEntities.size());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_title;
        private TextView tv_title;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            iv_title = itemView.findViewById(R.id.iv_title);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }

}
