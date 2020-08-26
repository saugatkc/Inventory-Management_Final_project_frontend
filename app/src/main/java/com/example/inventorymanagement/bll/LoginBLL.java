package com.example.inventorymanagement.bll;

import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.serverresponse.SignUpResponse;
import com.example.inventorymanagement.url.url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Url;

public class LoginBLL {
    boolean isSuccess = false;

    public boolean checkUser(String username, String password) {

        UsersAPI usersAPI = url.getInstance().create(UsersAPI.class);

        Call<SignUpResponse> usersCall = usersAPI.checkUser(username, password);

        try {
            Response<SignUpResponse> loginResponse = usersCall.execute();
            if (loginResponse.isSuccessful() &&
                    loginResponse.body().getStatus().equals("Login success!")) {

                url.token += loginResponse.body().getToken();
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

        public boolean checkadmin(String username, String password) {

        UsersAPI api = url.getInstance().create(UsersAPI.class);
        Call<SignUpResponse> UsersCall = api.checkUser(username, password);

        try {
            Response<SignUpResponse> loginResponse = UsersCall.execute();
            if (loginResponse.isSuccessful() &&
                    loginResponse.body().getStatus().equals("isadmin")) {

                url.token += loginResponse.body().getToken();
                //url.admin += loginResponse.body().getAdmin();
                // Url.Cookie = imageResponseResponse.headers().get("Set-Cookie");
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

}
