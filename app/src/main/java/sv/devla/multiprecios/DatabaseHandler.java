package sv.devla.multiprecios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // for our logs
    public static final String TAG = "DatabaseHandler.java";

    // database version
    private static final int DATABASE_VERSION = 5;

    // database name
    protected static final String DATABASE_NAME = "BusquedaDB";

    // table details
    public String tableName = "tbl_prds_busqueda";
    public String fieldObjectId = "id";
    public String fieldObjectName = "name";
    public String fieldObjectIDDB = "iddb";

    public String tableNameBrand = "tbl_brands_busqueda";
    public String fieldObjectIdBrand = "id";
    public String fieldObjectNameBrand = "name";
    public String fieldObjectIDDBBrand = "iddb";

    // constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creating table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "";
        String sql2 = "";
        sql += "CREATE TABLE " + tableName;
        sql += " ( ";
        sql += fieldObjectId + " INTEGER PRIMARY KEY AUTOINCREMENT, "; // control intero
        sql += fieldObjectName + " TEXT, ";  // nombre compuesto para busqueda
        sql += fieldObjectIDDB + " TEXT ";   //codigo de producto interno
        sql += " ) ";

        db.execSQL(sql);


        sql2 += "CREATE TABLE " + tableNameBrand;
        sql2 += " ( ";
        sql2 += fieldObjectIdBrand + " INTEGER PRIMARY KEY AUTOINCREMENT, "; // control intero
        sql2 += fieldObjectNameBrand + " TEXT, ";  // nombre compuesto para busqueda
        sql2 += fieldObjectIDDBBrand + " TEXT ";   //codigo de producto interno
        sql2 += " ) ";

        db.execSQL(sql2);

    }

    // When upgrading the database, it will drop the current table and recreate.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + tableName;

        db.execSQL(sql);

        String asql = "DROP TABLE IF EXISTS " + tableNameBrand;
        db.execSQL(asql);

        onCreate(db);
    }

    // create new record
    // @param myObj contains details to be added as single row.
    public boolean create(ProductSearchObject myObj) {

        boolean createSuccessful = false;

        if(!checkIfExists(myObj.objectName)){

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(fieldObjectName, myObj.objectName);
            values.put(fieldObjectIDDB, myObj.objectCode);

            createSuccessful = db.insert(tableName, null, values) > 0;

            db.close();

            if(createSuccessful){
                Log.e(TAG, myObj.objectName + " created.");
            }
        }

        return createSuccessful;
    }


    public boolean createBrand(ProductSearchObject myObj) {

        boolean createSuccessful = false;

        if(!checkIfExists(myObj.objectName)){

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(fieldObjectName, myObj.objectName);
            values.put(fieldObjectIDDB, myObj.objectCode);

            createSuccessful = db.insert(tableNameBrand, null, values) > 0;

            db.close();

            if(createSuccessful){
                Log.e(TAG, myObj.objectName + " created.");
            }
        }

        return createSuccessful;
    }

    // check if a record exists so it won't insert the next time you run this code
    public boolean checkIfExists(String objectName){

        boolean recordExists = false;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + fieldObjectId + " FROM " + tableName + " WHERE " + fieldObjectName + " = '" + objectName + "'", null);

        if(cursor!=null) {

            if(cursor.getCount()>0) {
                recordExists = true;
            }
        }

        cursor.close();
        db.close();

        return recordExists;
    }

    public boolean checkIfExistsBrand(String objectName){

        boolean recordExists = false;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + fieldObjectId + " FROM " + tableNameBrand + " WHERE " + fieldObjectName + " = '" + objectName + "'", null);

        if(cursor!=null) {

            if(cursor.getCount()>0) {
                recordExists = true;
            }
        }

        cursor.close();
        db.close();

        return recordExists;
    }

    // Read records related to the search term
    public List<ProductSearchObject> read(String searchTerm) {

        List<ProductSearchObject> recordsList = new ArrayList<ProductSearchObject>();

        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableName;
        sql += " WHERE " + fieldObjectName + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + fieldObjectId + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                // int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId)));
                String objectName = cursor.getString(cursor.getColumnIndex(fieldObjectName));
                String objectCode = cursor.getString(cursor.getColumnIndex(fieldObjectIDDB));
                ProductSearchObject productSearchObject = new ProductSearchObject(objectName,objectCode);

                // add to list
                recordsList.add(productSearchObject);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return the list of records
        return recordsList;
    }
// BRAND LIST

    public List<ProductSearchObject> readBrand(String searchTerm) {

        List<ProductSearchObject> recordsList = new ArrayList<ProductSearchObject>();

        // select query
        String sql = "";
        sql += "SELECT * FROM " + tableNameBrand;
        sql += " WHERE " + fieldObjectName + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + fieldObjectId + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                // int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId)));
                String objectName = cursor.getString(cursor.getColumnIndex(fieldObjectName));
                String objectCode = cursor.getString(cursor.getColumnIndex(fieldObjectIDDB));
                ProductSearchObject productSearchObject = new ProductSearchObject(objectName,objectCode);

                // add to list
                recordsList.add(productSearchObject);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return the list of records
        return recordsList;
    }
    // get last id BRAND

    public String getLastBrandID() {

        Cursor cursor = null;
        String empName = "0";
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery("SELECT MAX(iddb) as iddb FROM "+tableNameBrand,null);

            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                empName = cursor.getString(cursor.getColumnIndex("iddb"));
            }
            else{empName="0";}
            //Log.d("LAST BRAND",empName);
            return empName;
        }finally {

            cursor.close();
        }
    }
    // get last id Product

    public String getLastProductID() {

        Cursor cursor = null;
        String empName = "0";
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery("SELECT MAX(iddb) as iddb FROM "+tableName,null );

            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                empName = cursor.getString(cursor.getColumnIndex("iddb"));
            }
            else{empName="0";}


            //Log.d("LAST BRAND",empName);
            return empName;
        }finally {

            cursor.close();
        }
    }
}