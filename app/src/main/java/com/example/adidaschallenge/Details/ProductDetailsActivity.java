package com.example.adidaschallenge.Details;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adidaschallenge.Adapters.ProductAdapter;
import com.example.adidaschallenge.Adapters.ReviewAdapter;
import com.example.adidaschallenge.Dialog;
import com.example.adidaschallenge.List.ProductActivity;
import com.example.adidaschallenge.Models.ProductComplete;
import com.example.adidaschallenge.Models.ProductReview;
import com.example.adidaschallenge.R;
import com.example.adidaschallenge.Utils.Constants;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductDetailsActivity extends AppCompatActivity {
    TextView productnametextview, descriptiontextview, pricetextview, currencytextview;
    Button back, addreview;
    ImageView imageView;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productnametextview = (TextView) findViewById(R.id.productname);
        descriptiontextview = (TextView) findViewById(R.id.description);
        pricetextview = (TextView) findViewById(R.id.price);
        currencytextview = (TextView) findViewById(R.id.currency);
        back = (Button) findViewById(R.id.back);
        addreview = (Button) findViewById(R.id.addreview);
        imageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String productname = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String price = String.valueOf(intent.getIntExtra("price", 0));
        String currency = intent.getStringExtra("currency");

        if (intent.getStringExtra("img").isEmpty()) {
            imageView.setImageResource(R.drawable.imagenotavailable);
        } else {
            Picasso.with(this.getBaseContext()).load(intent.getStringExtra("img")).into(imageView);
        }

        System.out.println(productname + description + price + currency);

        productnametextview.setText(productname);
        descriptiontextview.setText(description);
        pricetextview.setText(price);
        currencytextview.setText(currency);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProductActivity.class);
                startActivity(i);
            }
        });

        addreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(id);

            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize
                (true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadReviews(id);
    }

    public void openDialog(String id) {
        Dialog dialog = new Dialog(id);
        dialog.show(getSupportFragmentManager(), "Add A Review");
    }

    private void loadReviews(String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_ReviewList + id,
                response -> {
                    try {

                        JSONArray array = new JSONArray(response);
                        List<ProductReview> reviewList = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject review = array.getJSONObject(i);

                            reviewList.add(new ProductReview(
                                    review.getString("productId"),
                                    review.getString("locale"),
                                    review.getInt("rating"),
                                    review.getString("text")
                            ));
                        }

                        ReviewAdapter adapter = new ReviewAdapter(ProductDetailsActivity.this, reviewList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept-Language", "en-US");
                return params;
            }
        };

        stringRequest.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
        queue.add(stringRequest);
    }
}
