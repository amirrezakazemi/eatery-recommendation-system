package com.example.androidfinalproject.model.database;


import com.example.androidfinalproject.model.Place;

import java.util.ArrayList;

public interface DatabaseDAO {

    void addVenue(Place venue);

    void updateVenue(Place venue);

    Place getVenue(String id);

    void updateDB(ArrayList<Place> venues);

    ArrayList<Place> getVenues();
}
