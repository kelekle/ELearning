package com.star.e_learning.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.star.e_learning.R;
import com.star.e_learning.api.ApiClient;
import com.star.e_learning.api.ApiInterface;
import com.star.e_learning.api.AppConfig;
import com.star.e_learning.api.Utils;
import com.star.e_learning.bean.Course;
import com.star.e_learning.bean.User;
import com.star.e_learning.repository.AppRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout headRelativeLayout;
    private RelativeLayout nameRelativeLayout;
    private RelativeLayout sexRelativeLayout;
    private RelativeLayout ageRelativeLayout;
    private RelativeLayout phoneRelativeLayout;
    private RelativeLayout regionRelativeLayout;
    private RelativeLayout descriptionRelativeLayout;

    private ImageView head;
    private TextView name;
    private TextView sex;
    private TextView age;
    private TextView region;
    private TextView description;
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    private AppRepository appRepository;

    private Bitmap headBitmap;// 头像Bitmap

    private static List<String> optionsSexItems = new ArrayList<>();
    private static List<String> optionsAgeItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    static {
        optionsSexItems.add("男");
        optionsSexItems.add("女");
        for(int i = 1;i < 121;i++){
            optionsAgeItems.add(String.valueOf(i));
        }
    }

    protected void initData(){
        User user = AppConfig.CURRENT_USER;
        if(user != null) {
            File file = new File(getExternalFilesDir(null).getAbsolutePath()
                    + "/user/" + AppConfig.CURRENT_USER.getUserid() + "/head.jpg");
            if (file.exists()) {
                head.setImageBitmap(Utils.getLocalBitmap(getExternalFilesDir(null).getAbsolutePath()
                        + "/user/" + AppConfig.CURRENT_USER.getUserid() + "/head.jpg"));
            } else {
                final File photo = new File(getExternalFilesDir(null) + "/user/" + user.getPhoto());
                Call<ResponseBody> call1 = apiInterface.getUserPhoto(user.getEmail());
                call1.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    Utils.writeResponseBodyToDisk(photo.getAbsolutePath(), response.body());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            head.setImageBitmap(Utils.getLocalBitmap(getExternalFilesDir(null).getAbsolutePath()
                                                    + "/user/" + AppConfig.CURRENT_USER.getUserid() + "/head.jpg"));
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
            }
        }
            name.setText(user.getUsername());
            sex.setText(user.getSex());
            age.setText(user.getAge());
            region.setText(user.getRegion());
            description.setText(user.getDescrtption());
            appRepository = new AppRepository(EditUserInfoActivity.this);
    }

    protected void initView() {
        setContentView(R.layout.activity_edit_user_info);
        headRelativeLayout = findViewById(R.id.profile_head_line);
        nameRelativeLayout = findViewById(R.id.profile_name_line);
        sexRelativeLayout = findViewById(R.id.profile_sex_line);
        ageRelativeLayout = findViewById(R.id.profile_age_line);
        regionRelativeLayout = findViewById(R.id.profile_region_line);
        descriptionRelativeLayout = findViewById(R.id.profile_description_line);
        head = findViewById(R.id.profile_head);
        name = findViewById(R.id.profile_name);
        sex = findViewById(R.id.profile_sex);
        age = findViewById(R.id.profile_age);
        region = findViewById(R.id.profile_region);
        description = findViewById(R.id.profile_description);
    }

    protected void setListener() {
        headRelativeLayout.setOnClickListener(this);
        nameRelativeLayout.setOnClickListener(this);
        phoneRelativeLayout.setOnClickListener(this);
        sexRelativeLayout.setOnClickListener(this);
        ageRelativeLayout.setOnClickListener(this);
        regionRelativeLayout.setOnClickListener(this);
        descriptionRelativeLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile_head_line:
                //todo change head icon
                showTypeDialog();
                break;
            case R.id.profile_name_line:
                Utils.start_Activity(EditUserInfoActivity.this, EditUsernameActivity.class);
                break;
            case R.id.profile_sex_line:
                OptionsPickerView pvOptions = new OptionsPickerBuilder(EditUserInfoActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        final String tx = optionsSexItems.get(options1);
                        Call<String> call = apiInterface.changeSex(tx);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    sex.setText(tx);
                                    AppConfig.CURRENT_USER.setSex(tx);
                                    appRepository.updateUser(AppConfig.CURRENT_USER);
                                }else {
                                    Utils.showLongToast(EditUserInfoActivity.this, "未知错误，更改失败！");
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Utils.showLongToast(EditUserInfoActivity.this, "未知错误，更改失败！");
                            }
                        });
                    }
                }).setOutSideCancelable(true)
                        .setSubmitText("完成")
                        .setCancelText("取消")
                        .isDialog(true)
                        .build();
                pvOptions.setPicker(optionsSexItems);
                pvOptions.show();
                break;
            case R.id.profile_age_line:
                OptionsPickerView ageOptions = new OptionsPickerBuilder(EditUserInfoActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        final String age_tx = optionsAgeItems.get(options1);
                        Call<String> call = apiInterface.changeAge(age_tx);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    age.setText(age_tx);
                                    AppConfig.CURRENT_USER.setAge(age_tx);
                                    appRepository.updateUser(AppConfig.CURRENT_USER);
                                }else {
                                    Utils.showLongToast(EditUserInfoActivity.this, "未知错误，更改失败！");
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Utils.showLongToast(EditUserInfoActivity.this, "未知错误，更改失败！");
                            }
                        });
                    }
                }).setOutSideCancelable(true)
                        .setSubmitText("完成")
                        .setCancelText("取消")
                        .isDialog(true)
                        .build();
                ageOptions.setPicker(optionsAgeItems);
                ageOptions.setSelectOptions(18);
                ageOptions.show();
                break;
            case R.id.profile_region_line:
                Utils.start_Activity(EditUserInfoActivity.this, EditRegionActivity.class);
                break;
            case R.id.profile_description_line:
                Utils.start_Activity(EditUserInfoActivity.this, EditDescriptionActivity.class);
                break;
        }
    }

    private void showTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(getExternalFilesDir(null).getAbsolutePath()
                                + "/user/" +  AppConfig.CURRENT_USER.getUserid() + "/head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(getExternalFilesDir(null).getAbsolutePath()
                            + "/user/" +  AppConfig.CURRENT_USER.getUserid() +"/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    headBitmap = extras.getParcelable("data");
                    if (headBitmap != null) {
                        setPicToView(headBitmap);// 保存在SD卡中
                        /**
                         * 上传服务器代码
                         */
                        File file = new File(getExternalFilesDir(null).getAbsolutePath()
                                + "/user/" +  AppConfig.CURRENT_USER.getUserid() + "/head.jpg");
                        RequestBody fileRQ = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("picture", file.getName(), fileRQ);
                        Call<String> call = apiInterface.changePhoto(AppConfig.CURRENT_USER.getEmail(), part);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.isSuccessful() && response.body()!= null){
                                    if(response.body().contains("ok")){
                                        head.setImageBitmap(headBitmap);// 用ImageView显示出来
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(getExternalFilesDir(null).getAbsolutePath()
                + "/user");
        file.mkdirs();// 创建文件夹
        String fileName = getExternalFilesDir(null).getAbsolutePath()
                + "/user/" + AppConfig.CURRENT_USER.getUserid() + "/head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
