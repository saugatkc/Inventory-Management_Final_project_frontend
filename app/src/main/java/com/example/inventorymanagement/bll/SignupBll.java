package com.example.inventorymanagement.bll;

import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.Users;
import com.example.inventorymanagement.serverresponse.SignUpResponse;
import com.example.inventorymanagement.url.url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class SignupBll {
    boolean isSuccess = false;

    public boolean signup(String fullname, String username, String contact, String email, String password) {

        Users users = new Users (fullname, username, contact, email, password);

        UsersAPI userAPI = url.getInstance().create(UsersAPI.class);
        Call<SignUpResponse> UsersCall = userAPI.signup(users);

        try {
            Response<SignUpResponse> signupResponse = UsersCall.execute();
            if (signupResponse.isSuccessful() &&
                    signupResponse.body().getStatus().equals("Signup Successful")) {

//                url.token += signupResponse.body().getToken();
//                // Url.Cookie = imageResponseResponse.headers().get("Set-Cookie");
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
