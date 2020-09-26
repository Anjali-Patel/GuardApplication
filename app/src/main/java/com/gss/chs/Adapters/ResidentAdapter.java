package com.gss.chs.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gss.chs.Activities.ResidentDetailActivity;
import com.gss.chs.Activities.StaffEditProfileActivity;
import com.gss.chs.Model.ResidentModel;
import com.gss.chs.Model.StaffModel;
import com.gss.chs.R;

import java.util.ArrayList;

public class ResidentAdapter extends RecyclerView.Adapter<ResidentAdapter.MyViewHolder> {
    static Context context;
    Context actcontext;
    private ArrayList<ResidentModel> BusinessArrayList;

    public ResidentAdapter(Context context, ArrayList<ResidentModel> BusinessModelArrayList) {
        this.context = context;
        BusinessArrayList = BusinessModelArrayList;
    }
    @Override
    public void onBindViewHolder(final ResidentAdapter.MyViewHolder holder, int position) {
        final ResidentModel Items = BusinessArrayList.get(position);
        holder.flat_number.setText(Items.getWing_name()+","+Items.getFlat_number());
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
                Intent intent = new Intent(context, ResidentDetailActivity.class);
                intent.putExtra("name",Items.getName());
                intent.putExtra("mobile",Items.getMobile_number());
                intent.putExtra("date",Items.getDate());

                intent.putExtra("flatNo",Items.getFlat_number());
                intent.putExtra("mailing_aadress",Items.getMailingAddress());

                intent.putExtra("allocated_work",Items.getAllocatedWork());

//                intent.putExtra("inTime",Items.getDate());
//
//                intent.putExtra("outTime",Items.getOutTime());

                intent.putExtra("purpose",Items.getPurpose());
                intent.putExtra("wing_name",Items.getWing_name());



//                intent.putExtra("cb_id", Items.getCb_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public ResidentAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewgroup, final int position) {
        View layout = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.resident_list_layout, viewgroup, false);
        ResidentAdapter.MyViewHolder holder = new ResidentAdapter.MyViewHolder(layout);
        return holder;
    }
    @Override
    public int getItemCount() {
        return BusinessArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name,flat_number;



        public MyViewHolder(View view) {
            super(view);
            flat_number = view.findViewById(R.id.flat_number);
            name = view.findViewById(R.id.name);

        }
    }

}