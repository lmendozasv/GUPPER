package sv.devla.gupper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLiteAdapter {

    public static final String DATABASE_NAME = "gupperdb";

    public static final String DEPTO_TABLE = "tbl_departaments";
    public static final String MUNIC_TABLE = "tbl_municipalities";
    /*catalogs*/
    public static final String BOXINGS_TABLE = "tbl_boxings";
    public static final String BRANDS_TABLE = "tbl_brands";
    public static final String CATS_TABLE = "tbl_cats";

    public static final String KEYWORDS_TABLE ="tbl_kw";
    public static final String PRICES_TABLE ="tbl_prices";
    public static final String ITEMS_TABLE ="tbl_items";
    public static final String TLOCATIONS_TABLE ="tbl_tcomlocations";
    public static final String LOCATIONS_TABLE ="tbl_comlocations";
    public static final String MATERIALS_TABLE ="tbl_materials";
    public static final String TPRICES_TABLE ="tbl_tprices";
    public static final String PRIZES_TABLE ="tbl_prizes";
    public static final String REDEEMS_TABLE ="tbl_myredeems";
    public static final String SOURCES_TABLE ="tbl_sources";
    public static final String SUBCATS_TABLE ="tbl_subcats";
    public static final String TELCOS_TABLE ="tbl_telcos";

    public static final int MYDATABASE_VERSION = 3;

    public static final String KEY_ID = "_id";

    public static final String FIELD_GNAME = "name";
    public static final String FIELD_GVALUE = "value";
    public static final String FIELD_GCODERELATION = "code";
    public static final String FIELD_url = "url";

    public static final String FIELD_id_item = "id_item";
    public static final String FIELD_name = "name";
    public static final String FIELD_id_cat = "id_cat";
    public static final String FIELD_id_brand = "id_brand";
    public static final String FIELD_id_box = "id_box";
    public static final String FIELD_id_subcat = "id_subcat";
    public static final String FIELD_startdt = "startdt";
    public static final String FIELD_lastdt = "lastdt";
    public static final String FIELD_quality_stars = "quality_stars";
    public static final String FIELD_id_mat = "id_mat";
    public static final String FIELD_quantity = "quantity";
    public static final String FIELD_status_item = "status_item";
    public static final String FIELD_cat_name = "cat_name";
    public static final String FIELD_status_cat = "status_cat";
    public static final String FIELD_name_brand = "name_brand";
    public static final String FIELD_cat_id = "cat_id";
    public static final String FIELD_status_brand = "status_brand";
    public static final String FIELD_name_boxing = "name_boxing";
    public static final String FIELD_id_user = "id_user";
    public static final String FIELD_status_box = "status_box";
    public static final String FIELD_name_mat = "name_mat";
    public static final String FIELD_status = "status";




    private static final String CREATE_DEPTO_TABLE =
            "create table " + DEPTO_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + FIELD_GNAME + " text , "
                    + FIELD_GVALUE + " text ); ";

    private static final String CREATE_MUNIC_TABLE =
            "create table " + MUNIC_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + FIELD_GNAME + " text , "
                    + FIELD_GCODERELATION + " text , "
                    + FIELD_GVALUE + " text ); ";

    private static final String CREATE_BOXINGS_TABLE =
            "create table " + BOXINGS_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + FIELD_GNAME + " text , "
                    + FIELD_GVALUE + " text ); ";

    private static final String CREATE_BRANDS_TABLE =
            "create table " + BRANDS_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + FIELD_GNAME + " text , "
                    + FIELD_GVALUE + " text ); ";

    private static final String CREATE_CATS_TABLE =
            "create table " + CATS_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + FIELD_GNAME + " text , "
                    + FIELD_GVALUE + " text ); ";


    private static final String CREATE_KEYWORDS_TABLE =
            "create table " + KEYWORDS_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + FIELD_GNAME + " text , "
                    +  FIELD_GVALUE + " text , "
                    + FIELD_GCODERELATION+ " text ); ";

    private static final String CREATE_PRICES_TABLE =
            "create table " + PRICES_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + FIELD_GNAME + " text , "
                    +  FIELD_GVALUE + " text , "
                    + FIELD_GCODERELATION+ " text ); ";

    private static final String CREATE_ITEMS_TABLE =
            "create table " + ITEMS_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + FIELD_id_item+" text,"
                    + FIELD_name+" text,"
                    + FIELD_id_cat+" text,"
                    + FIELD_id_brand+" text,"
                    + FIELD_id_box+" text,"
                    + FIELD_id_subcat+" text,"
                    + FIELD_startdt+" text,"
                    + FIELD_lastdt+" text,"
                    + FIELD_quality_stars+" text,"
                    + FIELD_id_mat+" text,"
                    + FIELD_quantity+" text,"
                    + FIELD_status_item+" text,"
                    + FIELD_cat_name+" text,"
                    + FIELD_status_cat+" text,"
                    + FIELD_name_brand+" text,"
                    + FIELD_cat_id+" text,"
                    + FIELD_status_brand+" text,"
                    + FIELD_name_boxing+" text,"
                    + FIELD_id_user+" text,"
                    + FIELD_status_box+" text,"
                    + FIELD_name_mat+" text,"
                    + FIELD_status+" text"
                    + FIELD_GCODERELATION+ " text ); ";



    private static final String CREATE_TLOCATIONS_TABLE =
            "create table " + TLOCATIONS_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + FIELD_GCODERELATION + " text , " //idtloc
                    + FIELD_GNAME + " text , " //nametloc
                    + FIELD_GVALUE + " text ); "; //icontloc

    private static final String CREATE_LOCATIONS_TABLE =
            "create table " + LOCATIONS_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + FIELD_GCODERELATION + " text , " //idloc
                    + FIELD_cat_id+" text," //type_location
                    + FIELD_GNAME + " text , " //loc name
                    + FIELD_GVALUE + " text ); "; //loc subname

    private static final String CREATE_MATERIALS_TABLE =
            "create table " + MATERIALS_TABLE + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + FIELD_GNAME + " text , " //idmat
                    + FIELD_GVALUE + " text ); "; //namemat

    private static final String CREATE_TPRICES_TABLE =
            "create table " + TPRICES_TABLE+ " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + FIELD_GNAME + " text , " //idmat
                    + FIELD_GVALUE + " text ); "; //namemat


    private static final String CREATE_PRIZES_TABLE =
            "create table " + PRIZES_TABLE+ " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + FIELD_GNAME + " text , " //idprize
                    + FIELD_name + " text , " //name prize
                    + FIELD_url + " text , " //img prize
                    + FIELD_GVALUE + " text ); "; //value prize

    private static final String CREATE_REDEEMS_TABLE =
            "create table " + REDEEMS_TABLE+ " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + FIELD_GNAME + " text , " //idprize
                    + FIELD_name + " text , " //status
                    + FIELD_GVALUE + " text ); "; //value prize

    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;

    private Context context;

    public SQLiteAdapter(Context c){
           context = c;
    }

    public SQLiteAdapter openToRead() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, DATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    public SQLiteAdapter openToWrite() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, DATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        sqLiteHelper.close();
    }
