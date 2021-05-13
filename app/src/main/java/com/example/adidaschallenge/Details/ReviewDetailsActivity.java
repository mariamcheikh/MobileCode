package com.example.adidaschallenge.Details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.adidaschallenge.List.ProductActivity;
import com.example.adidaschallenge.Models.ProductReview;
import com.example.adidaschallenge.R;

import java.util.List;

public class ReviewDetailsActivity extends AppCompatActivity {
    TextView ratingtextview, texttetview;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);
        ratingtextview = (TextView) findViewById(R.id.ratingBar);
        texttetview = (TextView) findViewById(R.id.rating);
        back = (Button) findViewById(R.id.back);

        Intent intent = getIntent();

        String rating = String.valueOf(intent.getIntExtra("rating", 0));
        String text = intent.getStringExtra("text");
        System.out.println(rating + text);
        ratingtextview.setText(rating);
        texttetview.setText(text);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProductActivity.class);
                startActivity(i);
            }
        });
    }
}