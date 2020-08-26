package com.example.inventorymanagement.activity.materialIncoming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.activity.dailyProduct.AddDailyProduct;
import com.example.inventorymanagement.activity.dailyProduct.dailyProductsActivity;
import com.example.inventorymanagement.activity.material.materialsActivity;
import com.example.inventorymanagement.activity.product.productsActivity;
import com.example.inventorymanagement.activity.productOutgoing.productsOutgoingActivity;
import com.example.inventorymanagement.activity.user.LoginActivity;
import com.example.inventorymanagement.activity.user.dasboardActivity;
import com.example.inventorymanagement.adapter.MaterialAdapter;
import com.example.inventorymanagement.adapter.MaterialIncomingAdapter;
import com.example.inventorymanagement.adapter.ProductAdapter;
import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.Materials;
import com.example.inventorymanagement.models.MaterialsIncoming;
import com.example.inventorymanagement.models.Products;
import com.example.inventorymanagement.url.url;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class materialsIncomingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private TextView tvPage;
    private ImageView add;

    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;
    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


        @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dataplace2);
        tvPage = findViewById(R.id.tvPage);
        recyclerView = findViewById(R.id.users_list);
        tvPage.setText("Materials Incoming");

//            add = findViewById(R.id.imgAdd);
//            add.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    Intent add = new Intent(materialsIncomingActivity.this, AddMaterialIncoming.class);
//                    startActivity(add);
//                }
//            });

        laodMaterialsIncoming();

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        navigationDrawer();
    }

    private void laodMaterialsIncoming() {

        UsersAPI usersAPI = url.getInstance().create(UsersAPI.class);
        Call<List<MaterialsIncoming>> materialsIncomingCALL = usersAPI.getMaterialsIncoming();

        materialsIncomingCALL.enqueue(new Callback<List<MaterialsIncoming>>() {
            @Override
            public void onResponse(Call<List<MaterialsIncoming>> call, Response<List<MaterialsIncoming>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Errror", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<MaterialsIncoming> materialsIncomingList = response.body();
                for (MaterialsIncoming materialsIncoming : materialsIncomingList){
                    MaterialIncomingAdapter ha = new MaterialIncomingAdapter(getApplicationContext(), materialsIncomingList);
                    recyclerView.setAdapter(ha);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                }

            }

            @Override
            public void onFailure(Call<List<MaterialsIncoming>> call, Throwable t) {

            }
        });

    }



    //Navigation drawer function

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
           navigationView.setCheckedItem(R.id.materialIncoming);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);

                else drawerLayout.openDrawer(GravityCompat.START);

            }
        });
        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

// Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

// Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(materialsIncomingActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to exit from application
                        SharedPreferences sharedPreferences = materialsIncomingActivity.this.getSharedPreferences("IMS", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("token");
                        editor.remove("isadmin");
                        editor.remove("status");
                        editor.remove("username");
                        editor.remove("password");
                        editor.commit();
                        url.token = "Bearer ";
                        url.status = "Status";
                        Intent i = new Intent(materialsIncomingActivity.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                break;

            case R.id.home:
                startActivity(new Intent(getApplicationContext(), dasboardActivity.class));

                break;

            case R.id.product:
                startActivity(new Intent(getApplicationContext(), productsActivity.class));

                break;

            case R.id.material:
                startActivity(new Intent(getApplicationContext(), materialsActivity.class));

                break;

            case R.id.dailyProduct:
                startActivity(new Intent(getApplicationContext(), dailyProductsActivity.class));

                break;
            case R.id.productOutgoing:
                startActivity(new Intent(getApplicationContext(), productsOutgoingActivity.class));

                break;

            case R.id.materialIncoming:
                startActivity(new Intent(getApplicationContext(), materialsIncomingActivity.class));

                break;


        }
        return true;
    }

}