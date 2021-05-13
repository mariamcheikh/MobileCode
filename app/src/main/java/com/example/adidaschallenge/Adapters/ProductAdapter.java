package com.example.adidaschallenge.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adidaschallenge.Details.ProductDetailsActivity;
import com.example.adidaschallenge.Models.ProductComplete;
import com.example.adidaschallenge.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<ProductComplete> ProductList;

    public ProductAdapter(Context mCtx, List<ProductComplete> ProductList) {
        this.mCtx = mCtx;
        this.ProductList = ProductList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list_row, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ProductComplete product = ProductList.get(position);

        holder.productname.setText(product.getName());
        holder.description.setText(product.getDescription());
        holder.price.setText(product.getPrice() + "");
        holder.currency.setText(product.getCurrency());

        if (product.getImg().isEmpty()) {
            holder.imageproduct.setImageResource(R.drawable.imagenotavailable);
        } else {
            Picasso.with(mCtx).load(product.getImg()).into(holder.imageproduct);
        }
        holder.displayproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mCtx, ProductDetailsActivity.class);

                myIntent.putExtra("id", product.getId());
                myIntent.putExtra("name", product.getName());
                myIntent.putExtra("description", product.getDescription());
                myIntent.putExtra("price", product.getPrice());
                myIntent.putExtra("currency", product.getCurrency());
                myIntent.putExtra("img", product.getImg());
                mCtx.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ProductList.size();
    }

    public void filterList(ArrayList<ProductComplete> filteredList) {
        ProductList = filteredList;
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        LinearLayout displayproduct;
        TextView productname, description, price, currency;
        ImageView imageproduct;


        public ProductViewHolder(View itemView) {
            super(itemView);

            productname = itemView.findViewById(R.id.productname);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            currency = itemView.findViewById(R.id.currency);
            displayproduct = itemView.findViewById(R.id.displayproduct);
            imageproduct = itemView.findViewById(R.id.imageproduct);


        }
    }


}
