package com.example.inventorymanagement.activity.product;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;



import com.example.inventorymanagement.R;
import com.example.inventorymanagement.activity.material.materialsActivity;
import com.example.inventorymanagement.activity.material.stockChange;
import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.Materials;
import com.example.inventorymanagement.models.MaterialsIncoming;
import com.example.inventorymanagement.models.Products;
import com.example.inventorymanagement.models.ProductsOutgoing;
import com.example.inventorymanagement.url.url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class stockChangeProduct extends AppCompatActivity {
    public String id, ProductId, quantity, stock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dataplace);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ProductId = bundle.getString("ProductId");
            id = bundle.getString("id");
            quantity = bundle.getString("quantity");
            stock = bundle.getString("stock");
        } else {
            Toast.makeText(this, "No Message", Toast.LENGTH_LONG).show();
        }
        addd();


        Intent add = new Intent(stockChangeProduct.this, productsActivity.class);
        startActivity(add);

    }

    public void addd() {
        Integer stock1 = Integer.parseInt(stock);
        Integer quantity1 = Integer.parseInt(quantity);
        Integer newStock = stock1 - quantity1;
        String s = newStock.toString();
        UsersAPI userApi = url.getInstance().create(UsersAPI.class);
        Call<Products> UsersCall = userApi.editProductStock(ProductId, s);

        UsersCall.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                Toast.makeText(stockChangeProduct.this, "Material updated Successfully", Toast.LENGTH_SHORT).show();
                makeTrue();

            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(stockChangeProduct.this, "error message" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        makeTrue();
    }

    public void makeTrue() {
        String a = id;
        UsersAPI userApi = url.getInstance().create(UsersAPI.class);
        Call<ProductsOutgoing> UsersCall = userApi.editProductDispatched(id, "true");

        UsersCall.enqueue(new Callback<ProductsOutgoing>() {
            @Override
            public void onResponse(Call<ProductsOutgoing> call, Response<ProductsOutgoing> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                Toast.makeText(stockChangeProduct.this, "Material updated Successfully", Toast.LENGTH_SHORT).show();
                Intent add = new Intent(stockChangeProduct.this, productsActivity.class);
                startActivity(add);
            }

            @Override
            public void onFailure(Call<ProductsOutgoing> call, Throwable t) {
//                Toast.makeText(stockChange.this, "error message" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
