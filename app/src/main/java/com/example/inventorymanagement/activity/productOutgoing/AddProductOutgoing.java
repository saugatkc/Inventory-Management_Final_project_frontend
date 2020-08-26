package com.example.inventorymanagement.activity.productOutgoing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.activity.dailyProduct.dailyProductsActivity;
import com.example.inventorymanagement.activity.materialIncoming.AddMaterialIncoming;
import com.example.inventorymanagement.activity.materialIncoming.materialsIncomingActivity;
import com.example.inventorymanagement.activity.product.productsActivity;
import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.MaterialsIncoming;
import com.example.inventorymanagement.models.ProductsOutgoing;
import com.example.inventorymanagement.url.url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductOutgoing extends AppCompatActivity {

    EditText etProduct, etCustomer, etPhone,etAddress, etQuantity, etTotalCost;
    ImageView imageBack;
    Button add;
    String id, product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_outgoing);


        etProduct = findViewById(R.id.etProduct);
        etCustomer = findViewById(R.id.etCustomer);
        etPhone = findViewById(R.id.etPhone);
        etQuantity = findViewById(R.id.etQuantity);
        etAddress = findViewById(R.id.etAddress);
        etTotalCost = findViewById(R.id.etTotalCost);
        imageBack = findViewById(R.id.imgBack);
        add = findViewById(R.id.btnPOAdd);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id= bundle.getString("id");
            product= bundle.getString("product");
        }
        else{
            Toast.makeText(this, "No Message", Toast.LENGTH_LONG).show();
        }

        etProduct.setText(product);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AddProductOutgoing.this, productsActivity.class);
                startActivity(back);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductOutgoing();
            }


        });
    }

    private void addProductOutgoing(){
        String i = id;

        UsersAPI userApi = url.getInstance().create(UsersAPI.class);
        Call<ProductsOutgoing> UsersCall = userApi.addProductOutgoing(i,etCustomer.getText().toString(),etPhone.getText().toString(),etAddress.getText().toString(),etQuantity.getText().toString(),etTotalCost.getText().toString());

        UsersCall.enqueue(new Callback<ProductsOutgoing>() {
            @Override
            public void onResponse(Call<ProductsOutgoing> call, Response<ProductsOutgoing> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddProductOutgoing.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(AddProductOutgoing.this, "details added Successfully", Toast.LENGTH_SHORT).show();
                Intent openHome = new Intent(AddProductOutgoing.this, productsOutgoingActivity.class);
                startActivity(openHome);
            }

            @Override
            public void onFailure(Call<ProductsOutgoing> call, Throwable t) {
                Toast.makeText(AddProductOutgoing.this, "details added Successfully", Toast.LENGTH_SHORT).show();
                Intent openHome = new Intent(AddProductOutgoing.this, productsOutgoingActivity.class);
                startActivity(openHome);
            }
        });
    }


}