package com.example.inventorymanagement.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.activity.dailyProduct.AddDailyProduct;
import com.example.inventorymanagement.activity.dailyProduct.dailyProductsActivity;
import com.example.inventorymanagement.activity.material.EditMaterial;
import com.example.inventorymanagement.activity.product.AddProduct;
import com.example.inventorymanagement.activity.product.EditProduct;
import com.example.inventorymanagement.activity.productOutgoing.AddProductOutgoing;
import com.example.inventorymanagement.models.Products;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.productViewholder> {
    private List<Products> bookingList;
    private Context mcontext;
    private String id;


    public ProductAdapter( Context mcontext, List<Products> bookingList){

        this.bookingList = bookingList;
        this.mcontext=mcontext;

    }


    @NonNull
    @Override
    public productViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_adapter, parent, false);
        return new productViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productViewholder holder, int i) {

        final Products products = bookingList.get (i);
        holder.tvProduct.setText(products.getProduct());
        holder.tvCost.setText(products.getCost());
        holder.tvStock.setText(products.getStock());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent notify = new Intent(mcontext, EditProduct.class);
                notify.putExtra("id", products.get_id());
                notify.putExtra("product", products.getProduct());
                notify.putExtra("cost", products.getCost());
                notify.putExtra("description", products.getDescription());
                notify.putExtra("stock", products.getStock());
                mcontext.startActivity(notify);

            }

        });
        holder.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent notify = new Intent(mcontext, AddProductOutgoing.class);
                notify.putExtra("id", products.get_id());
                notify.putExtra("product", products.getProduct());
                notify.putExtra("cost", products.getCost());
                notify.putExtra("description", products.getDescription());
                notify.putExtra("stock", products.getStock());
                mcontext.startActivity(notify);

            }

        });

        holder.btnDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent notify = new Intent(mcontext, AddDailyProduct.class);
                notify.putExtra("id", products.get_id());
                notify.putExtra("product", products.getProduct());
                notify.putExtra("cost", products.getCost());
                notify.putExtra("description", products.getDescription());
                notify.putExtra("stock", products.getStock());
                mcontext.startActivity(notify);

            }

        });




    }


    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class productViewholder extends RecyclerView.ViewHolder{

        TextView tvProduct, tvCost, tvStock;
//        ImageView imghotelimg, imghotel;
        Button btnEdit, btnOrder, btnDP;


        public productViewholder (@NonNull View itemView){
            super (itemView);

            tvProduct = itemView.findViewById(R.id.tvProductName);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvStock = itemView.findViewById(R.id.tvStock);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnOrder= itemView.findViewById(R.id.btnOrder);
            btnDP= itemView.findViewById(R.id.btnDP);


        }
    }



}