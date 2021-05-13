package com.example.adidaschallenge.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adidaschallenge.Adapters.ProductAdapter;
import com.example.adidaschallenge.Models.ProductComplete;
import com.example.adidaschallenge.R;
import com.example.adidaschallenge.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {

    List<ProductComplete> productList;
    RecyclerView recyclerView;
    Map<String, String> imagesMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        get_JSON();


        recyclerView = (RecyclerView) findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();

        loadProducts();
        //Search
        EditText search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }

        });

    }


    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_ProductList,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            String id = product.getString("id");
                            String img = "";
                            if (imagesMap.containsKey(id))
                                img = imagesMap.get(id);
                            productList.add(new ProductComplete(
                                    product.getString("id"),
                                    product.getString("name"),
                                    product.getString("description"),
                                    product.getString("currency"),
                                    product.getInt("price"),
                                    img
                            ));
                        }
                        ProductAdapter adapter = new ProductAdapter(ProductActivity.this, productList);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                });
        stringRequest.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
        queue.add(stringRequest);
    }

    //Filter
    private void filter(String text) {
        ArrayList<ProductComplete> filteredList = new ArrayList<>();
        for (ProductComplete product : productList) {
            if (product.getName().toLowerCase().contains(text.toLowerCase()) || product.getDescription().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(product);
                ProductAdapter adapter = new ProductAdapter(ProductActivity.this, productList);
                recyclerView.setAdapter(adapter);
                adapter.filterList(filteredList);

            }
        }
    }

    //Parse Json for *Image Part*
    public void get_JSON() {
        String json;
        try {
            InputStream inputStream = getAssets().open("products.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                imagesMap.put(object.getString("id"), object.getString("img"));
            }
            Toast.makeText(this, imagesMap.toString(), Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}