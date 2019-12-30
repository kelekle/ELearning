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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.star.e_learning.R;
import com.star.e_learning.api.ApiClient;
import com.star.e_learning.api.ApiInterface;
import com.star.e_learning.api.AppConfig;
import com.star.e_learning.api.Utils;
import com.star.e_learning.bean.Course;
import com.star.e_learning.bean.Model;
import com.star.e_learning.bean.MultiComment;
import com.star.e_learning.ui.activity.CourseDetailActivity;
import com.star.e_learning.ui.activity.HomeActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wwjun.wang on 2015/8/13.
 */
public class CourseItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Model> dataSet;

    private OnItemClickListener onItemClickListener;

    /**
     * 设置RecyclerView某个的监听
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        onItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public CourseItemAdapter(Context context) {
        this.context = context;
        this.dataSet = new ArrayList<>();
    }

    public void addList(List<Model> items) {
        this.dataSet.addAll(items);
        notifyDataSetChanged();
    }

    public void resetList(List<Model> items){
        this.dataSet.clear();
        this.dataSet.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Model.COURSE_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_image_type, parent, false);
                return new CourseImageHolder(view);
            case Model.COURSE_TEXT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_text_type, parent, false);
                return new CourseTextHolder(view);
            case Model.ENROLLMENT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_enrollment_type, parent, false);
                return new EnrollmentHolder(view);
            case Model.SUMMARY_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_summary_type, parent, false);
                return new SummaryHolder(view);
            case Model.LECTURE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_lecture_type, parent, false);
                return new LectureHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        Model object = dataSet.get(position);
        if(object != null){
            switch (object.getType()){
                case Model.COURSE_IMAGE_TYPE:
                    ((CourseImageHolder) holder).courseName.setText(((Course)object.getData()).getName());
                    ((CourseImageHolder) holder).certification.setText(((Course)object.getData()).getCertification());
                    ((CourseImageHolder) holder).openDate.setText(Utils.DateFormat(((Course)object.getData()).getOpenDate()));
                    ((CourseImageHolder) holder).description.setText(((Course)object.getData()).getDescription());
                    ((CourseImageHolder) holder).level.setText(((Course)object.getData()).getLevel());
                    ((CourseImageHolder) holder).status.setText(((Course)object.getData()).getStatus());
//                    System.out.println(Utils.BASE_URL + "courses/" + ((Course)object.getData()).getId() + "/photo");
                    final File photo = new File(context.getExternalFilesDir(null).getAbsolutePath()
                            + "/courses/" + ((Course)object.getData()).getAvatar().replace('\\','/'));
                    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<ResponseBody> call = apiInterface.getCoursePhoto(((Course)object.getData()).getId());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                            new AsyncTask<Void, Void, Void>(){
                                @Override
                                public Void doInBackground(Void... voids) {
                                    Utils.writeResponseBodyToDisk(photo.getAbsolutePath(), response.body());
                                    ((HomeActivity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (photo.exists()) {
                                                Glide.with(context).load(Uri.fromFile(new File(photo.getAbsolutePath()))).into(((CourseImageHolder) holder).img);
                                            }
                                            ((CourseImageHolder) holder).progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                    return null;
                                }
                            }.execute();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            ((CourseImageHolder) holder).progressBar.setVisibility(View.INVISIBLE);
                            if (photo.exists()) {
                                Glide.with(context).load(Uri.fromFile(new File(photo.getAbsolutePath()))).into(((CourseImageHolder) holder).img);
                            }
                        }
                    });
                    ((CourseImageHolder) holder).card.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            if(onItemClickListener != null){
                                onItemClickListener.onItemClick(position);
                            }
                        }
                    });
                    break;
                case Model.COURSE_TEXT_TYPE:
                    ((CourseTextHolder) holder).courseName.setText(((Course)object.getData()).getName());
                    ((CourseTextHolder) holder).certification.setText(((Course)object.getData()).getCertification());
                    ((CourseTextHolder) holder).openDate.setText(Utils.DateFormat(((Course)object.getData()).getOpenDate()));
                    ((CourseTextHolder) holder).description.setText(((Course)object.getData()).getDescription());
                    ((CourseTextHolder) holder).level.setText(((Course)object.getData()).getLevel());
                    ((CourseTextHolder) holder).status.setText(((Course)object.getData()).getStatus());
                    ((CourseTextHolder) holder).card.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            if(onItemClickListener != null){
                                onItemClickListener.onItemClick(position);
                            }
                        }
                    });
                    final File photo1 = new File(context.getExternalFilesDir(null).getAbsolutePath()
                            + "/courses/" + ((Course)object.getData()).getAvatar().replace('\\','/'));
                    ApiInterface apiInterface1 = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<ResponseBody> call1 = apiInterface1.getCoursePhoto(((Course)object.getData()).getId());
                    call1.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                            new AsyncTask<Void, Void, Void>(){
                                @Override
                                public Void doInBackground(Void... voids) {
                                    Utils.writeResponseBodyToDisk(photo1.getAbsolutePath(), response.body());
                                    return null;
                                }
                            }.execute();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                        }
                    });
                    break;
                case Model.LECTURE_TYPE:
                    ((LectureHolder) holder).courseName.setText(((Course)object.getData()).getName());
                    ((LectureHolder) holder).certification.setText(((Course)object.getData()).getCertification());
                    ((LectureHolder) holder).openDate.setText(Utils.DateFormat(((Course)object.getData()).getOpenDate()));
                    ((LectureHolder) holder).description.setText(((Course)object.getData()).getDescription());
                    ((LectureHolder) holder).level.setText(((Course)object.getData()).getLevel());
                    ((LectureHolder) holder).status.setText(((Course)object.getData()).getStatus());
                    System.out.println(AppConfig.BASE_RESOURCE_URL + ((Course)object.getData()).getAvatar().replace('\\', '/'));
                    final File photo3 = new File(context.getExternalFilesDir(null).getAbsolutePath()
                            + "/courses/" + ((Course)object.getData()).getAvatar().replace('\\','/'));
                    ApiInterface apiInterface3 = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<ResponseBody> call3 = apiInterface3.getCoursePhoto(((Course)object.getData()).getId());
                    call3.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                            new AsyncTask<Void, Void, Void>(){
                                @Override
                                public Void doInBackground(Void... voids) {
                                    Utils.writeResponseBodyToDisk(photo3.getAbsolutePath(), response.body());
                                    ((HomeActivity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (photo3.exists()) {
                                                Glide.with(context).load(Uri.fromFile(new File(photo3.getAbsolutePath()))).into(((LectureHolder) holder).img);
                                            }
                                            ((LectureHolder) holder).progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                    return null;
                                }
                            }.execute();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            ((LectureHolder) holder).progressBar.setVisibility(View.INVISIBLE);
                            if (photo3.exists()) {
                                Glide.with(context).load(Uri.fromFile(new File(photo3.getAbsolutePath()))).into(((LectureHolder) holder).img);
                            }
                        }
                    });
                    ((LectureHolder) holder).card.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            if(onItemClickListener != null){
                                onItemClickListener.onItemClick(position);
                            }
                        }
                    });
                    break;
                case Model.ENROLLMENT_TYPE:
                    ((EnrollmentHolder) holder).peopele.setText((String) object.getData());
                    break;
                case Model.SUMMARY_TYPE:
                    ((SummaryHolder) holder).max.setText(String.valueOf(((MultiComment) object.getData()).getMax()));
                    ((SummaryHolder) holder).medium.setText(String.valueOf(((MultiComment) object.getData()).getMedium()));
                    ((SummaryHolder) holder).min.setText(String.valueOf(((MultiComment) object.getData()).getMin()));
                    ((SummaryHolder) holder).maxDesc.setText(((MultiComment) object.getData()).getMaxDesc());
                    ((SummaryHolder) holder).mediumDesc.setText(((MultiComment) object.getData()).getMediumDesc());
                    ((SummaryHolder) holder).minDesc.setText(((MultiComment) object.getData()).getMinDesc());
                    break;
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getCourseID(int position){
        if(getItemViewType(position) == Model.COURSE_IMAGE_TYPE ||
                getItemViewType(position) == Model.COURSE_TEXT_TYPE ||
                getItemViewType(position) == Model.LECTURE_TYPE)
            return ((Course) dataSet.get(position).getData()).getId();
        return "";
    }

    public String getTitle(int position){
        if(getItemViewType(position) == Model.COURSE_IMAGE_TYPE ||
                getItemViewType(position) == Model.COURSE_TEXT_TYPE ||
                getItemViewType(position) == Model.LECTURE_TYPE)
            return ((Course) dataSet.get(position).getData()).getName();
        return "";
    }

    public String getCourseDescription(int position){
        if(getItemViewType(position) == Model.COURSE_IMAGE_TYPE ||
                getItemViewType(position) == Model.COURSE_TEXT_TYPE ||
                getItemViewType(position) == Model.LECTURE_TYPE)
            return ((Course) dataSet.get(position).getData()).getDescription();
        return "";
    }

    public String getCourseAvatar(int position){
        if(getItemViewType(position) == Model.COURSE_IMAGE_TYPE ||
                getItemViewType(position) == Model.COURSE_TEXT_TYPE ||
                getItemViewType(position) == Model.LECTURE_TYPE)
            return ((Course) dataSet.get(position).getData()).getAvatar();
        return "";
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (dataSet.get(position).getType()) {
            case Model.COURSE_IMAGE_TYPE:
                return Model.COURSE_IMAGE_TYPE;
            case Model.COURSE_TEXT_TYPE:
                return Model.COURSE_TEXT_TYPE;
            case Model.SUMMARY_TYPE:
                return Model.SUMMARY_TYPE;
            case Model.ENROLLMENT_TYPE:
                return Model.ENROLLMENT_TYPE;
            case Model.LECTURE_TYPE:
                return Model.LECTURE_TYPE;
            default:
                return -1;
        }
    }

    protected static class CourseImageHolder extends RecyclerView.ViewHolder{

        TextView courseName;
        TextView description;
        TextView certification;
        TextView openDate;
        TextView level;
        TextView status;
        ImageView img;
        ProgressBar progressBar;
        private CardView card;

        protected CourseImageHolder(@NonNull View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.desc);
            certification = (TextView) itemView.findViewById(R.id.certification);
            openDate = (TextView) itemView.findViewById(R.id.openDate);
            level = (TextView) itemView.findViewById(R.id.level);
            status = (TextView) itemView.findViewById(R.id.status);
            img = (ImageView) itemView.findViewById(R.id.img);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_load_photo);
            card = (CardView) itemView.findViewById(R.id.card);
        }

    }

    protected static class CourseTextHolder extends RecyclerView.ViewHolder{

        TextView courseName;
        TextView description;
        TextView certification;
        TextView openDate;
        TextView level;
        TextView status;
        private CardView card;

        protected CourseTextHolder(@NonNull View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.desc);
            certification = (TextView) itemView.findViewById(R.id.certification);
            openDate = (TextView) itemView.findViewById(R.id.openDate);
            level = (TextView) itemView.findViewById(R.id.level);
            status = (TextView) itemView.findViewById(R.id.status);
            card = (CardView) itemView.findViewById(R.id.card);
        }

    }

    protected static class LectureHolder extends RecyclerView.ViewHolder{

        TextView courseName;
        TextView description;
        TextView certification;
        TextView openDate;
        TextView level;
        TextView status;
        ImageView img;
        ProgressBar progressBar;
        private CardView card;

        protected LectureHolder(@NonNull View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.desc);
            certification = (TextView) itemView.findViewById(R.id.certification);
            openDate = (TextView) itemView.findViewById(R.id.openDate);
            level = (TextView) itemView.findViewById(R.id.level);
            status = (TextView) itemView.findViewById(R.id.status);
            img = (ImageView) itemView.findViewById(R.id.img);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_load_photo);
            card = (CardView) itemView.findViewById(R.id.card);
        }
    }

    protected static class EnrollmentHolder extends RecyclerView.ViewHolder{

        TextView peopele;

        protected EnrollmentHolder(@NonNull View itemView) {
            super(itemView);
            peopele = (TextView)itemView.findViewById(R.id.people);
        }
    }

    protected static class SummaryHolder extends RecyclerView.ViewHolder{

        TextView max;
        TextView min;
        TextView medium;
        TextView maxDesc;
        TextView minDesc;
        TextView mediumDesc;

        protected SummaryHolder(@NonNull View itemView) {
            super(itemView);
            max = (TextView)itemView.findViewById(R.id.max);
            min = (TextView) itemView.findViewById(R.id.min);
            medium = (TextView) itemView.findViewById(R.id.medium);
            maxDesc = (TextView)itemView.findViewById(R.id.max_desc);
            minDesc = (TextView) itemView.findViewById(R.id.min_desc);
            mediumDesc = (TextView) itemView.findViewById(R.id.medium_desc);
        }
    }

}
