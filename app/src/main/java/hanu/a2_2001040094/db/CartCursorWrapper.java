package hanu.a2_2001040094.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_2001040094.models.Product;

public class CartCursorWrapper extends CursorWrapper {
    public CartCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Product getProduct(){
        Cursor cursor = getWrappedCursor();
        int idc = cursor.getColumnIndex("id");
        int namec = cursor.getColumnIndex("name");
        int imgURLc = cursor.getColumnIndex("imgURL");
        int categoryc = cursor.getColumnIndex("category");
        int unitPricec = cursor.getColumnIndex("unitPrice");
        int quantityc = cursor.getColumnIndex("quantity");


        cursor.moveToNext();

            int id = cursor.getInt(idc);
            String name = cursor.getString(namec);
            String url = cursor.getString(imgURLc);
            String category = cursor.getString(categoryc);
            int unitPrice = cursor.getInt(unitPricec);
            int quantity = cursor.getInt(quantityc);
            Product product = new Product(id, name, url, category, unitPrice, quantity);
            return product;
    }

    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        Cursor cursor = getWrappedCursor();
        int idc = cursor.getColumnIndex("id");
        int namec = cursor.getColumnIndex("name");
        int imgURLc = cursor.getColumnIndex("imgURL");
        int categoryc = cursor.getColumnIndex("category");
        int unitPricec = cursor.getColumnIndex("unitPrice");
        int quantityc = cursor.getColumnIndex("quantity");

        while (cursor.moveToNext()){
            int id = cursor.getInt(idc);
            String name = cursor.getString(namec);
            String url = cursor.getString(imgURLc);
            String category = cursor.getString(categoryc);
            int unitPrice = cursor.getInt(unitPricec);
            int quantity = cursor.getInt(quantityc);
            Product product = new Product(id, name, url, category, unitPrice, quantity);
            products.add(product);

        }
        return products;
    }
}
