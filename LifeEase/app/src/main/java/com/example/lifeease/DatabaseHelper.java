package com.example.lifeease;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MEDICINE.DB";
    private static final int DATABASE_VERSION = 2;

    // Table names
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_CART = "cart";
    private static final String TABLE_ORDERS = "orders";

    // Columns for products table
    public static final String COL_ID = "id";
    public static final String COL_PRODUCT_NAME = "productName";
    public static final String COL_PRODUCT_PRICE = "productPrice";
    public static final String COL_PRODUCT_QUANTITY = "productQuantity";
    public static final String COL_PRODUCT_IMAGE_URI = "productImageUri"; // BLOB for image storage

    // Columns for cart table
    private static final String COL_USERNAME = "username";
    private static final String COL_PRODUCT = "product";
    private static final String COL_PRICE = "price";
    private static final String COL_MEDICINE = "medicine";
    private static final String COL_ORDER_USERNAME = "username";
    private static final String COL_TOTAL_MEDICINES = "total_medicines";
    private static final String COL_TOTAL_COST = "total_cost";
    private static final String COL_ORDER_DATE = "order_date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create products table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PRODUCT_NAME + " TEXT, " +
                COL_PRODUCT_PRICE + " REAL, " +
                COL_PRODUCT_QUANTITY + " INTEGER, " +
                COL_PRODUCT_IMAGE_URI + " BLOB)");

        // Create cart table
        db.execSQL("CREATE TABLE " + TABLE_CART + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_PRODUCT + " TEXT, " +
                COL_PRICE + " FLOAT, " +
                COL_MEDICINE + " TEXT)");

        // Create orders table
        db.execSQL("CREATE TABLE " + TABLE_ORDERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "total_medicines INTEGER, " +
                "total_cost REAL, " +
                "order_date TEXT)");

    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        // Recreate tables
        onCreate(db);
    }


    // Insert product
    public boolean insertProduct(String name, double price, int quantity, byte[] imageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, name);
        values.put(COL_PRODUCT_PRICE, price);
        values.put(COL_PRODUCT_QUANTITY, quantity);
        values.put(COL_PRODUCT_IMAGE_URI, imageByteArray);
        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close(); // Close the database
        return result != -1;
    }

    // Get all products (with alias for _id)
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT " + COL_ID + " AS _id, " +
                        COL_PRODUCT_NAME + ", " +
                        COL_PRODUCT_PRICE + ", " +
                        COL_PRODUCT_QUANTITY + ", " +
                        COL_PRODUCT_IMAGE_URI +
                        " FROM " + TABLE_PRODUCTS,
                null
        );
    }

    public Cursor getSearchProductByName(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT " + COL_ID + " AS _id, " +
                COL_PRODUCT_NAME + ", " +
                COL_PRODUCT_PRICE + ", " +
                COL_PRODUCT_QUANTITY + ", " +
                COL_PRODUCT_IMAGE_URI +
                " FROM " + TABLE_PRODUCTS +
                " WHERE " + COL_PRODUCT_NAME + " LIKE ?";
        return db.rawQuery(sql, new String[]{"%" + query + "%"});
    }
    // Get product by name
    public Cursor getProductByName(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COL_PRODUCT_NAME + " = ?",
                new String[]{productName}
        );
        return cursor;
    }

    // Update product
    public boolean updateProduct(int id, String name, double price, int quantity, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, name);
        values.put(COL_PRODUCT_PRICE, price);
        values.put(COL_PRODUCT_QUANTITY, quantity);
        values.put(COL_PRODUCT_IMAGE_URI, image);

        int rowsUpdated = db.update(TABLE_PRODUCTS, values, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsUpdated > 0;
    }

    // Delete product
    public boolean deleteProduct(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_PRODUCTS, COL_PRODUCT_NAME + " = ?", new String[]{productName});
        db.close();
        return rowsAffected > 0;
    }

    // Add to cart
    public void addCart(String username, String product, float price, int medicine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USERNAME, username);
        cv.put(COL_PRODUCT, product);
        cv.put(COL_PRICE, price);
        cv.put(COL_MEDICINE, medicine);

        // Use insertWithOnConflict to prevent duplicate entries based on product and username
        long result = db.insertWithOnConflict(TABLE_CART, null, cv, SQLiteDatabase.CONFLICT_IGNORE);

        if (result == -1) {
            // Error handling in case insertion fails
            Log.e("DB_ERROR", "Failed to add product to cart.");
        } else {
            Log.d("DB_SUCCESS", "Product added to cart successfully.");
        }

        db.close();
    }

    // Check if product is already in cart
    public boolean checkCart(String username, String product) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(
                TABLE_CART,
                new String[]{COL_USERNAME, COL_PRODUCT},
                COL_USERNAME + " = ? AND " + COL_PRODUCT + " = ?",
                new String[]{username, product},
                null, null, null
        );

        boolean exists = c.moveToFirst();
        c.close();
        db.close();

        return exists;
    }

    // Get cart items for a specific user
    public List<CartItem> getCartItems(String username) {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_CART,
                new String[]{COL_PRODUCT, COL_MEDICINE, COL_PRICE}, // Ensure correct column names are used
                COL_USERNAME + " = ?",
                new String[]{username},
                null, null, null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String productName = cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT));
                String medicine = cursor.getString(cursor.getColumnIndexOrThrow(COL_MEDICINE)); // Medicine column
                float price = cursor.getFloat(cursor.getColumnIndexOrThrow(COL_PRICE));
                cartItems.add(new CartItem(productName, medicine, price)); // Adapt constructor for CartItem
            }
            cursor.close();
        }
        db.close(); // Close the database connection
        return cartItems;
    }

    // Delete a cart item for a specific user
    public void deleteCartItem(String username, String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, COL_USERNAME + " = ? AND " + COL_PRODUCT + " = ?", new String[]{username, productName});
        db.close(); // Close the database connection
    }

    // Insert a new order into the orders table
    public boolean insertOrder(String username, int totalMedicines, float totalCost, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ORDER_USERNAME, username);
        values.put(COL_TOTAL_MEDICINES, totalMedicines);
        values.put(COL_TOTAL_COST, totalCost);
        values.put(COL_ORDER_DATE, date);

        long result = db.insert(TABLE_ORDERS, null, values);

        if (result == -1) {
            Log.e("DB_ERROR", "Failed to insert order for user: " + username);
            db.close();
            return false;
        } else {
            Log.d("DB_SUCCESS", "Order inserted successfully for user: " + username);
            db.close();
            return true;
        }
    }


    public Cursor getOrders(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS + " WHERE " + COL_ORDER_USERNAME + " = ?",
                new String[]{username});
    }




    // Clear all cart items for a specific user
    public void clearCart(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart", "username = ?", new String[]{username});
        db.close();
    }


}
