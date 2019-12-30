package com.star.e_learning.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.star.e_learning.R;
import com.star.e_learning.api.ApiClient;
import com.star.e_learning.api.ApiInterface;
import com.star.e_learning.api.Utils;
import com.star.e_learning.bean.Material;
import com.star.e_learning.bean.Model;
import com.star.e_learning.bean.MultiComment;
import com.star.e_learning.ui.activity.CourseDetailActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaterialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Model> dataSet;

    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

    MediaPlayer mPlayer;
    private boolean fabStateVolume = false;

    public MaterialAdapter(Context context) {
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
            case Model.VIDEO_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_video_type, parent, false);
                return new VideoViewHolder(view);
            case Model.IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_image_type, parent, false);
                return new ImageViewHolder(view);
            case Model.AUDIO_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_audio_type, parent, false);
                return new AudioViewHolder(view);
            case Model.PDF_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_summary_type, parent, false);
                return new PDFViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        Model object = dataSet.get(position);
        if(object != null){
            switch (object.getType()){
                case Model.VIDEO_TYPE:
                    ((VideoViewHolder) holder).name.setText(((Material)object.getData()).getMaterialUrl().substring(4));
                    ((VideoViewHolder) holder).openDate.setText(Utils.DateFormat(((Material)object.getData()).getCreateDate()));
                    ((VideoViewHolder) holder).description.setText(((Material)object.getData()).getDescription());
                    ((VideoViewHolder) holder).type.setText(((Material)object.getData()).getMaterialType());
                    ((VideoViewHolder) holder).progressBar.setVisibility(View.INVISIBLE);
                    if(((Material)object.getData()).getStatus() == "1"){
                        ((VideoViewHolder) holder).status.setText("Open");
                    }else {
                        ((VideoViewHolder) holder).status.setText("Close");
                    }
                    final File videoFile = new File(context.getExternalFilesDir(null) + "/material/" +
                            ((Material)object.getData()).getMaterialUrl().replace("\\","/"));
                    Call<ResponseBody> call = apiInterface.getMaterialsMedia(((Material)object.getData()).getId());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                            if (response.isSuccessful() && response.body() != null){
                                new AsyncTask<Void, Void, Void>(){
                                    @Override
                                    protected Void doInBackground(Void... voids){
                                        Utils.writeResponseBodyToDisk(videoFile.getAbsolutePath(), response.body());
                                        ((CourseDetailActivity) context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((VideoViewHolder) holder).progressBar.setVisibility(View.INVISIBLE);
                                                ((VideoViewHolder) holder).videoView.setVideoURI(Uri.fromFile(videoFile));
                                            }
                                        });
                                        return null;
                                    }
                                }.execute();
                            } else {
                                ((VideoViewHolder) holder).progressBar.setVisibility(View.INVISIBLE);
                                if(videoFile.exists()){
                                    ((VideoViewHolder) holder).videoView.setVideoURI(Uri.fromFile(videoFile));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            ((VideoViewHolder) holder).progressBar.setVisibility(View.INVISIBLE);
                            if(videoFile.exists()){
                                ((VideoViewHolder) holder).videoView.setVideoURI(Uri.fromFile(videoFile));
                            }
                        }
                    });
                    break;
                case Model.IMAGE_TYPE:
                    ((ImageViewHolder) holder).courseName.setText(((Material)object.getData()).getMaterialUrl().substring(4));
                    ((ImageViewHolder) holder).openDate.setText(Utils.DateFormat(((Material)object.getData()).getCreateDate()));
                    ((ImageViewHolder) holder).description.setText(((Material)object.getData()).getDescription());
                    ((ImageViewHolder) holder).type.setText(((Material)object.getData()).getMaterialType());
                    if(((Material)object.getData()).getStatus() == "1"){
                        ((ImageViewHolder) holder).status.setText("Open");
                    }else {
                        ((ImageViewHolder) holder).status.setText("Close");
                    }
                    final File path = new File(context.getExternalFilesDir(null) + "/material/"
                            + ((Material)object.getData()).getMaterialUrl().replace("\\","/"));
                    Call<ResponseBody> photo = apiInterface.getCoursePhoto(((Material)object.getData()).getId());
                    photo.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                            if (response.isSuccessful() && response.body() != null){
                                new AsyncTask<Void, Void, Void>(){
                                    @Override
                                    protected Void doInBackground(Void... voids){
                                        Utils.writeResponseBodyToDisk(path.getAbsolutePath(), response.body());
                                        ((CourseDetailActivity) context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((ImageViewHolder) holder).progressBar.setVisibility(View.INVISIBLE);
                                                if(path.exists()){
                                                    Glide.with(context).load(Uri.fromFile(new File(path.getAbsolutePath()))).into(((ImageViewHolder) holder).img);
                                                }
                                            }
                                        });
                                        return null;
                                    }
                                }.execute();
                            } else {
                                ((ImageViewHolder) holder).progressBar.setVisibility(View.INVISIBLE);
                                if(path.exists()){
                                    Glide.with(context).load(Uri.fromFile(new File(path.getAbsolutePath()))).into(((ImageViewHolder) holder).img);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            ((ImageViewHolder) holder).progressBar.setVisibility(View.INVISIBLE);
                            if(path.exists()){
                                Glide.with(context).load(Uri.fromFile(new File(path.getAbsolutePath()))).into(((ImageViewHolder) holder).img);
                            }
                        }
                    });
                    break;
                case Model.AUDIO_TYPE:
                    ((AudioViewHolder) holder).floatingActionButton.setEnabled(false);
                    ((AudioViewHolder) holder).floatingActionButton.setBackgroundColor(context.getResources().getColor(R.color.gray01));
                    ((AudioViewHolder) holder).courseName.setText(((Material)object.getData()).getMaterialUrl().substring(4));
                    ((AudioViewHolder) holder).openDate.setText(Utils.DateFormat(((Material)object.getData()).getCreateDate()));
                    ((AudioViewHolder) holder).description.setText(((Material)object.getData()).getDescription());
                    ((AudioViewHolder) holder).type.setText(((Material)object.getData()).getMaterialType());
                    if(((Material)object.getData()).getStatus() == "1"){
                        ((AudioViewHolder) holder).status.setText("Open");
                    }else {
                        ((AudioViewHolder) holder).status.setText("Close");
                    }
                    final File audioPath = new File(context.getExternalFilesDir(null) + "/material/"
                            + ((Material)object.getData()).getMaterialUrl().replace("\\","/"));
                    Call<ResponseBody> audio = apiInterface.getCoursePhoto(((Material)object.getData()).getId());
                    audio.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                            if (response.isSuccessful() && response.body() != null){
                                new AsyncTask<Void, Void, Void>(){
                                    @Override
                                    protected Void doInBackground(Void... voids){
                                        Utils.writeResponseBodyToDisk(audioPath.getAbsolutePath(), response.body());
                                        ((CourseDetailActivity) context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((AudioViewHolder) holder).floatingActionButton.setEnabled(true);
                                                ((AudioViewHolder) holder).floatingActionButton.setBackgroundColor(context.getResources().getColor(R.color.title_bar_search_color));
//                                                ((AudioViewHolder) holder).floatingActionButton.setBackgroundResource(R.mipmap.home_search);
                                            }
                                        });
                                        return null;
                                    }
                                }.execute();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                        }
                    });
                    ((AudioViewHolder) holder).floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (fabStateVolume) {
                                if (mPlayer.isPlaying()) {
                                    mPlayer.stop();
                                }
                                ((AudioViewHolder) holder).floatingActionButton.setImageResource(R.mipmap.volume);
                                fabStateVolume = false;
                            } else {
                                try {
                                    mPlayer.setDataSource(audioPath.getAbsolutePath());
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                                mPlayer.setLooping(true);
                                mPlayer.start();
                                ((AudioViewHolder) holder).floatingActionButton.setImageResource(R.mipmap.mute);
                                fabStateVolume = true;
                            }
                        }
                    });
                    break;
                case Model.PDF_TYPE:
                    final File pdfPath = new File(context.getExternalFilesDir(null) + "/material/"
                            + ((Material)object.getData()).getMaterialUrl().replace("\\","/"));
                    Call<ResponseBody> pdf = apiInterface.getCoursePhoto(((Material)object.getData()).getId());
                    pdf.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                            if (response.isSuccessful() && response.body() != null){
                                new AsyncTask<Void, Void, Void>(){
                                    @Override
                                    protected Void doInBackground(Void... voids){
                                        Utils.writeResponseBodyToDisk(pdfPath.getAbsolutePath(), response.body());
                                        ((CourseDetailActivity) context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((AudioViewHolder) holder).floatingActionButton.setEnabled(true);
                                                ((AudioViewHolder) holder).floatingActionButton.setBackgroundColor(context.getResources().getColor(R.color.title_bar_search_color));
//                                                ((AudioViewHolder) holder).floatingActionButton.setBackgroundResource(R.mipmap.home_search);
                                            }
                                        });
                                        return null;
                                    }
                                }.execute();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getCourseID(int position){
        if(getItemViewType(position) == Model.PDF_TYPE ||
                getItemViewType(position) == Model.IMAGE_TYPE ||
                getItemViewType(position) == Model.AUDIO_TYPE ||
                getItemViewType(position) == Model.VIDEO_TYPE)
            return ((Material) dataSet.get(position).getData()).getId();
        else
            return "";
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (dataSet.get(position).getType()) {
            case Model.VIDEO_TYPE:
                return Model.VIDEO_TYPE;
            case Model.AUDIO_TYPE:
                return Model.AUDIO_TYPE;
            case Model.IMAGE_TYPE:
                return Model.IMAGE_TYPE;
            case Model.PDF_TYPE:
                return Model.PDF_TYPE;
            default:
                return -1;
        }
    }

    protected static class VideoViewHolder extends RecyclerView.ViewHolder{

        VideoView videoView;
        TextView description;
        TextView name;
        TextView openDate;
        TextView type;
        TextView status;
        ProgressBar progressBar;

        protected VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video);
            name = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.desc);
            openDate = (TextView) itemView.findViewById(R.id.openDate);
            type = (TextView) itemView.findViewById(R.id.level);
            status = (TextView) itemView.findViewById(R.id.status);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_load_photo);
        }

    }

    protected static class ImageViewHolder extends RecyclerView.ViewHolder{

        TextView courseName;
        TextView description;
        TextView openDate;
        TextView type;
        TextView status;
        ImageView img;
        ProgressBar progressBar;

        protected ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.desc);
            openDate = (TextView) itemView.findViewById(R.id.openDate);
            type = (TextView) itemView.findViewById(R.id.level);
            status = (TextView) itemView.findViewById(R.id.status);
            img = (ImageView) itemView.findViewById(R.id.img);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_load_photo);
        }
    }

    protected static class AudioViewHolder extends RecyclerView.ViewHolder{

        TextView courseName;
        TextView description;
        TextView openDate;
        TextView type;
        TextView status;
        FloatingActionButton floatingActionButton;

        protected AudioViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.desc);
            openDate = (TextView) itemView.findViewById(R.id.openDate);
            type = (TextView) itemView.findViewById(R.id.level);
            status = (TextView) itemView.findViewById(R.id.status);
            floatingActionButton = (FloatingActionButton) itemView.findViewById(R.id.volume);
        }
    }

    protected static class PDFViewHolder extends RecyclerView.ViewHolder{

        TextView max;
        TextView min;
        TextView medium;
        TextView maxDesc;
        TextView minDesc;
        TextView mediumDesc;

        protected PDFViewHolder(@NonNull View itemView) {
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
