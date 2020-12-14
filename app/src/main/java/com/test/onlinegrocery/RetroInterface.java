package com.test.onlinegrocery;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetroInterface {

    @FormUrlEncoded
    @POST("login")
    Call<String> Login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<String> Register(@Field("name") String name, @Field("email") String email, @Field("password") String password);

}
