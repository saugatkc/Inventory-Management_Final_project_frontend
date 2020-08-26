package com.example.inventorymanagement.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.inventorymanagement.activity.material.EditMaterial;
import com.example.inventorymanagement.activity.material.materialsActivity;
import com.example.inventorymanagement.activity.material.stockChange;
import com.example.inventorymanagement.activity.materialIncoming.materialsIncomingActivity;
import com.example.inventorymanagement.activity.product.EditProduct;
import com.example.inventorymanagement.activity.productOutgoing.productsOutgoingActivity;
import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.Materials;
import com.example.inventorymanagement.models.MaterialsIncoming;
import com.example.inventorymanagement.models.Products;
import com.example.inventorymanagement.url.url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaterialIncomingAdapter extends RecyclerView.Adapter<MaterialIncomingAdapter.materialIncomingViewholder> {
    private List<MaterialsIncoming> materialsIncomingList;
    private Context mcontext;
    public String id,stock,quantity ,MI_id;


    public MaterialIncomingAdapter( Context mcontext, List<MaterialsIncoming> materialsIncomingList){

        this.materialsIncomingList = materialsIncomingList;
        this.mcontext=mcontext;

    }


    @NonNull
    @Override
    public MaterialIncomingAdapter.materialIncomingViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.material_incoming_adapter, parent, false);
        return new MaterialIncomingAdapter.materialIncomingViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialIncomingAdapter.materialIncomingViewholder holder, int i) {

        final MaterialsIncoming materialsIncoming = materialsIncomingList.get (i);
        holder.date.setText(materialsIncoming.getCreatedAt());
        holder.material.setText(materialsIncoming.material.getMaterial());
        holder.supplier.setText(materialsIncoming.getSupplier());
        holder.phone.setText(materialsIncoming.getPhone());
        holder.quantity.setText(materialsIncoming.getQuantity());
        holder.totalCost.setText(materialsIncoming.getTotalCost());

        id=materialsIncoming.material.get_id();
        stock=materialsIncoming.material.getStock();
        MI_id=materialsIncoming.get_id();

        if (!materialsIncoming.getDelivered()){
            holder.delivered.setVisibility(View.VISIBLE);
        }
        holder.delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notify = new Intent(mcontext, stockChange.class);
                notify.putExtra("MaterialId", materialsIncoming.material.get_id());
                notify.putExtra("id", materialsIncoming.get_id());
                notify.putExtra("stock", materialsIncoming.material.getStock());
                notify.putExtra("quantity", materialsIncoming.getQuantity());
                mcontext.startActivity(notify);


            }

        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }

        });

    }




    @Override
    public int getItemCount() {
        return materialsIncomingList.size();
    }

    public class materialIncomingViewholder extends RecyclerView.ViewHolder{

        TextView date, material, supplier, phone, quantity,totalCost;
//        ImageView imghotelimg, imghotel;
        Button delivered, delete;


        public materialIncomingViewholder (@NonNull View itemView){
            super (itemView);
            date = itemView.findViewById(R.id.tvDate);
            material = itemView.findViewById(R.id.tvMaterialIncomingName);
            supplier = itemView.findViewById(R.id.tvSupplier);
            phone = itemView.findViewById(R.id.tvPhone);
            quantity = itemView.findViewById(R.id.tvQuantity);
            totalCost = itemView.findViewById(R.id.tvTotalCost);
            delivered = itemView.findViewById(R.id.btnDelivered);
            delete = itemView.findViewById(R.id.btnDelete);

        }
    }


    public void delete(){

        UsersAPI usersAPI = url.getInstance().create(UsersAPI.class);
        Call<Void> hotelCall = usersAPI.removeMaterialsIncoming(MI_id);

        hotelCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mcontext, "Errror", Toast.LENGTH_SHORT).show();
                    Intent notify = new Intent(mcontext, materialsIncomingActivity.class);
                    mcontext.startActivity(notify);
                    return;
                }

                Toast.makeText(mcontext, "Removed", Toast.LENGTH_SHORT).show();
                Intent notify = new Intent(mcontext, materialsIncomingActivity.class);
                mcontext.startActivity(notify);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

}