package com.example.inventorymanagement.activity.dailyProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.activity.material.AddMaterial;
import com.example.inventorymanagement.activity.material.materialsActivity;
import com.example.inventorymanagement.activity.material.stockChange;
import com.example.inventorymanagement.activity.product.productsActivity;
import com.example.inventorymanagement.activity.productOutgoing.AddProductOutgoing;
import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.DailyProducts;
import com.example.inventorymanagement.models.Materials;
import com.example.inventorymanagement.models.Products;
import com.example.inventorymanagement.models.ProductsOutgoing;
import com.example.inventorymanagement.url.url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDailyProduct extends AppCompatActivity {

    EditText etProduct, etQuantity, etDamaged, etRemaining;
    ImageView imageBack;
    Button add;
    String id, product, stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily_product);

        etProduct = findViewById(R.id.etProduct);
        etQuantity = findViewById(R.id.etQuantity);
        etDamaged = findViewById(R.id.etDamaged);
        etRemaining = findViewById(R.id.etRemaining);
        imageBack = findViewById(R.id.imgBack);
        add = findViewById(R.id.btnAdd);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id= bundle.getString("id");
            product= bundle.getString("product");
            stock= bundle.getString("stock");
        }
        else{
            Toast.makeText(this, "No Message", Toast.LENGTH_LONG).show();
        }
        etProduct.setText(product);


        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AddDailyProduct.this, productsActivity.class);
                startActivity(back);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDailyProduct();
            }


        });
    }

    private void addDailyProduct(){
        String quantity = etQuantity.getText().toString();
        String damaged = etDamaged.getText().toString();
        String remaining= etRemaining.getText().toString();


        UsersAPI userApi = url.getInstance().create(UsersAPI.class);
        Call<DailyProducts> UsersCall = userApi.addDailyProduct(id,quantity,damaged,remaining);

        UsersCall.enqueue(new Callback<DailyProducts>() {
            @Override
            public void onResponse(Call<DailyProducts> call, Response<DailyProducts> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddDailyProduct.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(AddDailyProduct.this, "details added Successfully", Toast.LENGTH_SHORT).show();
                stockChange();
            }

            @Override
            public void onFailure(Call<DailyProducts> call, Throwable t) {
                Toast.makeText(AddDailyProduct.this, "details added Successfully", Toast.LENGTH_SHORT).show();
                stockChange();
            }
        });
    }

    public void stockChange(){
        Integer stock1=Integer.parseInt(stock);
        Integer quantity1=Integer.parseInt(etRemaining.getText().toString());
        Integer newStock= stock1 + quantity1;
        String s=newStock.toString();
        UsersAPI userApi = url.getInstance().create(UsersAPI.class);
        Call<Products> UsersCall = userApi.editProductStock(id,s);

        UsersCall.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                Toast.makeText(AddDailyProduct.this, "product updated Successfully", Toast.LENGTH_SHORT).show();
                Intent openHome = new Intent(AddDailyProduct.this, dailyProductsActivity.class);
                startActivity(openHome);

            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(AddDailyProduct.this, "error message" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}