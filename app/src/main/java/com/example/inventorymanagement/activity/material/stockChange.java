package com.example.inventorymanagement.activity.material;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.Materials;
import com.example.inventorymanagement.models.MaterialsIncoming;
import com.example.inventorymanagement.url.url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class stockChange extends AppCompatActivity {

    public String id, MaterialId,quantity,stock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dataplace);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            MaterialId=bundle.getString("MaterialId");
            id= bundle.getString("id");
            quantity= bundle.getString("quantity");
            stock= bundle.getString("stock");
        }
        else{
            Toast.makeText(this, "No Message", Toast.LENGTH_LONG).show();
        }
        addd();


        Intent add = new Intent(stockChange.this, materialsActivity.class);
        startActivity(add);

    }

    public void addd(){
        Integer stock1=Integer.parseInt(stock);
        Integer quantity1=Integer.parseInt(quantity);
        Integer newStock= stock1 + quantity1;
        String s=newStock.toString();
        UsersAPI userApi = url.getInstance().create(UsersAPI.class);
        Call<Materials> UsersCall = userApi.editMaterialStock(MaterialId,s);

        UsersCall.enqueue(new Callback<Materials>() {
            @Override
            public void onResponse(Call<Materials> call, Response<Materials> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                Toast.makeText(stockChange.this, "Material updated Successfully", Toast.LENGTH_SHORT).show();
                makeTrue();

            }

            @Override
            public void onFailure(Call<Materials> call, Throwable t) {
                Toast.makeText(stockChange.this, "error message" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        makeTrue();
    }

    public  void makeTrue(){
        String a =id;
        UsersAPI userApi = url.getInstance().create(UsersAPI.class);
        Call<MaterialsIncoming> UsersCall = userApi.editMaterialDelivered(id,"true");

        UsersCall.enqueue(new Callback<MaterialsIncoming>() {
            @Override
            public void onResponse(Call<MaterialsIncoming> call, Response<MaterialsIncoming> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                Toast.makeText(stockChange.this, "Material updated Successfully", Toast.LENGTH_SHORT).show();
                Intent add = new Intent(stockChange.this, materialsActivity.class);
                startActivity(add);
            }

            @Override
            public void onFailure(Call<MaterialsIncoming> call, Throwable t) {
//                Toast.makeText(stockChange.this, "error message" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}