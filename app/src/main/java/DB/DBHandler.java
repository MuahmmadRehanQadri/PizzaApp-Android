package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.Pizza;

/**
 * Created by Muhammad Rehan Qadri on 6/22/2015.
 */
public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pizza.db";
    private static final String SQL_CREATE_PIZZA = "CREATE TABLE PIZZA ( Name TEXT, Type TEXT, Price DOUBLE, Image BLOB )";
    private static final String SQL_CREATE_ORDER = "CREATE TABLE ORDER ( Name TEXT, Type TEXT, Quantity INTEGER, Price DOUBLE )";
    private static final String SQL_DELETE_PIZZA = "DROP TABLE IF EXISTS PIZZA";
    private static final String SQL_DELETE_ORDER = "DROP TABLE IF EXISTS ORDER";
    private String SQL_SELECT_PIZZA="SELECT * FROM PIZZA";


    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PIZZA);
    //    db.execSQL(SQL_CREATE_ORDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PIZZA);
        db.execSQL(SQL_DELETE_ORDER);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addNewPizza(String editTextName, String editTextType, String editTextPrice, byte[] bytes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Name", editTextName);
        contentValues.put("Type", editTextType);
        contentValues.put("Price", editTextPrice);

        contentValues.put("Image", bytes);
        db.insert("Pizza", null, contentValues);
        db.close();
    }

    public ArrayList<Pizza> getAllPizzas() {
        ArrayList<Pizza> pizzasList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQL_SELECT_PIZZA, null);
        //looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Pizza pizza = new Pizza(cursor.getString(0), cursor.getString(1), cursor.getDouble(2), cursor.getBlob(3));
                pizzasList.add(pizza);
            }while(cursor.moveToNext());
        }

        return pizzasList;
    }
}
