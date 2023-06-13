package hanu.a2_2001040094.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "cart.db";
    public static final int DB_VERSION = 1;

    public DbHelper (@Nullable Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table cart
        String sql = "CREATE TABLE cart("+
                "id INTEGER PRIMARY KEY,"+
                "name TEXT NOT NULL,"+
                "imgURL TEXT NOT NULL,"+
                "category TEXT NOT NULL,"+
                "unitPrice INTEGER NOT NULL ,"+
                "quantity INTEGER NOT NULL"+ ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = " DROP TABLE IF EXISTS cart";
        db.execSQL(sql);
        onCreate(db);
    }
}
