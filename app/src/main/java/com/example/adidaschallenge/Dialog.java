package com.example.adidaschallenge;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.adidaschallenge.Details.ProductDetailsActivity;
import com.example.adidaschallenge.List.ProductActivity;
import com.example.adidaschallenge.Utils.Constants;
import com.example.adidaschallenge.Utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class Dialog extends AppCompatDialogFragment {

    private EditText text = null;
    private RatingBar rating;
    private Button submitreview;
    private String id;

    public Dialog(String id) {
        this.id = id;
    }

    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view).setTitle("Add A Product Review").setMessage("This helps us to continue providing great products and helps potential buyers to make confident decisions.")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });


        text = view.findViewById(R.id.addtext);
        rating = view.findViewById(R.id.ratingBar);
        submitreview = view.findViewById(R.id.submitreview);
        submitreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addreview(id);
            }
        });
        return builder.create();
    }

    private void addreview(String id) {

        final String tRating = text.getText().toString().trim();
        int s = (int) rating.getRating();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("rating", s + "");
            jsonBody.put("text", tRating);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        final String requestBody = jsonBody.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_ADD_REVIEW + id,
                response -> {
                    Toast.makeText(this.getContext(), "Success: " + id, Toast.LENGTH_LONG).show();
                }, error -> {
            Toast.makeText(this.getContext(), "Error==> " + error.getMessage(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept-Language", "en-US");
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestHandler.getInstance(this.getContext()).addToRequestQueue(stringRequest);
    }

}
