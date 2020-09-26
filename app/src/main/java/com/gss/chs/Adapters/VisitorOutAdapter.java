package com.gss.chs.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gss.chs.Activities.ExpectedVisitorProfileActivity;
import com.gss.chs.Activities.StaffProfileActivity;
import com.gss.chs.Model.StaffModel;
import com.gss.chs.Model.VisitorModel;
import com.gss.chs.R;

import java.util.ArrayList;

public class VisitorOutAdapter extends RecyclerView.Adapter<VisitorOutAdapter.MyViewHolder> {
    static Context context;
    Context actcontext;
    private ArrayList<VisitorModel> BusinessArrayList;

    public VisitorOutAdapter(Context context, ArrayList<VisitorModel> BusinessModelArrayList) {
        this.context = context;
        BusinessArrayList = BusinessModelArrayList;
    }

    @Override
    public void onBindViewHolder(final VisitorOutAdapter.MyViewHolder holder, int position) {
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
                Intent intent = new Intent(context, ExpectedVisitorProfileActivity.class);
                intent.putExtra("name",Items.getName());
                intent.putExtra("mobile",Items.getMobile_number());

                intent.putExtra("date",Items.getDate());

                intent.putExtra("flatNo",Items.getUser_flatnumber());

                intent.putExtra("inTime",Items.getIn_time());

                intent.putExtra("outTime",Items.getOutTime());

                intent.putExtra("purpose",Items.getPurpose());
                intent.putExtra("wing_name",Items.getWing_name());

//                intent.putExtra("cb_id", Items.getCb_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public VisitorOutAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewgroup, final int position) {
        View layout = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.visitor_layout, viewgroup, false);
        VisitorOutAdapter.MyViewHolder holder = new VisitorOutAdapter.MyViewHolder(layout);
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