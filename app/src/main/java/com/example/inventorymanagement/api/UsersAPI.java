package com.example.inventorymanagement.api;

import com.example.inventorymanagement.models.DailyProducts;
import com.example.inventorymanagement.models.Materials;
import com.example.inventorymanagement.models.MaterialsIncoming;
import com.example.inventorymanagement.models.Products;
import com.example.inventorymanagement.models.ProductsOutgoing;
import com.example.inventorymanagement.models.Users;
import com.example.inventorymanagement.serverresponse.SignUpResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UsersAPI {

    @POST("users/signup")
    Call<SignUpResponse> signup(@Body Users users);

    @FormUrlEncoded
    @POST("users/login")
    Call<SignUpResponse> checkUser(@Field("username") String username, @Field("password") String password);

//    @Multipart
//    @POST("upload/users")
//    Call<ImageResponse> uploadImage(@Header("Authorization") String token, @Part MultipartBody.Part file);
//

    @GET("users/me")
    Call<Users> getUserDetails(@Header("Authorization") String token);

    @PUT("users/me")
    Call<Users> updateUser(@Header("Authorization") String token, @Body Users users);

    @GET("products/product")
    Call<List<Products>> getProducts();

    @GET("materials/material")
    Call<List<Materials>> getMaterials();

    @GET("materialsIncoming/materialIncoming")
    Call<List<MaterialsIncoming>> getMaterialsIncoming();

    @GET("productsOutgoing/productOutgoing")
    Call<List<ProductsOutgoing>> getProductsOutgoing();

    @GET("dailyProducts/dailyProduct")
    Call<List<DailyProducts>> getDailyProducts();


    @POST("products/product")
    Call<Products> addProduct(@Body Products products);

    @POST("materials/material")
    Call<Materials> addMaterials(@Body Materials materials);

    @FormUrlEncoded
    @POST("dailyProducts/dailyProduct")
    Call<DailyProducts> addDailyProduct(@Field("product") String product,@Field("quantity") String quantity,@Field("damaged") String damaged,@Field("remaining") String remaining);

    @FormUrlEncoded
    @POST("productsOutgoing/productOutgoing")
    Call<ProductsOutgoing> addProductOutgoing(@Field("product") String product,@Field("customer") String customer,@Field("phone") String phone,@Field("address") String address,@Field("quantity") String quantity,@Field("totalCost") String totalCost);

    @FormUrlEncoded
    @POST("materialsIncoming/materialIncoming")
    Call<MaterialsIncoming> addMaterialIncoming(@Field("material") String material,@Field("supplier") String supplier,@Field("phone") String phone,@Field("quantity") String quantity,@Field("totalCost") String totalCost);

    @FormUrlEncoded
    @PUT("materials/{id}")
    Call<Materials> editMaterials (@Path("id") String id,@Field("material") String material,@Field("stock") String stock);

    @FormUrlEncoded
    @PUT("products/{id}")
    Call<Products> editProduct (@Path("id") String id,@Field("product") String product,@Field("cost") String cost,@Field("description") String description,@Field("stock") String stock);

    @FormUrlEncoded
    @PUT("materials/{id}")
    Call<Materials> editMaterialStock (@Path("id") String id,@Field("stock") String stock);

    @FormUrlEncoded
    @PUT("materialsIncoming/{id}")
    Call<MaterialsIncoming> editMaterialDelivered (@Path("id") String id,@Field("delivered") String delivered);

    @FormUrlEncoded
    @PUT("products/{id}")
    Call<Products> editProductStock (@Path("id") String id,@Field("stock") String stock);

    @FormUrlEncoded
    @PUT("productsOutgoing/{id}")
    Call<ProductsOutgoing> editProductDispatched (@Path("id") String id,@Field("dispatched") String delivered);

    @DELETE("productsOutgoing/{id}")
    Call<Void> removeProductsOutgoing(@Path("id") String id);

    @DELETE("materialsIncoming/{id}")
    Call<Void> removeMaterialsIncoming(@Path("id") String id);

    @DELETE("dailyProducts/{id}")
    Call<Void> removeDailyProducts(@Path("id") String id);

}
