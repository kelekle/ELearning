package com.star.e_learning.ui.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;
import com.star.e_learning.R;
import com.star.e_learning.api.ApiClient;
import com.star.e_learning.api.ApiInterface;
import com.star.e_learning.bean.JsonBean;
import com.star.e_learning.util.AppConfig;
import com.star.e_learning.util.Utils;
import com.star.e_learning.bean.User;
import com.star.e_learning.repository.AppRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
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
    private RelativeLayout descriptionRelativeLayout;

    private CircleImageView head;
    private TextView name;
    private TextView description;
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    private AppRepository appRepository;

    private Bitmap headBitmap;// 头像Bitmap


    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    private static final String CROP_IMAGE_FILE_NAME = "bala_crop.jpg";
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 50;
    private static int output_Y = 50;
    //改变头像的标记位
    private int new_icon=0xa3;
    private String mExtStorDir;
    private Uri imageUri;

    private static List<String> optionsSexItems = new ArrayList<>();
    private static List<String> optionsAgeItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    @Override
    protected void onResume(){
        super.onResume();
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
            name.setText(user.getUsername());
            description.setText(user.getDescrtption());
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
        appRepository = new AppRepository(EditUserInfoActivity.this);
    }

    protected void initView() {
        setContentView(R.layout.activity_edit_user_info);
        headRelativeLayout = findViewById(R.id.profile_head_line);
        nameRelativeLayout = findViewById(R.id.profile_name_line);
        descriptionRelativeLayout = findViewById(R.id.profile_description_line);
        head = findViewById(R.id.profile_head);
        name = findViewById(R.id.profile_name);
        description = findViewById(R.id.profile_description);
    }

    protected void setListener() {
        headRelativeLayout.setOnClickListener(this);
        nameRelativeLayout.setOnClickListener(this);
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
//            case R.id.profile_sex_line:
//                OptionsPickerView pvOptions = new OptionsPickerBuilder(EditUserInfoActivity.this, new OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
//                        //返回的分别是三个级别的选中位置
//                        final String tx = optionsSexItems.get(options1);
//                        Call<String> call = apiInterface.changeSex(tx);
//                        call.enqueue(new Callback<String>() {
//                            @Override
//                            public void onResponse(Call<String> call, Response<String> response) {
//                                if(response.isSuccessful() && response.body() != null){
//                                    AppConfig.CURRENT_USER.setSex(tx);
//                                    appRepository.updateUser(AppConfig.CURRENT_USER);
//                                }else {
//                                    Utils.showLongToast(EditUserInfoActivity.this, "未知错误，更改失败！");
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<String> call, Throwable t) {
//                                Utils.showLongToast(EditUserInfoActivity.this, "未知错误，更改失败！");
//                            }
//                        });
//                    }
//                }).setOutSideCancelable(true)
//                        .setSubmitText("完成")
//                        .setCancelText("取消")
//                        .isDialog(true)
//                        .build();
//                pvOptions.setPicker(optionsSexItems);
//                pvOptions.show();
//                break;
//            case R.id.profile_age_line:
//                OptionsPickerView ageOptions = new OptionsPickerBuilder(EditUserInfoActivity.this, new OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
//                        final String age_tx = optionsAgeItems.get(options1);
//                        Call<String> call = apiInterface.changeAge(age_tx);
//                        call.enqueue(new Callback<String>() {
//                            @Override
//                            public void onResponse(Call<String> call, Response<String> response) {
//                                if(response.isSuccessful() && response.body() != null){
//                                    age.setText(age_tx);
//                                    AppConfig.CURRENT_USER.setAge(age_tx);
//                                    appRepository.updateUser(AppConfig.CURRENT_USER);
//                                }else {
//                                    Utils.showLongToast(EditUserInfoActivity.this, "未知错误，更改失败！");
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<String> call, Throwable t) {
//                                Utils.showLongToast(EditUserInfoActivity.this, "未知错误，更改失败！");
//                            }
//                        });
//                    }
//                }).setOutSideCancelable(true)
//                        .setSubmitText("完成")
//                        .setCancelText("取消")
//                        .isDialog(true)
//                        .build();
//                ageOptions.setPicker(optionsAgeItems);
//                ageOptions.setSelectOptions(18);
//                ageOptions.show();
//                break;
//            case R.id.profile_region_line:
//                Utils.start_Activity(EditUserInfoActivity.this, EditRegionActivity.class);
//                break;
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
                startActivityForResult(intent1, CODE_GALLERY_REQUEST);
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
                startActivityForResult(intent2, CODE_CAMERA_REQUEST);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;
            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(getImageContentUri(tempFile));
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }
                break;
            case CODE_RESULT_REQUEST:
                System.out.println("coming");
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                setImageToHeadView(intent,bitmap);
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            String storage = Environment.getExternalStorageDirectory().getPath();
            File file = new File(getExternalFilesDir(null).getAbsolutePath() + "/temp.jpg");
