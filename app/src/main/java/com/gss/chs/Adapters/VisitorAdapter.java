package com.gss.chs.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gss.chs.Activities.EditProfileActivity;
import com.gss.chs.Activities.StaffProfileActivity;
import com.gss.chs.Model.StaffModel;
import com.gss.chs.Model.VisitorModel;
import com.gss.chs.R;

import java.util.ArrayList;

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.MyViewHolder> {
    static Context context;
    Context actcontext;
    private ArrayList<VisitorModel> BusinessArrayList;

    public VisitorAdapter(Context context, ArrayList<VisitorModel> BusinessModelArrayList) {
        this.context = context;
        BusinessArrayList = BusinessModelArrayList;
    }

    @Override
    public void onBindViewHolder(final VisitorAdapter.MyViewHolder holder, int position) {
        final VisitorModel Items = BusinessArrayList.get(position);
        holder.date.setText(Items.getDate());
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
                Intent intent = new Intent(context, EditProfileActivity.class);
                intent.putExtra("name",Items.getName());
                intent.putExtra("visitor_id",Items.getVisitor_id());
                intent.putExtra("mobile",Items.getMobile_number());

                intent.putExtra("date",Items.getDate());

                intent.putExtra("flatNo",Items.getUser_flatnumber());

                intent.putExtra("inTime",Items.getIn_time());

                intent.putExtra("outTime",Items.getOutTime());

                intent.putExtra("purpose",Items.getPurpose());
                intent.putExtra("wing_name",Items.getWing_name());


                context.startActivity(intent);
            }
        });
    }

    @Override
    public VisitorAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewgroup, final int position) {
        View layout = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.visitor_layout, viewgroup, false);
        VisitorAdapter.MyViewHolder holder = new VisitorAdapter.MyViewHolder(layout);
        return holder;
    }

    @Override
    public int getItemCount() {

        return BusinessArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date;

        public MyViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.date);
            name = view.findViewById(R.id.name);

        }
    }
}