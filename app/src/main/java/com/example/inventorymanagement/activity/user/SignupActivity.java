package com.example.inventorymanagement.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.Users;
import com.example.inventorymanagement.serverresponse.SignUpResponse;
import com.example.inventorymanagement.url.url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity  extends AppCompatActivity {

    Button login, btnSignup;
    EditText etfullname, etusername, etcontact, etemail, etpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        login = findViewById(R.id.btnAlreadylogin);
        etfullname = findViewById(R.id.etfullname);
        etusername = findViewById(R.id.etusername);
        etcontact = findViewById(R.id.etcontact);
        etemail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);
        btnSignup = findViewById(R.id.btnregister);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                register();
            }


            private void register() {

                String fullname = etfullname.getText().toString().trim();
                String username = etusername.getText().toString().trim();
                String contact = etcontact.getText().toString().trim();
                String email = etemail.getText().toString().trim();
                String password = etpassword.getText().toString().trim();
                Users users = new Users(fullname, username, contact, email, password);

                UsersAPI userApi = url.getInstance().create(UsersAPI.class);
                Call<SignUpResponse> UsersCall = userApi.signup(users);

                UsersCall.enqueue(new Callback<SignUpResponse>() {
                    @Override
                    public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(SignupActivity.this, "Signup Successfull", Toast.LENGTH_SHORT).show();
                        Intent openHome = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(openHome);
                    }

                    @Override
                    public void onFailure(Call<SignUpResponse> call, Throwable t) {
                        Toast.makeText(SignupActivity.this, "error message" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginpage();
            }

            private void openLoginpage() {
                Intent goToLogin = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(goToLogin);
            }
        });

    }
}
