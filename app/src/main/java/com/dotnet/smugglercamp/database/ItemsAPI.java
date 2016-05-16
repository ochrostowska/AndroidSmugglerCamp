package com.dotnet.smugglercamp.database;

import com.dotnet.smugglercamp.Item;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ItemsAPI {

    // Tworzymy zapytanie typu POST podając URL realizujący dodawanie rekordów do bazy
    // W tym przypadku jest to skrypt addItems.php
    @FormUrlEncoded
    @POST("/addItems.php")
    Call<ResponseBody> insertUser(
            @Field("item_id") int item_id,
            @Field("codename") String codename,
            @Field("name") String name,
            @Field("quantity") int quantity);

    @GET("/listItems.php")
    Call<List<Item>> getItems();
}