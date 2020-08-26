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
import com.example.inventorymanagement.activity.material.stockChange;
import com.example.inventorymanagement.activity.product.stockChangeProduct;
import com.example.inventorymanagement.activity.productOutgoing.AddProductOutgoing;
import com.example.inventorymanagement.activity.productOutgoing.productsOutgoingActivity;
import com.example.inventorymanagement.api.UsersAPI;
import com.example.inventorymanagement.models.MaterialsIncoming;
import com.example.inventorymanagement.models.Products;
import com.example.inventorymanagement.models.ProductsOutgoing;
import com.example.inventorymanagement.url.url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductOutgoingAdapter extends RecyclerView.Adapter<ProductOutgoingAdapter.productOutgoingViewholder> {
    private List<ProductsOutgoing> productsOutgoingList;
    private Context mcontext;
    public String id,stock,quantity ,PO_id;


    public ProductOutgoingAdapter( Context mcontext, List<ProductsOutgoing> productsOutgoingList){

        this.productsOutgoingList = productsOutgoingList;
        this.mcontext=mcontext;

    }


    @NonNull
    @Override
    public ProductOutgoingAdapter.productOutgoingViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_outgoing_adapter, parent, false);
        return new productOutgoingViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOutgoingAdapter.productOutgoingViewholder holder, int i) {

        final ProductsOutgoing productsOutgoing = productsOutgoingList.get (i);
        holder.date.setText(productsOutgoing.getCreatedAt());
        holder.product.setText(productsOutgoing.product.getProduct());
        holder.customer.setText(productsOutgoing.getCustomer());
        holder.address.setText(productsOutgoing.getAddress());
        holder.phone.setText(productsOutgoing.getPhone());
        holder.quantity.setText(productsOutgoing.getQuantity());
        holder.totalCost.setText(productsOutgoing.getTotalCost());

        id=productsOutgoing.product.get_id();
        stock=productsOutgoing.product.getStock();
        PO_id=productsOutgoing.get_id();

        if (!productsOutgoing.getDispatched()){
            holder.dispatched.setVisibility(View.VISIBLE);
        }
        holder.dispatched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notify = new Intent(mcontext, stockChangeProduct.class);
                notify.putExtra("ProductId", productsOutgoing.product.get_id());
                notify.putExtra("id", productsOutgoing.get_id());
                notify.putExtra("stock", productsOutgoing.product.getStock());
                notify.putExtra("quantity", productsOutgoing.getQuantity());
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
        return productsOutgoingList.size();
    }

    public class productOutgoingViewholder extends RecyclerView.ViewHolder{

        TextView date, product, customer, phone, address, quantity,totalCost;
        //        ImageView imghotelimg, imghotel;
        Button dispatched, delete;


        public productOutgoingViewholder (@NonNull View itemView){
            super (itemView);
            date = itemView.findViewById(R.id.tvDate);
            product = itemView.findViewById(R.id.tvProductOutgoingName);
            customer = itemView.findViewById(R.id.tvCustomer);
            phone = itemView.findViewById(R.id.tvPhone);
            address= itemView.findViewById(R.id.tvAddress);
            quantity = itemView.findViewById(R.id.tvQuantity);
            totalCost = itemView.findViewById(R.id.tvTotalCost);
            dispatched = itemView.findViewById(R.id.btnDispatched);
            delete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public void delete(){
        String PO= PO_id;

        UsersAPI usersAPI = url.getInstance().create(UsersAPI.class);
        Call<Void> hotelCall = usersAPI.removeProductsOutgoing(PO);

        hotelCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mcontext, "Errror", Toast.LENGTH_SHORT).show();
                    Intent notify = new Intent(mcontext, productsOutgoingActivity.class);
                    mcontext.startActivity(notify);
                    return;
                }

                Toast.makeText(mcontext, "Removed", Toast.LENGTH_SHORT).show();
                Intent notify = new Intent(mcontext, productsOutgoingActivity.class);
                mcontext.startActivity(notify);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


}