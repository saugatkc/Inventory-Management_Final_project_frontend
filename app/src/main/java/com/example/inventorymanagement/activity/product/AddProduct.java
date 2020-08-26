package com.example.inventorymanagement.activity.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.activity.user.LoginActivity;
import com.example.inventorymanagement.activity.user.SignupActivity;
import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.Products;
import com.example.inventorymanagement.models.Users;
import com.example.inventorymanagement.serverresponse.SignUpResponse;
import com.example.inventorymanagement.url.url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProduct extends AppCompatActivity {

    EditText product, cost, description, stock;
    ImageView imageBack;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    product = findViewById(R.id.etProduct);
    cost = findViewById(R.id.etCost);
    description = findViewById(R.id.etDescription);
    stock = findViewById(R.id.etStock);
    imageBack = findViewById(R.id.imgBack);
    add = findViewById(R.id.btnAdd);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AddProduct.this, productsActivity.class);
                startActivity(back);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }


        });
    }
    private void addProduct(){

        Products products = new Products(product.getText().toString(), cost.getText().toString(), description.getText().toString(),stock.getText().toString());

        UsersAPI userApi = url.getInstance().create(UsersAPI.class);
        Call<Products> UsersCall = userApi.addProduct(products);

        UsersCall.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddProduct.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(AddProduct.this, "Product added Successfully", Toast.LENGTH_SHORT).show();
                Intent openHome = new Intent(AddProduct.this, productsActivity.class);
                startActivity(openHome);
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(AddProduct.this, "error message" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}