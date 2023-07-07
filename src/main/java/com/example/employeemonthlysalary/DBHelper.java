package com.example.employeemonthlysalary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "EmployeeDB";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_EMPLOYEE = "employee";
    public static final String COLUMN_EMPLOYEE_ID = "employee_id";
    public static final String COLUMN_EMPLOYEE_NAME = "employee_name";
    public static final String COLUMN_EMPLOYEE_SALARY = "employee_salary";
    public static final String COLUMN_EMPLOYEE_TAX = "employee_tax";
    public static final String COLUMN_EMPLOYEE_NETSAL = "employee_netsal";

    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createEmployeeTableQuery = "CREATE TABLE " + TABLE_EMPLOYEE + "("
                + COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMPLOYEE_NAME + " TEXT,"
                + COLUMN_EMPLOYEE_SALARY + " REAL,"
                + COLUMN_EMPLOYEE_TAX + " REAL,"
                + COLUMN_EMPLOYEE_NETSAL + " REAL" + ")";
        db.execSQL(createEmployeeTableQuery);

        String createUserTableQuery = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_USERNAME + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT" + ")";
        db.execSQL(createUserTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public long insertEmployee(String name, double salary, double tax, double netSalary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_NAME, name);
        values.put(COLUMN_EMPLOYEE_SALARY, salary);
        values.put(COLUMN_EMPLOYEE_TAX, tax);
        values.put(COLUMN_EMPLOYEE_NETSAL, netSalary);
        long id = db.insert(TABLE_EMPLOYEE, null, values);
        db.close();
        return id;
    }

    public Cursor getAllEmployees() {
        String query = "SELECT * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public void deleteAllEmployees() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, null, null);
        db.close();
    }

    public long insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, username);
        values.put(COLUMN_USER_PASSWORD, password);
        long id = db.insert(TABLE_USER, null, values);
        db.close();
        return id;
    }

    public boolean checkUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_USERNAME + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean credentialsMatch = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return credentialsMatch;
    }
}

