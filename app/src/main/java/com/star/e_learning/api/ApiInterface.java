package com.star.e_learning.api;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.gson.JsonObject;
import com.star.e_learning.bean.Course;
import com.star.e_learning.bean.Material;
import com.star.e_learning.bean.Teacher;
import com.star.e_learning.bean.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface ApiInterface {

    @GET("courses")
    Call<List<Course>> getCourses(

    );

    @GET("courses/{id}")
    Call<Course> getCourse(
            @Path("id") String id
    );

    @GET("courses/{id}/materials")
    Call<List<Material>> getCourseMaterials(
            @Path("id") String id
    );

    @GET("courses/{id}/teachers")
    Call<List<Teacher>> getCourseTeachers(
            @Path("id") String id
    );

    @Streaming
    @GET("courses/{id}/photo")
    Call<ResponseBody> getCoursePhoto(
            @Path("id") String id
    );

    @Streaming
    @GET("materials/{id}/media")
    Call<ResponseBody> getMaterialsMedia(
            @Path("id") String id
    );

    @Streaming
    @GET("materials/{id}/file")
    Call<ResponseBody> getMaterialsFile(
            @Path("id") String id
    );

    @Streaming
    @GET("materials/{id}/videoframe")
    Call<ResponseBody> getMaterialsVideoFrame(
            @Path("id") String id
    );

    @Streaming
    @GET("teachers/{id}/photo")
    Call<ResponseBody> getTeacherPhoto(
            @Path("id") String id
    );

    @FormUrlEncoded
    @POST("user/login")
    Call<JsonObject> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("user/register")
    Call<JsonObject> register(
            @Field("email") String email,
            @Field("password") String password,
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST("user/find_pwd")
    Call<JsonObject> findPassword(
            @Field("email") String email,
            @Field("password") String password,
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST("user/get_code")
    Call<JsonObject> getCode(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("user/change_username")
    Call<JsonObject> changeUsername(
            @Field("email") String email,
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("user/change_description")
    Call<JsonObject> changeDescription(
            @Field("email") String email,
            @Field("description") String description
    );

//    @FormUrlEncoded
//    @POST("user/change_sex")
//    Call<String> changeSex(
//            @Field("sex") String sex
//    );
//
//    @FormUrlEncoded
//    @POST("user/change_age")
//    Call<String> changeAge(
//            @Field("age") String age
//    );
//
//    @FormUrlEncoded
//    @POST("user/change_region")
//    Call<String> changeRegion(
//            @Field("region") String region
//    );

    @Multipart
    @POST("user/change_photo")
    Call<JsonObject> changePhoto(
            @Part("email") RequestBody email, @Part MultipartBody.Part photo
    );

    @Streaming
    @GET("user/get_user_photo")
    Call<ResponseBody> getUserPhoto(
            @Query("name") String name
    );

    @GET("user/get_user")
    Call<User> getUser(
            @Query("email") String email
    );

}