//            if (!dirFile.exists()) {
//                if (!dirFile.mkdirs()) {
//                    System.out.println("文件夹创建失败");
//                } else {
//                    System.out.println( "文件夹创建成功");
//                }
//            }
//            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(file);
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);
        //将裁剪好的图输出到所建文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //注意：此处应设置return-data为false，如果设置为true，是直接返回bitmap格式的数据，耗费内存。设置为false，然后，设置裁剪完之后保存的路径，即：intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
//        intent.putExtra("return-data", true);
        intent.putExtra("return-data", false);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent,Bitmap bitmap){
        File file = new File(getExternalFilesDir(null).getAbsolutePath() + "/temp.jpg");
        RequestBody fileRQ = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("photo", file.getName(), fileRQ);
        RequestBody param = RequestBody.create(MediaType.parse("text/plain"), AppConfig.CURRENT_USER.getEmail());
        Call<JsonObject> call = apiInterface.changePhoto(param, part);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful() && response.body()!= null){
                    System.out.println("upload ok");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(EditUserInfoActivity.this)
                                    .load(imageUri)
                                    .apply(RequestOptions.circleCropTransform())
                                    .apply(RequestOptions.fitCenterTransform())
                                    .into(head);
                            File pic = new File(getExternalFilesDir(null).getAbsolutePath()
                                    + "/user/" + AppConfig.CURRENT_USER.getUserid() + "/head.jpg");
                            if(pic.exists()){
                                pic.delete();
                            }
                            file.renameTo(pic);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
//        try {
//            if (intent != null) {
//                System.out.println("setttttttt");
//                Bitmap b = imageZoom(bitmap);//看个人需求，可以不压缩
//                Glide.with(this).load(imageUri).apply(RequestOptions.circleCropTransform())
//                                .apply(RequestOptions.fitCenterTransform()).into(head);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    private Bitmap imageZoom(Bitmap bitMap) {
        //图片允许最大空间   单位：KB
        double maxSize =1000.00;
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length/1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),
                    bitMap.getHeight() / Math.sqrt(i));
        }
        return bitMap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }


//    @Override
//    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 1:
//                System.out.println("111");
//                if (resultCode == RESULT_OK) {
//                    System.out.println("crop");
//                    cropPhoto(data.getData());// 裁剪图片
//                }
//                break;
//            case 2:
//                System.out.println("222");
//                if (resultCode == RESULT_OK) {
//                    System.out.println("temp");
//                    File temp = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
//                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
//                }
//                break;
//            case 3:
//                System.out.println("result:" + resultCode + "  " + RESULT_OK);
////                if (data != null) {
//                    Uri uri = geturi(data);//解决方
//                    System.out.println(uri);
//                    System.out.println("333 " + data);
//                    Uri extras = data.getData();
//                    //head = extras.getParcelable("data");
////                    try {
//                        headBitmap = Utils.getLocalBitmap("file:///sdcard/temp.jpg");
////                        BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
////                    } catch (FileNotFoundException e) {
////                        e.printStackTrace();
////                    }
//                    if (headBitmap != null) {
//                        System.out.println("ookk");
//                        setPicToView(headBitmap);// 保存在SD卡中
//                        File file = new File(getExternalFilesDir(null).getAbsolutePath()
//                                + "/user/" +  AppConfig.CURRENT_USER.getUserid() + "/head.jpg");
//                        RequestBody fileRQ = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                        MultipartBody.Part part = MultipartBody.Part.createFormData("picture", file.getName(), fileRQ);
//                        Call<JsonObject> call = apiInterface.changePhoto(AppConfig.CURRENT_USER.getEmail(), part);
//                        call.enqueue(new Callback<JsonObject>() {
//                            @Override
//                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                                if(response.isSuccessful() && response.body()!= null){
//                                    System.out.println("upload ok");
//                                    head.setImageBitmap(headBitmap);// 用ImageView显示出来
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<JsonObject> call, Throwable t) {
//
//                            }
//                        });
//                    }
////                }
//                break;
//            default:
//                break;
//        }
//    }



}
