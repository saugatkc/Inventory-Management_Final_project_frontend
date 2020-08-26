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
import com.example.inventorymanagement.activity.material.EditMaterial;
import com.example.inventorymanagement.activity.material.materialsActivity;
import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.Materials;
import com.example.inventorymanagement.models.Products;
import com.example.inventorymanagement.url.url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProduct extends AppCompatActivity {

    String id, product,cost,description, stock;
    EditText  etProduct,etCost,etDescription,etStock;
    ImageView imageBack;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etProduct = findViewById(R.id.etProduct);
        etCost = findViewById(R.id.etCost);
        etDescription = findViewById(R.id.etDescription);
        etStock = findViewById(R.id.etStock);
        imageBack = findViewById(R.id.imgBack);
        btnAdd= findViewById(R.id.btnAdd);

        btnAdd.setText("Update");

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(EditProduct.this, productsActivity.class);
                startActivity(back);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id= bundle.getString("id");
            product= bundle.getString("product");
            cost= bundle.getString("cost");
            description= bundle.getString("description");
            stock= bundle.getString("stock");
        }
        else{
            Toast.makeText(this, "No Message", Toast.LENGTH_LONG).show();
        }

        etStock.setText(stock);
        etProduct.setText(product);
        etCost.setText(cost);
        etDescription.setText(description);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProduct();
            }


        });
    }

    private void editProduct(){

        UsersAPI userApi = url.getInstance().create(UsersAPI.class);
        Call<Products> UsersCall = userApi.editProduct(id,etProduct.getText().toString(),etCost.getText().toString(),etDescription.getText().toString(),etStock.getText().toString());

        UsersCall.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(EditProduct.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(EditProduct.this, "Product updated Successfully", Toast.LENGTH_SHORT).show();
                Intent openHome = new Intent(EditProduct.this, productsActivity.class);
                startActivity(openHome);
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(EditProduct.this, "error message" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}