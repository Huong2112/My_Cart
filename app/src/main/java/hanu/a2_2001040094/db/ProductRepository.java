package hanu.a2_2001040094.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;
import hanu.a2_2001040094.models.Product;

public class ProductRepository {

    private static ProductRepository instance;

    private DbHelper dbHelper;
    private Context context;
    SQLiteDatabase db;

    public static ProductRepository getInstance(Context context){
        if (instance == null){
            instance = new ProductRepository(context);
        }
        return instance;
    }

    public ProductRepository(Context context) {
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
    }

    // get all product from cart db
    public List<Product> all(){
        // load db
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getReadableDatabase();

        String sql = " SELECT * FROM cart";
        Cursor cursor = db.rawQuery(sql, null);

        CartCursorWrapper cartCursorWrapper = new CartCursorWrapper(cursor);
        List<Product> products = cartCursorWrapper.getProducts();
        cursor.close();
        return products;
    }

    public void removeProduct(int id){
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("DELETE FROM cart WHERE id = ? ");
        statement.bindLong(1,id);
        statement.executeUpdateDelete();
        statement.close();

    }

    public void editProduct(int id, int quantity){
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("UPDATE cart SET quantity = ? WHERE id = ? ");
        statement.bindLong(1, quantity);
        statement.bindLong(2, id);
        statement.executeUpdateDelete();
        statement.close();

    }

    public void addProduct(int id, String name, String url, String category, int unitPrice, int quantity){
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement(  "INSERT INTO cart VALUES (?,?,?,?,?,?)");
        statement.bindLong(1, id);
        statement.bindString(2,name);
        statement.bindString(3,url);
        statement.bindString(4,category);
        statement.bindLong(5, unitPrice);
        statement.bindLong(6,quantity);
        statement.executeInsert();

    }

    public List<Product> getProductByID(int id){
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM cart WHERE id =" + id;

        Cursor cursor = db.rawQuery(sql, null);

        CartCursorWrapper cartCursorWrapper = new CartCursorWrapper(cursor);
            List<Product> products = cartCursorWrapper.getProducts();
            cursor.close();
            return products;
    }

    public int getQuantInDb(int id){
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getReadableDatabase();
        String sql = " SELECT * FROM cart WHERE id = " + String.valueOf(id);

        Cursor cursor = db.rawQuery(sql, null);

        CartCursorWrapper cartCursorWrapper = new CartCursorWrapper(cursor);
        //get all product in db
        Product product = cartCursorWrapper.getProduct();
        cursor.close();
        int quant = product.getQuantity();
        return quant;
    }

    @SuppressLint("Range")
    public int getTotalAmount(){
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getReadableDatabase();
        String sql = "SELECT SUM(unitPrice * quantity) AS total\n" +
                "FROM cart";

        Cursor cursor = db.rawQuery(sql, null);

        int total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("total"));
        }
        cursor.close();
        db.close();
        return total;
    }
}
