package com.star.e_learning.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.star.e_learning.R;
import com.star.e_learning.api.ApiClient;
import com.star.e_learning.api.ApiInterface;
import com.star.e_learning.util.Utils;
import com.star.e_learning.bean.Teacher;
import com.star.e_learning.ui.activity.CourseDetailActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>  {

    private Context context;
    private ArrayList<Teacher> dataSet;

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

    public TeacherAdapter(Context context) {
        this.context = context;
        this.dataSet = new ArrayList<>();
    }

    public void addList(List<Teacher> items) {
        this.dataSet.addAll(items);
        notifyDataSetChanged();
    }

    public void resetList(List<Teacher> items){
        this.dataSet.clear();
        this.dataSet.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_type, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TeacherViewHolder holder, int position) {
        Teacher object = dataSet.get(position);
        if(object != null){
            holder.teacherName.setText(object.getName());
            holder.desc.setText(object.getDescription());
            holder.email.setText(object.getEmail());
            holder.phone.setText(object.getTelephone());
            holder.progressBar.setVisibility(View.VISIBLE);
            final File photo = new File(context.getExternalFilesDir(null).getAbsolutePath()
                    + "/courses/" + object.getPhoto().replace('\\','/'));
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.getTeacherPhoto(object.getUserid());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            Utils.writeResponseBodyToDisk(photo.getAbsolutePath(), response.body());
                            ((CourseDetailActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(photo.exists()){
                                        Glide.with(context).load(Uri.fromFile(new File(photo.getAbsolutePath())))
                                                .transition(DrawableTransitionOptions.withCrossFade())
                                                .into(holder.img);
                                    }
                                    holder.progressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                            return null;
                        }
                    }.execute();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    holder.progressBar.setVisibility(View.INVISIBLE);
                    if(photo.exists()){
                        Glide.with(context).load(photo)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(holder.img);
                    }
                    holder.progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    protected static class TeacherViewHolder extends RecyclerView.ViewHolder{

        TextView phone;
        TextView email;
        TextView desc;
        TextView teacherName;
        ImageView img;
        ProgressBar progressBar;

        protected TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            phone = itemView.findViewById(R.id.phone);
            email = itemView.findViewById(R.id.email);
            desc = itemView.findViewById(R.id.desc);
            teacherName = itemView.findViewById(R.id.author);
            img = itemView.findViewById(R.id.img);
            progressBar = itemView.findViewById(R.id.progress_load_photo);
        }

    }

}
