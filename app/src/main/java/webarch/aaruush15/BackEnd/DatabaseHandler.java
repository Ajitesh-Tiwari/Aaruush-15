package webarch.aaruush15.BackEnd;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AJITESH on 13-07-2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    SharedPreferences pref;
    BufferedReader br;

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Database";

    // Contacts table name
    private static final String TABLE_DATA = "data";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";
    private static final String KEY_DOMAIN = "domain";
    private static final String KEY_VENUE = "venue";
    private static final String KEY_TIME = "time";
    private static final String KEY_DATE = "date";
    private static final String KEY_DESC = "desc";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_FAV = "fav";

    public DatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        pref=context.getSharedPreferences("MyPref", 0);
        try {
            br= new BufferedReader(new InputStreamReader(context.getAssets().open("staticdata.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " VARCHAR(40),"
                + KEY_TYPE + " VARCHAR(20),"
                + KEY_DOMAIN + " VARCHAR(20),"
                + KEY_VENUE + " VARCHAR(20),"
                + KEY_TIME + " VARCHAR(20),"
                + KEY_DATE + " VARCHAR(30),"
                + KEY_DESC + " VARCHAR(1000),"
                + KEY_IMAGE + " VARCHAR(20),"
                + KEY_FAV + " INTEGER(1)"
                + ");";

        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
        setVersion(0);
        try {
            rnrStatic();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        // Create tables again
        onCreate(sqLiteDatabase);

    }

    public void rnrStatic()throws IOException
    {

        String readLine = null;
        String data="";
        while ((readLine = br.readLine()) != null)
            data+=readLine;
        try {
            JSONArray response=new JSONArray(data);
            forceRead(response);
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
    public void forceRead(JSONArray response)
    {
        new RunThread().execute(response);
    }

    void addData(Data data) {
        Log.d("AARUUSH","addData");

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_ID,data.getID());
        values.put(KEY_NAME, data.getName().trim()); // Contact Name
        values.put(KEY_TYPE, data.getType().trim().toLowerCase());
        values.put(KEY_DOMAIN, data.getDomain().trim().toLowerCase()); // Contact Name
        values.put(KEY_VENUE, data.getVenue().trim().toLowerCase());
        values.put(KEY_TIME, data.getTime()); // Contact Name
        values.put(KEY_DATE, data.getDate());
        values.put(KEY_DESC, data.getDesc());
        values.put(KEY_IMAGE, data.getImage().trim().toLowerCase());
        values.put(KEY_FAV, 0);

        // Inserting Row
        db.insert(TABLE_DATA, null, values);
        db.close(); // Closing database connection
    }

    public Data getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DATA, new String[] { KEY_ID,
                        KEY_NAME, KEY_TYPE, KEY_DOMAIN, KEY_VENUE, KEY_TIME, KEY_DATE, KEY_DESC, KEY_IMAGE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return null;
        }

        if (cursor != null)
            cursor.moveToFirst();

        Data data = new Data(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6),cursor.getString(7),cursor.getString(8));
        // return contact
        return data;
    }
    public List<Data> getAllData() {
        List<Data> dataList = new ArrayList<Data>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return null;
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Data data = new Data();
                data.setID(Integer.parseInt(cursor.getString(0)));
                data.setName(cursor.getString(1));
                data.setType(cursor.getString(2));
                data.setDomain(cursor.getString(3));
                data.setVenue(cursor.getString(4));
                data.setTime(cursor.getString(5));
                data.setDate(cursor.getString(6));
                data.setDesc(cursor.getString(7));
                data.setImage(cursor.getString(8));

                // Adding contact to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return contact list
        return dataList;
    }
    public int getDataCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
    public int updateData(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, data.getName()); // Contact Name
        values.put(KEY_TYPE, data.getType().trim().toLowerCase());
        values.put(KEY_DOMAIN, data.getDomain().trim().toLowerCase()); // Contact Name
        values.put(KEY_VENUE, data.getVenue().trim().toLowerCase());
        values.put(KEY_TIME, data.getTime()); // Contact Name
        values.put(KEY_DATE, data.getDate());
        values.put(KEY_DESC, data.getDesc());
        values.put(KEY_IMAGE, data.getImage().trim().toLowerCase());

        // updating row
        return db.update(TABLE_DATA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(data.getID()) });
    }
    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATA, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
    public boolean dataExists(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " +TABLE_DATA + " where " + KEY_ID + " = " + id;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public List<Data> getDatabyDomain(String Domain){

        List<Data> dataList = new ArrayList<Data>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA +" WHERE "+KEY_DOMAIN+"='"+Domain+"';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return null;
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Data data = new Data();
                data.setID(Integer.parseInt(cursor.getString(0)));
                data.setName(cursor.getString(1));
                data.setType(cursor.getString(2));
                data.setDomain(cursor.getString(3));
                data.setVenue(cursor.getString(4));
                data.setTime(cursor.getString(5));
                data.setDate(cursor.getString(6));
                data.setDesc(cursor.getString(7));
                data.setImage(cursor.getString(8));

                // Adding contact to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return contact list
        return dataList;
    }

    public List<Data> getDatabyTypeFav(String Type){
        List<Data> dataList = new ArrayList<Data>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA +" WHERE "+KEY_TYPE+"='"+Type+"' AND "+KEY_FAV+"='1';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return null;
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Data data = new Data();
                data.setID(Integer.parseInt(cursor.getString(0)));
                data.setName(cursor.getString(1));
                data.setType(cursor.getString(2));
                data.setDomain(cursor.getString(3));
                data.setVenue(cursor.getString(4));
                data.setTime(cursor.getString(5));
                data.setDate(cursor.getString(6));
                data.setDesc(cursor.getString(7));
                data.setImage(cursor.getString(8));

                // Adding contact to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return contact list
        return dataList;
    }
    public List<Data> getDatabyType(String Type){
        List<Data> dataList = new ArrayList<Data>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA +" WHERE "+KEY_TYPE+"='"+Type+"';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return null;
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Data data = new Data();
                data.setID(Integer.parseInt(cursor.getString(0)));
                data.setName(cursor.getString(1));
                data.setType(cursor.getString(2));
                data.setDomain(cursor.getString(3));
                data.setVenue(cursor.getString(4));
                data.setTime(cursor.getString(5));
                data.setDate(cursor.getString(6));
                data.setDesc(cursor.getString(7));
                data.setImage(cursor.getString(8));

                // Adding contact to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return contact list
        return dataList;
    }
    public int getVersion(){
        return pref.getInt("Version", 0);
    }
    public void setVersion(int version)
    {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("Version", version);
        editor.commit();

    }

    public int setFavourite(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FAV, 1);

        // updating row
        return db.update(TABLE_DATA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public List<Data> getFavourite(){
        List<Data> dataList = new ArrayList<Data>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA +" WHERE "+KEY_FAV+"=1;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return null;
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Data data = new Data();
                data.setID(Integer.parseInt(cursor.getString(0)));
                data.setName(cursor.getString(1));
                data.setType(cursor.getString(2));
                data.setDomain(cursor.getString(3));
                data.setVenue(cursor.getString(4));
                data.setTime(cursor.getString(5));
                data.setDate(cursor.getString(6));
                data.setDesc(cursor.getString(7));
                data.setImage(cursor.getString(8));

                // Adding contact to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return contact list
        return dataList;

    }
    public int removeFavourite(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FAV, 0);

        // updating row
        return db.update(TABLE_DATA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });

    }
    public int isFavourite(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DATA, new String[] {KEY_FAV}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return -1;
        }

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(0);

    }
    private class RunThread extends AsyncTask<JSONArray, Void,Void> {
        @Override
        protected Void doInBackground(JSONArray... jsonArrays) {
            JSONArray response=jsonArrays[0];

            try {
                if(response.getJSONObject(0).getInt("version")==-1){
                    onUpgrade(getWritableDatabase(),1,1);}
                else{
                    setVersion(response.getJSONObject(0).getInt("version"));
                    for(int i=1;i<response.length();i++)
                    {
                        JSONObject Jobj=response.getJSONObject(i);
                        if(dataExists(Jobj.getInt("id")))
                        {

                            Log.d("AARUUSH", "Update");
                            updateData(new Data(Jobj.getInt("id"),
                                    Jobj.getString("name"),
                                    Jobj.getString("type"),
                                    Jobj.getString("domain"),
                                    Jobj.getString("venue"),
                                    Jobj.getString("time"),
                                    Jobj.getString("date"),
                                    Jobj.getString("desc"),
                                    Jobj.getString("image")
                            ));
                        }
                        else
                        {
                            Log.d("AARUUSH", "Add");
                            addData(new Data(Jobj.getInt("id"),
                                    Jobj.getString("name"),
                                    Jobj.getString("type"),
                                    Jobj.getString("domain"),
                                    Jobj.getString("venue"),
                                    Jobj.getString("time"),
                                    Jobj.getString("date"),
                                    Jobj.getString("desc"),
                                    Jobj.getString("image")
                            ));
                        }
                    }}
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
