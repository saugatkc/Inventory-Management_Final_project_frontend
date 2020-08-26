package com.example.inventorymanagement.activity.material;

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
import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.Materials;
import com.example.inventorymanagement.url.url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMaterial extends AppCompatActivity {

    String id, material, stock;
    EditText etStock;
    TextView etMaterial;
    Button btnAdd;
    ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);

        etMaterial = findViewById(R.id.etMaterial);
        etStock = findViewById(R.id.etStock);
        imageBack = findViewById(R.id.imgBack);
        btnAdd= findViewById(R.id.btnAdd);

        btnAdd.setText("Update");

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(EditMaterial.this, materialsActivity.class);
                startActivity(back);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id= bundle.getString("id");
            material= bundle.getString("material");
            stock= bundle.getString("stock");
        }
        else{
            Toast.makeText(this, "No Message", Toast.LENGTH_LONG).show();
        }

        etStock.setText(stock);
        etMaterial.setText(material);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMaterial();
            }


        });
    }

    private void editMaterial(){

        UsersAPI userApi = url.getInstance().create(UsersAPI.class);
        Call<Materials> UsersCall = userApi.editMaterials(id,etMaterial.getText().toString(),etStock.getText().toString());

        UsersCall.enqueue(new Callback<Materials>() {
            @Override
            public void onResponse(Call<Materials> call, Response<Materials> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(EditMaterial.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(EditMaterial.this, "Material updated Successfully", Toast.LENGTH_SHORT).show();
                Intent openHome = new Intent(EditMaterial.this, materialsActivity.class);
                startActivity(openHome);
            }

            @Override
            public void onFailure(Call<Materials> call, Throwable t) {
                Toast.makeText(EditMaterial.this, "error message" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}