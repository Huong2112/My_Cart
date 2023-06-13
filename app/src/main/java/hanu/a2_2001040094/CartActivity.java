package hanu.a2_2001040094;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_2001040094.adapters.ProductInCartAdapter;
import hanu.a2_2001040094.db.ProductRepository;
import hanu.a2_2001040094.interfaces.CalculatSum;
import hanu.a2_2001040094.models.Product;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvCard;
    private ProductInCartAdapter cartAdapter;

    private ProductRepository productRepository;

    private List<Product> products;
    public TextView amount;

    int total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        productRepository = ProductRepository.getInstance(getApplicationContext());
        products = productRepository.all();
        rvCard = findViewById(R.id.rvcart);
        rvCard.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new ProductInCartAdapter(this, new CalculatSum() {
            @Override
            public void OnClick(int amount) {
                TextView textView = findViewById(R.id.sumhe);
                setTotal(String.valueOf(amount), textView);
            }
        });
        cartAdapter.setData(products);
        rvCard.setAdapter(cartAdapter);

        amount = findViewById(R.id.sumhe);
        setTotal(String.valueOf(productRepository.getTotalAmount()), amount);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(getApplicationContext());
        menuInflater.inflate(R.menu.menu_cart_icon, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void setTotal(String amount, TextView text) {
        String amountpaid = "đ" + amount;
        text.setText(amountpaid);
    }

}