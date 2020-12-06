package com.example.androidfinalproject.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidfinalproject.model.Place;

import java.util.ArrayList;

import static com.example.androidfinalproject.model.database.DatabaseConstants.PlaceTable.PLACE_ID;

public class DatabaseOperation implements DatabaseDAO {

    private SQLiteDatabase db;

    public DatabaseOperation(Context context) {
        MyDBOpenHelper helper = new MyDBOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    @Override
    public void addVenue(Place venue) {
        ContentValues values = new ContentValues();
        values.put(PLACE_ID, venue.getId());
        values.put(DatabaseConstants.PlaceTable.NAME, venue.getName());
        values.put(DatabaseConstants.PlaceTable.ADDRESS, venue.getAddress());
        values.put(DatabaseConstants.PlaceTable.PHONE, venue.getPhone());
        values.put(DatabaseConstants.PlaceTable.CATEGORIES, venue.getCategory());
        values.put(DatabaseConstants.PlaceTable.CATEGORIES, venue.getHasInfo());
        System.out.println("adding venues to Db ============   " + venue.getName());
//        values.put(DatabaseConstants.PlaceTable.LAT , venue.getLatitude());
//        values.put(DatabaseConstants.PlaceTable.LONG , venue.getLongitude());
        db.insert(DatabaseConstants.PlaceTable.TABLE_NAME, null, values);
    }

    @Override
    public void updateVenue(Place venue) {
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.PlaceTable.ADDRESS, venue.getAddress());
        values.put(DatabaseConstants.PlaceTable.PHONE, venue.getPhone());
        values.put(DatabaseConstants.PlaceTable.CATEGORIES, venue.getCategory());
        values.put(DatabaseConstants.PlaceTable.HASINFO, 1);
        venue.setHasInfo(1);
        db.update(DatabaseConstants.PlaceTable.TABLE_NAME, values, PLACE_ID + "=?", new String[]{venue.getId()});
    }

    @Override
    public Place getVenue(String id) {
        Cursor cursor = db.query(DatabaseConstants.PlaceTable.TABLE_NAME,
                null, "venue_id=?", new String[]{id}, null, null, null);

        String name, address, phone, categories;
        int info;
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PlaceTable.NAME));
            address = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PlaceTable.ADDRESS));
            phone = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PlaceTable.PHONE));
            categories = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PlaceTable.CATEGORIES));
            info = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.PlaceTable.HASINFO));
            cursor.close();
            return new Place(id, name, address, phone, categories, info);
        } else return null;

    }

    @Override
    public ArrayList<Place> getVenues() {
        Cursor cursor = db.query(DatabaseConstants.PlaceTable.TABLE_NAME,
                null, null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<Place> venues = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            String id, name, address, phone, categories;
            int info;
            id = cursor.getString(cursor.getColumnIndex(PLACE_ID));
            name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PlaceTable.NAME));
            address = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PlaceTable.ADDRESS));
            phone = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PlaceTable.PHONE));
            categories = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PlaceTable.CATEGORIES));
            info = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.PlaceTable.HASINFO));
            venues.add(new Place(id, name, address, phone, categories, info));
            System.out.println("getting venues ============   " + name);
            cursor.moveToNext();
        }
        cursor.close();
        return venues;
    }

    @Override
    public void updateDB(ArrayList<Place> venues) {
        db.execSQL(DatabaseConstants.PlaceTable.DELETE_ENTRIES);
        db.execSQL(DatabaseConstants.PlaceTable.CREATE_PLACE_TABLE_SYNTAX);
        for (int i = 0; i < venues.size(); i++)
            addVenue(venues.get(i));
    }

}
