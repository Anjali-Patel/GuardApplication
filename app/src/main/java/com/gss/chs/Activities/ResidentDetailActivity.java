package com.gss.chs.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gss.chs.R;

public class ResidentDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView name,mobile_number,date,flat_number,purpose,mailing_address,allocated_work;
    EditText out_time,in_time;
    ImageView pPhoto;
    FrameLayout progressBarHolder;
    String str_name,str_date,str_flatnumber,str_purpose,str_mobilenumber,str_wing,str_allocated_work,str_mailing_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_detail);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name=findViewById(R.id.name);
        progressBarHolder=findViewById(R.id.progressBarHolder);
        pPhoto=findViewById(R.id.pPhoto);
        in_time=findViewById(R.id.in_time);
        allocated_work=findViewById(R.id.allocated_work);
        mailing_address=findViewById(R.id.mailing_address);
        out_time=findViewById(R.id.out_time);
        mobile_number=findViewById(R.id.mobile_number);
        date=findViewById(R.id.date);
        flat_number=findViewById(R.id.flat_number);
        purpose=findViewById(R.id.purpose);
        str_name=getIntent().getStringExtra("name");
        str_allocated_work=getIntent().getStringExtra("allocated_work");
        str_mailing_address=getIntent().getStringExtra("mailing_aadress");
        str_date=getIntent().getStringExtra("date");
//        str_intime=getIntent().getStringExtra("inTime");
//        str_outtime=getIntent().getStringExtra("outTime");
        str_flatnumber=getIntent().getStringExtra("flatNo");
        str_purpose=getIntent().getStringExtra("purpose");
        str_wing=getIntent().getStringExtra("wing_name");
        str_mobilenumber=getIntent().getStringExtra("mobile");
        name.setText(str_name);
        mobile_number.setText(str_mobilenumber);
        date.setText(str_date);
        flat_number.setText(str_wing+","+str_flatnumber);
        allocated_work.setText(str_allocated_work);
        mailing_address.setText(str_mailing_address);

//        in_time.setText(str_intime);
//        out_time.setText(str_outtime);
        purpose.setText(str_purpose);
    }




}

