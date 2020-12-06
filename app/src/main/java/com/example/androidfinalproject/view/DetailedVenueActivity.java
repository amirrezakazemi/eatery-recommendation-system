package com.example.androidfinalproject.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidfinalproject.model.Comment;
import com.example.androidfinalproject.controller.CommentAdapter;
import com.example.androidfinalproject.controller.DataProvider;
import com.example.androidfinalproject.model.Place;
import com.example.androidfinalproject.controller.PlaceAdapter;
import com.example.androidfinalproject.R;
import com.example.androidfinalproject.model.database.CommentDatabase;

import java.util.ArrayList;

public class DetailedVenueActivity extends Activity implements PlaceAdapter.ItemClickListener {
    TextView venueName, venueAddress, venuePhone, venueCategory;
    EditText comment;
    Button submit;
    AppCompatButton button;
    DataProvider dataProvider;
    RecyclerView recyclerView;
    PlaceAdapter mAdapter;
    Place mVenue;
    ArrayList<Comment> comments;
    RecyclerView cRecycleView;
    CommentAdapter commentsAdapter;
    CommentDatabase commentDatabase;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_venue);

        dataProvider = new DataProvider(this, new DataProvider.DataReadyListener() {
            @Override
            public void onDataReady(ArrayList<Place> venues) {

                if (mAdapter != null)
                    mAdapter.update(venues);
                else mAdapter = new PlaceAdapter(getApplicationContext(), venues);

                if (venues.size() > 0)
                    button.setText("مکان های مشابه: ");
                else
                    button.setText("مکان مشابهی یافت نشد!");
            }
        }, new DataProvider.DetailsReadyListener() {
            @Override
            public void onDetailsReady(Place venue) {

                mVenue = venue;
                venuePhone.setText(venue.getPhone());

                venueAddress.setText(venue.getAddress());

                venueCategory.setText(venue.getCategory());
            }
        });
        venueName = findViewById(R.id.venue_name);
        venueAddress = findViewById(R.id.venue_address);
        venuePhone = findViewById(R.id.venue_phone);
        venueCategory = findViewById(R.id.venue_category);
        button = findViewById(R.id.edit_info_button);
        comment = findViewById(R.id.commentBox);
        submit = findViewById(R.id.button);
        venueName.setText(getIntent().getStringExtra("name"));

        id = getIntent().getStringExtra("id");
        System.out.println("print1");
        commentDatabase = new CommentDatabase(getApplicationContext());

        dataProvider.getDetails(id);
        mAdapter = new PlaceAdapter(getApplicationContext(), new ArrayList<Place>());
        mAdapter.setClickListener(this);
        dataProvider.getRecommendations(id);
        recyclerView = findViewById(R.id.recommendation_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

    }

    public void onclick(View view) {
        commentDatabase.insert(id, String.valueOf(comment.getText()));
        comment.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getApplicationContext(), DetailedVenueActivity.class);
        intent.putExtra("id", mAdapter.getItem(position).getId());
        intent.putExtra("name", mAdapter.getItem(position).getName());
        startActivity(intent);
    }

    public void COnClick(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_comment);
        Button buttonDialog = (Button) dialog.findViewById(R.id.buttonDialog);
        buttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        comments = commentDatabase.query(id);
        commentsAdapter = new CommentAdapter(getApplicationContext(), comments);
        cRecycleView = dialog.findViewById(R.id.comment_recycler_view);
        dialog.setTitle(R.string.title);
        cRecycleView.setLayoutManager(new LinearLayoutManager(this));
        cRecycleView.setAdapter(commentsAdapter);
        dialog.show();
    }
}
