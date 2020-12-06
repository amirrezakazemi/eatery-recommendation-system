package com.example.androidfinalproject.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidfinalproject.model.Place;
import com.example.androidfinalproject.R;

import java.util.List;

/* there is an interface and a class inside PlaceAdapter Class. we explain their usage later.
   inner class : View holder
   inner interface : ItemClickListener
*/
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    // ers barie bala yani inke gharare PlaceAdapter  majmooeE az View Holder ha bashad
    private List<Place> venueList;
    private List<Place> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    public PlaceAdapter(Context context, List<Place> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // we need to override these methods because extension
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        String name = mData.get(i).getName();
        holder.myTextView.setText(name);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void update(List<Place> newList) {
        mData = newList;
        this.notifyDataSetChanged();
    }

    public Place getItem(int id) {
        return mData.get(id);
    }

}
