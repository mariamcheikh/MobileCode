package com.example.adidaschallenge.Adapters;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adidaschallenge.Details.ReviewDetailsActivity;
import com.example.adidaschallenge.Models.ProductReview;
import com.example.adidaschallenge.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context mCtx;
    private List<ProductReview> ReviewList;

    public ReviewAdapter(Context mCtx, List<ProductReview> ReviewList) {
        this.mCtx = mCtx;
        this.ReviewList = ReviewList;
    }

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list_review_row, null);
        return new ReviewAdapter.ReviewViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder holder, int position) {
        ProductReview review = ReviewList.get(position);

        holder.rating.setText(review.getRating() + "");
        holder.text.setText(review.getText());

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mCtx, ReviewDetailsActivity.class);

                myIntent.putExtra("rating", review.getRating());
                myIntent.putExtra("text", review.getText());
                mCtx.startActivity(myIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return ReviewList.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView rating, text;
        CardView cardview;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            rating = itemView.findViewById(R.id.rating);
            text = itemView.findViewById(R.id.text);
            cardview = itemView.findViewById(R.id.cardview);

        }
    }
}
