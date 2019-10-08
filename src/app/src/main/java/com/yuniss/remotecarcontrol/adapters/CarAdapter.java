package com.yuniss.remotecarcontrol.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuniss.remotecarcontrol.R;
import com.yuniss.remotecarcontrol.activities.CarDetails;
import com.yuniss.remotecarcontrol.helpers.Constants;
import com.yuniss.remotecarcontrol.model.Car;
import com.yuniss.remotecarcontrol.local.Current;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder>{

    private List<Car> carList;
    private Context _context;


    public CarAdapter(List<Car> carList,Context context) {
        this.carList = carList;
        this._context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Car car = carList.get(position);
        holder.name_txt.setText(car.getTitle());
        holder.phone_txt.setText(car.getPhone());
        holder.created_at_txt.setText("04/10/2019");
        holder.car_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Current.currentCar = car;
                _context.startActivity(new Intent(_context, CarDetails.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name_txt, phone_txt, created_at_txt;
        public RelativeLayout car_layout;

        public MyViewHolder(View view) {
            super(view);
            name_txt =  view.findViewById(R.id.name_txt);
            phone_txt =  view.findViewById(R.id.phone_txt);
            created_at_txt = view.findViewById(R.id.created_at_txt_number);
            car_layout = view.findViewById(R.id.car_layout);
        }
    }
}
