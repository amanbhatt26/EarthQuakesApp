package com.example.earthquake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EarthQuakeAdapter extends RecyclerView.Adapter<EarthQuakeAdapter.Viewholder> {
    private final List<Earthquake> mEarthquakeList;

    public EarthQuakeAdapter(List<Earthquake> mEarthquakeList) {
        this.mEarthquakeList = mEarthquakeList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_earthquake, parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.earthquake = mEarthquakeList.get(position);
        holder.detailsView.setText(mEarthquakeList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mEarthquakeList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private final View parentView;
        private final TextView detailsView;
        private Earthquake earthquake;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            this.parentView = itemView;
            this.detailsView = itemView.findViewById(R.id.list_item_earthquake_details);
        }

        @NonNull
        @Override
        public String toString(){
            return super.toString() + " " + detailsView.getText() + " ";
        }
    }
}
