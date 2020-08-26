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

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.activity.material.EditMaterial;
import com.example.inventorymanagement.activity.materialIncoming.AddMaterialIncoming;
import com.example.inventorymanagement.models.Materials;
import com.example.inventorymanagement.models.Products;

import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.materialViewholder> {
private List<Materials> materialsList;
private Context mcontext;
private String id;


public MaterialAdapter(Context mcontext, List<Materials> materialsList){

        this.materialsList = materialsList;
        this.mcontext=mcontext;

        }


@NonNull
@Override
public materialViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.material_adapter, parent, false);
        return new materialViewholder(view);
        }

@Override
public void onBindViewHolder(@NonNull materialViewholder holder, int i) {

final Materials material = materialsList.get (i);
        holder.tvMaterial.setText(material.getMaterial());
        holder.tvStock.setText(material.getStock());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent notify = new Intent(mcontext, EditMaterial.class);
            notify.putExtra("id", material.get_id());
            notify.putExtra("material", material.getMaterial());
            notify.putExtra("stock", material.getStock());
            mcontext.startActivity(notify);

              }

         });

    holder.btnOrder.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent notify = new Intent(mcontext, AddMaterialIncoming.class);
            notify.putExtra("id", material.get_id());
            notify.putExtra("material", material.getMaterial());
            notify.putExtra("stock", material.getStock());
            mcontext.startActivity(notify);

        }

    });


    }


@Override
public int getItemCount() {
        return materialsList.size();
        }

public class materialViewholder extends RecyclerView.ViewHolder{

    TextView tvMaterial, tvStock;
//        ImageView imghotelimg, imghotel;
        Button btnEdit,btnOrder;


    public materialViewholder (@NonNull View itemView){
        super (itemView);

        tvMaterial = itemView.findViewById(R.id.tvMaterialName);
        tvStock = itemView.findViewById(R.id.tvStock);
        btnEdit = itemView.findViewById(R.id.btnEdit);
        btnOrder = itemView.findViewById(R.id.btnOrder);
    }
}



}