package hanu.a2_2001040094;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import hanu.a2_2001040094.adapters.ProductsAdapter;
import hanu.a2_2001040094.fragment.Constants;
import hanu.a2_2001040094.models.Product;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvProduct;
    private ProductsAdapter productsAdapter;
    private EditText searchText;
    private ImageButton searchBtn;
    private List<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
        Constants.executor.execute(new Runnable() {
            @Override
            public void run() {
                String json = loadJSON("https://hanu-congnv.github.io/mpr-cart-api/products.json");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (json == null) {
                            return;
                        }
                        try {
                            JSONArray arrayProduct = new JSONArray(json);

                            for (int i = 0; i < arrayProduct.length(); i++) {
                                JSONObject productObject = arrayProduct.getJSONObject(i);
                                int id = productObject.getInt("id");
                                String thumbnail = productObject.getString("thumbnail");
                                String name = productObject.getString("name");
                                String category = productObject.getString("category");
                                int unitPrice = productObject.getInt("unitPrice");
                                Product product = new Product(id, name, thumbnail, category, unitPrice);
                                products.add(product);
                            }
                            rvProduct = findViewById(R.id.rvProduct);
                            productsAdapter = new ProductsAdapter(MainActivity.this);
                            productsAdapter.setData(products);
                            rvProduct.setAdapter(productsAdapter);

                            List<Product> list = products;
                            searchText = findViewById(R.id.sedit);
                            searchBtn = findViewById(R.id.searchbtn);
                            searchBtn.setOnClickListener(new View.OnClickListener() {
                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void onClick(View v) {
                                    String keyword = searchText.getText().toString();
                                    searchProduct(keyword, list);
                                }
                            });
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });

    }

    public void searchProduct(String keyword, List<Product> products) {
        List<Product> searchedProduct = new ArrayList<>();
        String[] keys = keyword.split(" ");
        for (int i = 0; i < keys.length; i++) {
            for (int j = 0; j < products.size(); j++) {
                if (products.get(j).getName().toLowerCase().contains(keys[i].toLowerCase())) {
                    if (!searchedProduct.contains(products.get(j))) {
                        searchedProduct.add(products.get(j));
                    }
                }

            }
        }
        productsAdapter.setData(searchedProduct);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(getApplicationContext());
        menuInflater.inflate(R.menu.menu_cart_icon, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cartmenu) {
            Intent myIntent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(myIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private String loadJSON(String link) {
        try {
            URL url = new URL(link);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.connect();

            Scanner sc = new Scanner(urlConnection.getInputStream());
            StringBuilder result = new StringBuilder();
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean doubleBackToExitPressedOnce = false;
    private long lastClickTime = 0;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        productsAdapter.setData(products);
        Toast.makeText(this, "Touch to back one more time to close!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}

