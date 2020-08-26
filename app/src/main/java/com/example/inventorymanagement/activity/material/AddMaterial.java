package com.example.inventorymanagement.activity.material;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.activity.product.AddProduct;
import com.example.inventorymanagement.activity.product.productsActivity;
import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.Materials;
import com.example.inventorymanagement.models.Products;
import com.example.inventorymanagement.url.url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMaterial extends AppCompatActivity {

    EditText material, stock;
    ImageView imageBack;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);

        material = findViewById(R.id.etMaterial);
        stock = findViewById(R.id.etStock);
        imageBack = findViewById(R.id.imgBack);
        add = findViewById(R.id.btnAdd);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AddMaterial.this, materialsActivity.class);
                startActivity(back);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMaterials();
            }


        });
    }

    private void addMaterials(){

        Materials materials = new Materials(material.getText().toString(), stock.getText().toString());

        UsersAPI userApi = url.getInstance().create(UsersAPI.class);
        Call<Materials> UsersCall = userApi.addMaterials(materials);

        UsersCall.enqueue(new Callback<Materials>() {
            @Override
            public void onResponse(Call<Materials> call, Response<Materials> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddMaterial.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(AddMaterial.this, "Material added Successfully", Toast.LENGTH_SHORT).show();
                Intent openHome = new Intent(AddMaterial.this, materialsActivity.class);
                startActivity(openHome);
            }

            @Override
            public void onFailure(Call<Materials> call, Throwable t) {
                Toast.makeText(AddMaterial.this, "error message" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}