package com.example.pet.interfaces;


import com.example.pet.dto.commentD;
import com.example.pet.dto.likeD;
import com.example.pet.dto.postD;
import com.example.pet.dto.postDs;
import com.example.pet.dto.resD;
import com.example.pet.dto.tagD;
import com.example.pet.dto.userD;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface NInterface {

    @FormUrlEncoded
    @POST("user/login")
    Call<resD> loginI(
            @Field("UserID") String userID,
            @Field("UserPWD") String userPWD
    );

    @FormUrlEncoded
    @POST("user/join")
    Call<userD> joinI(
            @Field("UserID") String userID,
            @Field("UserPWD") String userPWD,
            @Field("UserPWD2") String userPWD2
    );

    @FormUrlEncoded
    @POST("posting/addpost")
    Call<postD> postI(
            @Field("title") String title,
            @Field("content") String content,
            @Field("writer") String writer
    );
    @FormUrlEncoded
    @POST("posting/update")
    Call<postD> post_updateI(
            @Field("title") String title,
            @Field("content") String content,
            @Field("writer") String writer,
            @Field("write_date") String write_date,
            @Field("postno") String postno

    );
    @FormUrlEncoded
    @POST("posting/delete")
    Call<postD> post_deleteI(
            @Field("postno") String postno
    );

    @Multipart
    @POST("iposting/addipost")
    Call<resD> postiI(
            @Part MultipartBody.Part image,
            @Part("title") RequestBody title,
            @Part("content") RequestBody content,
            @Part("writer") RequestBody writer
            );

    @FormUrlEncoded
    @POST("classr/addclass")
    Call<resD> postcI(
            @Field("title") String title,
            @Field("content") String content,
            @Field("writer") String writer,
            @Field("addtime") String addtime,
            @Field("latitue") double latitue,
            @Field("longitude") double longitude
    );
    @FormUrlEncoded
    @POST("classr/classmember")
    Call<ArrayList<String>> classmember(
            @Field("postno") int postno
    );
    @FormUrlEncoded
    @POST("classr/joinclass")
    Call<resD> joinclass(
            @Field("postno") int postno,
            @Field("userID") String userID
    );

    @Multipart
    @POST("lostdog/addfinda")
    Call<resD> postfaI(
            @Part MultipartBody.Part image,
            @Part("title") RequestBody title,
            @Part("content") RequestBody content,
            @Part("writer") RequestBody writer,
            @Part("latitue") RequestBody latitue,
            @Part("longitude") RequestBody longitude
    );

    @Multipart
    @POST("lostdog/addfindm")
    Call<resD> postfmI(
            @Part MultipartBody.Part image,
            @Part("title") RequestBody title,
            @Part("content") RequestBody content,
            @Part("writer") RequestBody writer,
            @Part("latitue") RequestBody latitue,
            @Part("longitude") RequestBody longitude
    );


    @FormUrlEncoded
    @POST("lookup/lookuppost")
    Call<ArrayList<postD>> lookupI(
            @Field("page_n") int page_n,
            @Field("data_n") int data_n
    );
    @FormUrlEncoded
    @POST("lookup/lookupposti")
    Call<ArrayList<postD>> lookupiI(
            @Field("page_n") int page_n,
            @Field("data_n") int data_n
    );
    @FormUrlEncoded
    @POST("lookup/lookupfinda")
    Call<ArrayList<postD>> lookupfaI(
            @Field("kind") String kind,
            @Field("who") String who,
            @Field("page_n") int page_n,
            @Field("data_n") int data_n,
            @Field("latitue") double latitue,
            @Field("longitude") double longitude
    );

    @FormUrlEncoded
    @POST("lookup/lookupclass")
    Call<ArrayList<postD>> lookupcI(
            @Field("page_n") int page_n,
            @Field("data_n") int data_n,
            @Field("latitue") double latitue,
            @Field("longitude") double longitude
    );


    @GET("lookup/lookuppost/{postno}")
    Call<postD> lookuppI(
            @Path("postno") int postno
    );
    @GET("lookup/lookupposti/{postno}")
    Call<postD> lookuppiI(
            @Path("postno") int postno
    );
    @GET("lookup/lookupfinda/{postno}")
    Call<postD> lookupfapI(
            @Path("postno") int postno
    );
    @GET("lookup/lookupfindm/{postno}")
    Call<postD> lookupfmpI(
            @Path("postno") int postno
    );
    @GET("lookup/lookupclass/{postno}")
    Call<postD> lookupcpI(
            @Path("postno") int postno
    );



    @FormUrlEncoded
    @POST("lookup/lookuppost/tag")
    Call<ArrayList<postD>> lookuptI(
            @Field("tag") String tag,
            @Field("page_n") int page_n,
            @Field("data_n") int data_n
    );
    @FormUrlEncoded
    @POST("lookup/lookuppost/find")
    Call<ArrayList<postD>> lookupfI(
            @Field("kind") String kind,
            @Field("what") String what,
            @Field("page_n") int page_n,
            @Field("data_n") int data_n
    );


    @FormUrlEncoded
    @POST("lookup/lookupposti/tag")
    Call<ArrayList<postD>> lookupitI(
            @Field("tag") String tag,
            @Field("page_n") int page_n,
            @Field("data_n") int data_n
    );
    @FormUrlEncoded
    @POST("lookup/lookupposti/find")
    Call<ArrayList<postD>> lookupifI(
            @Field("kind") String kind,
            @Field("what") String what,
            @Field("sort") String sort,
            @Field("page_n") int page_n,
            @Field("data_n") int data_n
    );


    @GET("likes/tagauto/n")
    Call<ArrayList<tagD>> tagautonI();
    @GET("likes/tagauto/i")
    Call<ArrayList<tagD>> tagautoiI();
    @GET("likes/favoritetag/n")
    Call<ArrayList<tagD>> favoritetagnI();
    @GET("likes/favoritetag/i")
    Call<ArrayList<tagD>> favoritetagiI();


    @FormUrlEncoded
    @POST("comment/addcomment/post")
    Call<commentD> addcommentpI(
            @Field("postno") int postno,
            @Field("writer") String writer,
            @Field("content") String content
    );
    @FormUrlEncoded
    @POST("comment/addcomment/ipost")
    Call<commentD> addcommentipI(
            @Field("postno") int postno,
            @Field("writer") String writer,
            @Field("content") String content
    );
    @FormUrlEncoded
    @POST("comment/addcomment/class")
    Call<commentD> addcommentcI(
            @Field("postno") int postno,
            @Field("writer") String writer,
            @Field("content") String content
    );
    @FormUrlEncoded
    @POST("comment/addcomment/finda")
    Call<commentD> addcommentfaI(
            @Field("postno") int postno,
            @Field("writer") String writer,
            @Field("content") String content
    );
    @FormUrlEncoded
    @POST("comment/addcomment/findm")
    Call<commentD> addcommentfmI(
            @Field("postno") int postno,
            @Field("writer") String writer,
            @Field("content") String content
    );

    @FormUrlEncoded
    @POST("comment/showcomment/post")
    Call<ArrayList<commentD>> showcommentpI(
            @Field("postno") int postno
    );
    @FormUrlEncoded
    @POST("comment/showcomment/ipost")
    Call<ArrayList<commentD>> showcommentipI(
            @Field("postno") int postno
    );
    @FormUrlEncoded
    @POST("comment/showcomment/class")
    Call<ArrayList<commentD>> showcommentcI(
            @Field("postno") int postno
    );
    @FormUrlEncoded
    @POST("comment/showcomment/finda")
    Call<ArrayList<commentD>> showcommentfaI(
            @Field("postno") int postno
    );
    @FormUrlEncoded
    @POST("comment/showcomment/findm")
    Call<ArrayList<commentD>> showcommentfmI(
            @Field("postno") int postno
    );

    @FormUrlEncoded
    @POST("likes/addlike")
    Call<resD> addlikeI(
            @Field("postno") int postno,
            @Field("UserID") String UserID
    );

    @FormUrlEncoded
    @POST("likes/dellike")
    Call<resD> dellikeI(
            @Field("postno") int postno,
            @Field("UserID") String UserID
    );
    @FormUrlEncoded
    @POST("likes/chklike")
    Call<resD> chklikeI(
            @Field("postno") int postno,
            @Field("UserID") String UserID
    );
    @GET("likes/cntlike")
    Call<ArrayList<likeD>> cntlikeI(
    );






    @GET("getse/sd")
    Call<userD> getss();


}
