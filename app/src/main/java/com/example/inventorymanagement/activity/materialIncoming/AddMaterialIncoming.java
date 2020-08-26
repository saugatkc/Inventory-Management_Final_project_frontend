package com.example.inventorymanagement.activity.materialIncoming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.activity.dailyProduct.AddDailyProduct;
import com.example.inventorymanagement.activity.dailyProduct.dailyProductsActivity;
import com.example.inventorymanagement.activity.material.materialsActivity;
import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.DailyProducts;
import com.example.inventorymanagement.models.MaterialsIncoming;
import com.example.inventorymanagement.url.url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMaterialIncoming extends AppCompatActivity {

    EditText etMaterial, etSupplier, etPhone, etQuantity, etTotalCost;
    ImageView imageBack;
    Button add;
    String id, material;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material_incoming);

        etMaterial = findViewById(R.id.etMaterial);
        etSupplier = findViewById(R.id.etSupplier);
        etPhone = findViewById(R.id.etPhone);
        etQuantity = findViewById(R.id.etQuantity);
        etTotalCost = findViewById(R.id.etTotalCost);
        imageBack = findViewById(R.id.imgBack);
        add = findViewById(R.id.btnAdd);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id= bundle.getString("id");
            material= bundle.getString("material");
        }
        else{
            Toast.makeText(this, "No Message", Toast.LENGTH_LONG).show();
        }

        etMaterial.setText(material);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AddMaterialIncoming.this, materialsActivity.class);
                startActivity(back);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMaterialIncoming();
            }


        });
    }
    private void addMaterialIncoming(){

        UsersAPI userApi = url.getInstance().create(UsersAPI.class);
        Call<MaterialsIncoming> UsersCall = userApi.addMaterialIncoming(id,etSupplier.getText().toString(),etPhone.getText().toString(),etQuantity.getText().toString(),etTotalCost.getText().toString());

        UsersCall.enqueue(new Callback<MaterialsIncoming>() {
            @Override
            public void onResponse(Call<MaterialsIncoming> call, Response<MaterialsIncoming> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddMaterialIncoming.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(AddMaterialIncoming.this, "details added Successfully", Toast.LENGTH_SHORT).show();
                Intent openHome = new Intent(AddMaterialIncoming.this, materialsIncomingActivity.class);
                startActivity(openHome);
            }

            @Override
            public void onFailure(Call<MaterialsIncoming> call, Throwable t) {
                Toast.makeText(AddMaterialIncoming.this, "details added Successfully", Toast.LENGTH_SHORT).show();
                Intent openHome = new Intent(AddMaterialIncoming.this, materialsIncomingActivity.class);
                startActivity(openHome);
            }
        });
    }
}