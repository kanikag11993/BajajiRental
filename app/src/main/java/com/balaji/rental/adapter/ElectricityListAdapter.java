package com.balaji.rental.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balaji.rental.R;
import com.balaji.rental.model.ElectricityModel;
import com.balaji.rental.model.PropertyModel;

import java.util.ArrayList;

public class ElectricityListAdapter extends RecyclerView.Adapter<ElectricityListAdapter.ElectricityViewHolder> {

    private Context mContext;
    private ArrayList<ElectricityModel> electricityModelArrayList;

    public ElectricityListAdapter(Context mContext, ArrayList<ElectricityModel> electricityModelArrayList) {
        this.mContext = mContext;
        this.electricityModelArrayList = electricityModelArrayList;
    }

    @NonNull
    @Override
    public ElectricityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.electricity_item, parent, false);
        return new ElectricityListAdapter.ElectricityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ElectricityViewHolder holder, int position) {
        ElectricityModel electricityModel = electricityModelArrayList.get(position);
        holder.readingCapturedDate.setText(electricityModel.getDateTaken());
        holder.readingMonth.setText(electricityModel.getMonth());
        holder.reading.setText(electricityModel.getReadingValue().toString());
    }

    @Override
    public int getItemCount() {
        return electricityModelArrayList.size();
    }

    public class ElectricityViewHolder extends RecyclerView.ViewHolder {
        private TextView readingMonth;
        private TextView readingCapturedDate;
        private TextView reading;

        public ElectricityViewHolder(@NonNull View itemView) {
            super(itemView);
            readingMonth = itemView.findViewById(R.id.month);
            readingCapturedDate = itemView.findViewById(R.id.date_taken);
            reading = itemView.findViewById(R.id.reading);
        }
    }
}
