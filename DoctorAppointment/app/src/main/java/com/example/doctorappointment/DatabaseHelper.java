package com.example.doctorappointment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DOCTOR_APPOINTMENT.DB";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_DOCTORS = "doctors";
    private static final String TABLE_BOOKINGS = "bookings";

    // Columns for doctors table
    public static final String COL_DOCTOR_ID = "id";
    public static final String COL_DOCTOR_NAME = "name";
    public static final String COL_SPECIALIZATION = "specialization";
    public static final String COL_PHONE = "phone";
    public static final String COL_FEE = "fee";
    public static final String COL_VISITING_TIME = "visiting_time";
    public static final String COL_DOCTOR_IMAGE_URI = "productImageUri";
    // Columns for bookings table
    public static final String COL_BOOKING_ID = "id";
    public static final String COL_PATIENT_NAME = "patient_name";
    public static final String COL_DOCTOR_BOOKED = "doctor_name";
    public static final String COL_DATE = "appointment_date";
    public static final String COL_TIME = "appointment_time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create doctors table
        db.execSQL("CREATE TABLE " + TABLE_DOCTORS + " (" +
                COL_DOCTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DOCTOR_NAME + " TEXT, " +
                COL_SPECIALIZATION + " TEXT, " +
                COL_PHONE + " TEXT, " +
                COL_FEE + " REAL, " +
                COL_VISITING_TIME + " TEXT, " +
                COL_DOCTOR_IMAGE_URI + " BLOB)");


        // Create bookings table
        db.execSQL("CREATE TABLE " + TABLE_BOOKINGS + " (" +
                COL_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PATIENT_NAME + " TEXT, " +
                COL_DOCTOR_BOOKED + " TEXT, " +
                COL_DATE + " TEXT, " +
                COL_TIME + " TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
    }

    // Insert a new doctor
    public boolean insertDoctor(String name, String specialization, String phone, double fee, String visitingTime, byte[] imageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DOCTOR_NAME, name);
        values.put(COL_SPECIALIZATION, specialization);
        values.put(COL_PHONE, phone);
        values.put(COL_FEE, fee);
        values.put(COL_VISITING_TIME, visitingTime);
        values.put(COL_DOCTOR_IMAGE_URI, imageByteArray);

        long result = db.insert(TABLE_DOCTORS, null, values);
        if (result == -1) {
            Log.e("DatabaseHelper", "Failed to insert doctor. Values: " + values.toString());
        }
        db.close();
        return result != -1;
    }

    // Get all doctors
    public Cursor getAllDoctors() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT " + COL_DOCTOR_ID + " AS _id, " +
                        COL_DOCTOR_NAME + ", " +
                        COL_SPECIALIZATION + ", " +
                        COL_PHONE + ", " +
                        COL_FEE + ", " +
                        COL_VISITING_TIME + ", " +
                        COL_DOCTOR_IMAGE_URI +
                        " FROM " + TABLE_DOCTORS,
                null
        );
    }

    public Cursor getDoctorByName(String doctorName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_DOCTORS + " WHERE " + COL_DOCTOR_NAME + " = ?",
                new String[]{doctorName}
        );
        return cursor;
    }

    public boolean updateDoctor(int id, String name, String specialization, String phone, double fee, String visitingTime, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DOCTOR_NAME, name);
        values.put(COL_SPECIALIZATION, specialization);
        values.put(COL_PHONE, phone);
        values.put(COL_FEE, fee);
        values.put(COL_VISITING_TIME, visitingTime);
        values.put(COL_DOCTOR_IMAGE_URI, image);

        int rowsUpdated = db.update(TABLE_DOCTORS, values, COL_DOCTOR_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsUpdated > 0;
    }

    public Cursor getSearchDoctorByNameOrSpecialization(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT " + COL_DOCTOR_ID + " AS _id, " + // Alias for adapter compatibility
                COL_DOCTOR_NAME + ", " +
                COL_SPECIALIZATION + ", " +
                COL_PHONE + ", " +
                COL_FEE + ", " +
                COL_VISITING_TIME + ", " + // Include the visiting time column for completeness
                COL_DOCTOR_IMAGE_URI + // Include the image column
                " FROM " + TABLE_DOCTORS + // Table name
                " WHERE " + COL_DOCTOR_NAME + " LIKE ? OR " + COL_SPECIALIZATION + " LIKE ?"; // Search criteria
        return db.rawQuery(sql, new String[]{"%" + query + "%", "%" + query + "%"}); // Parameters for the query
    }



    // Insert a new booking
    public boolean insertBooking(String patientName, String doctorName, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PATIENT_NAME, patientName);
        values.put(COL_DOCTOR_BOOKED, doctorName);
        values.put(COL_DATE, date);
        values.put(COL_TIME, time);

        long result = db.insert(TABLE_BOOKINGS, null, values);
        db.close();
        return result != -1;
    }


    // Get all bookings
    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT " + COL_BOOKING_ID + " AS _id, " +
                        COL_PATIENT_NAME + ", " +
                        COL_DOCTOR_BOOKED + ", " +
                        COL_DATE + ", " +
                        COL_TIME +
                        " FROM " + TABLE_BOOKINGS,
                null
        );
    }



    // Delete a doctor by name
    public boolean deleteDoctor(String doctorName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_DOCTORS, COL_DOCTOR_NAME + " = ?", new String[]{doctorName});
        db.close();
        return rowsAffected > 0;
    }

}
