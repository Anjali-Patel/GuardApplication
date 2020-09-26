package com.gss.chs.Adapters;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gss.chs.Activities.EditProfileActivity;
import com.gss.chs.Activities.RegularVisitorProfileActivity;
import com.gss.chs.Activities.StaffEditProfileActivity;
import com.gss.chs.Model.StaffModel;
import com.gss.chs.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.MyViewHolder> {
    static Context context;
    Context actcontext;
    private ArrayList<StaffModel> BusinessArrayList;

    public StaffAdapter(Context context, ArrayList<StaffModel> BusinessModelArrayList) {
        this.context = context;
        BusinessArrayList = BusinessModelArrayList;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
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
                Intent intent = new Intent(context, StaffEditProfileActivity.class);
                intent.putExtra("name",Items.getName());
                intent.putExtra("mobile",Items.getMobileNumber());
                intent.putExtra("title",Items.getOccupation());

                intent.putExtra("service_name",Items.getServiceName());
                intent.putExtra("staff_id",Items.getStaff_id());
//                intent.putExtra("inTime",Items.getDate());
//
//                intent.putExtra("outTime",Items.getOutTime());

                intent.putExtra("address",Items.getAddress());
                intent.putExtra("Email",Items.getEmail());


                context.startActivity(intent);
            }
        });

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewgroup, final int position) {
        View layout = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.staff_layout, viewgroup, false);
        MyViewHolder holder = new MyViewHolder(layout);

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