/*
    public long insertCities(String cityname,String zipid, String postcode){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CITYNAME, cityname);
        contentValues.put(KEY_ZIPID, zipid);
        contentValues.put(KEY_POSTCODE, postcode);
        return sqLiteDatabase.insert(CITIES_TABLE, null, contentValues);
    }
*/
    public int deleteAllDeptos(){
        return sqLiteDatabase.delete(DEPTO_TABLE, null, null);
    }


    public class SQLiteHelper extends SQLiteOpenHelper {

        public SQLiteHelper(Context context, String name,
                            CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(CREATE_DEPTO_TABLE);
            db.execSQL(CREATE_MUNIC_TABLE);
          //  db.execSQL(CREATE_BOXINGS_TABLE);
          //  db.execSQL(CREATE_BRANDS_TABLE);
         //   db.execSQL(CREATE_CATS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

        }

    }

    public void insertDepartment(String name,String id){
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_GNAME, id);
        values.put(FIELD_GVALUE, name);
            // Inserting Row
        try{
            Log.d("Insertando SQLITE",values.toString());
            db.insert(DEPTO_TABLE, null, values);
            db.close();
            // Closing database connection
           }
        catch(Exception s){
            Log.d("Error",s.toString());
        }
    }


    public void insertLocality(String name,String id,String department){
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_GNAME, id);
        values.put(FIELD_GCODERELATION, department);
        values.put(FIELD_GVALUE, name);
        // Inserting Row
        try{
            Log.d("Insertando SQLITE ",MUNIC_TABLE+values.toString());
            db.insert(MUNIC_TABLE, null, values);
            db.close();
            // Closing database connection
        }
        catch(Exception s){
            Log.d("Error",s.toString());
        }
    }


    public List<String> getCities(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DEPTO_TABLE;


        sqLiteHelper = new SQLiteHelper(context, DATABASE_NAME, null, MYDATABASE_VERSION);

        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d("cities",labels.toString());
        return labels;
    }

    public List<String> getLocationsByCity(String department){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + MUNIC_TABLE + " WHERE " + FIELD_GCODERELATION+ " = " +department;

        Log.d("Query",selectQuery);

        sqLiteHelper = new SQLiteHelper(context, DATABASE_NAME, null, MYDATABASE_VERSION);

        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d("cities",labels.toString());
        return labels;
    }

    public int getCitiesCount() {
        sqLiteHelper = new SQLiteHelper(context, DATABASE_NAME, null, MYDATABASE_VERSION);
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db, DEPTO_TABLE);
        db.close();
        int fpp=(int)cnt;
        return fpp;
    }


    public String getIDfromName(String name){
        String empid="";
        String selectQuery = "SELECT "+FIELD_GVALUE+" from "+DEPTO_TABLE+" where "+FIELD_GNAME+" = '" + name+"' ";

        sqLiteHelper = new SQLiteHelper(context, DATABASE_NAME, null, MYDATABASE_VERSION);

        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            empid = cursor.getString(0);
        }
        return empid;
    }

    public String getIDfromNameMuni(String name){
        String empid="";
        String selectQuery = "SELECT "+FIELD_GVALUE+" from "+MUNIC_TABLE+" where "+FIELD_GNAME+" = '" + name+"' ";

        sqLiteHelper = new SQLiteHelper(context, DATABASE_NAME, null, MYDATABASE_VERSION);

        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            empid = cursor.getString(0);
        }
        return empid;
    }
}