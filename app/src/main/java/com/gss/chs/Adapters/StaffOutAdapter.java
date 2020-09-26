package com.gss.chs.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gss.chs.Activities.StaffEditProfileActivity;
import com.gss.chs.Activities.StaffProfileActivity;
import com.gss.chs.Model.StaffModel;
import com.gss.chs.R;

import java.util.ArrayList;

public class StaffOutAdapter  extends RecyclerView.Adapter<StaffOutAdapter.MyViewHolder> {
    static Context context;
    Context actcontext;
    private ArrayList<StaffModel> BusinessArrayList;

    public StaffOutAdapter(Context context, ArrayList<StaffModel> BusinessModelArrayList) {
        this.context = context;
        BusinessArrayList = BusinessModelArrayList;
    }
    @Override
    public void onBindViewHolder(final StaffOutAdapter.MyViewHolder holder, int position) {
        final StaffModel Items = BusinessArrayList.get(position);
        holder.mobile_number.setText(Items.getMobileNumber());
        holder.name.setText(Items.getName());

//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .error(R.drawable.noimage)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .priority(Priority.HIGH);

//        Glide.with(context).load(Items.getBusinesImage()).apply(options).into(holder.BusinessImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StaffProfileActivity.class);

                intent.putExtra("name",Items.getName());
                intent.putExtra("mobile",Items.getMobileNumber());
                intent.putExtra("title",Items.getOccupation());

                intent.putExtra("service_name",Items.getServiceName());

//                intent.putExtra("inTime",Items.getDate());
//
//                intent.putExtra("outTime",Items.getOutTime());

                intent.putExtra("address",Items.getAddress());
                intent.putExtra("Email",Items.getEmail());






//                intent.putExtra("cb_id", Items.getCb_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public StaffOutAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewgroup, final int position) {
        View layout = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.staff_layout, viewgroup, false);
        StaffOutAdapter.MyViewHolder holder = new StaffOutAdapter.MyViewHolder(layout);
        return holder;
    }
    @Override
    public int getItemCount() {

        return BusinessArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,mobile_number;
        public MyViewHolder(View view) {
            super(view);
            mobile_number = view.findViewById(R.id.date);
            name = view.findViewById(R.id.name);

        }
    }

}