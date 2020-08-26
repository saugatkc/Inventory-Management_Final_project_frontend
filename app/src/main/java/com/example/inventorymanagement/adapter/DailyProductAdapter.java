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
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.activity.dailyProduct.dailyProductsActivity;
import com.example.inventorymanagement.activity.productOutgoing.productsOutgoingActivity;
import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.DailyProducts;
import com.example.inventorymanagement.models.Products;
import com.example.inventorymanagement.url.url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyProductAdapter extends RecyclerView.Adapter<DailyProductAdapter.dailyProductViewholder> {
private List<DailyProducts> dailyProductsList;
private Context mcontext;
private String id;


public DailyProductAdapter( Context mcontext, List<DailyProducts> dailyProductsList){

        this.dailyProductsList = dailyProductsList;
        this.mcontext=mcontext;

        }


@NonNull
@Override
public dailyProductViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.daily_product_adapter, parent, false);
        return new dailyProductViewholder(view);
        }

@Override
public void onBindViewHolder(@NonNull dailyProductViewholder holder, int i) {

final DailyProducts dailyProduct = dailyProductsList.get (i);
        holder.date.setText(dailyProduct.getCreatedAt());
        holder.product.setText(dailyProduct.product.getProduct());
        holder.quantity.setText(dailyProduct.getQuantity());
        holder.damaged.setText(dailyProduct.getDamaged());
        holder.remaining.setText(dailyProduct.getRemaining());

        id=dailyProduct.get_id();

    holder.delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            delete();
        }

    });

}


@Override
public int getItemCount() {
        return dailyProductsList.size();
        }

public class dailyProductViewholder extends RecyclerView.ViewHolder{

    TextView date,product,quantity,damaged,remaining;
//        ImageView imghotelimg, imghotel;
        Button delete;


    public dailyProductViewholder (@NonNull View itemView){
        super (itemView);

        date = itemView.findViewById(R.id.tvDate);
        product = itemView.findViewById(R.id.tvDailyProductName);
        quantity = itemView.findViewById(R.id.tvQuantity);
        damaged = itemView.findViewById(R.id.tvDamaged);
        remaining = itemView.findViewById(R.id.tvRemaining);
        delete = itemView.findViewById(R.id.btnDelete);

    }
}

    public void delete(){

        UsersAPI usersAPI = url.getInstance().create(UsersAPI.class);
        Call<Void> hotelCall = usersAPI.removeDailyProducts(id);

        hotelCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mcontext, "Errror", Toast.LENGTH_SHORT).show();
                    Intent notify = new Intent(mcontext, dailyProductsActivity.class);
                    mcontext.startActivity(notify);
                    return;
                }

                Toast.makeText(mcontext, "Removed", Toast.LENGTH_SHORT).show();
                Intent notify = new Intent(mcontext, dailyProductsActivity.class);
                mcontext.startActivity(notify);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


}