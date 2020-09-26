package com.gss.chs.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gss.chs.R;

public class ExpectedVisitorProfileActivity extends AppCompatActivity {
Toolbar toolbar;
TextView name,mobile_number,date,flat_number,purpose;
EditText out_time,in_time;
ImageView pPhoto;
FrameLayout progressBarHolder;
    String str_name,str_date,str_intime,str_outtime,str_flatnumber,str_purpose,str_mobilenumber,str_wing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expected_visitor_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name=findViewById(R.id.name);
        pPhoto=findViewById(R.id.pPhoto);
        in_time=findViewById(R.id.in_time);
        out_time=findViewById(R.id.out_time);
        progressBarHolder=findViewById(R.id.progressBarHolder);
        mobile_number=findViewById(R.id.mobile_number);
        date=findViewById(R.id.date);
        flat_number=findViewById(R.id.flat_number);
        purpose=findViewById(R.id.purpose);
        str_name=getIntent().getStringExtra("name");
        str_date=getIntent().getStringExtra("date");
        str_intime=getIntent().getStringExtra("inTime");
        str_outtime=getIntent().getStringExtra("outTime");
        str_flatnumber=getIntent().getStringExtra("flatNo");
        str_purpose=getIntent().getStringExtra("purpose");
        str_wing=getIntent().getStringExtra("wing_name");
        str_mobilenumber=getIntent().getStringExtra("mobile");
        name.setText(str_name);
        mobile_number.setText(str_mobilenumber);
        date.setText(str_date);
        flat_number.setText(str_wing + "," + str_flatnumber);
        in_time.setText(str_intime);
        out_time.setText(str_outtime);
        purpose.setText(str_purpose);
    }

    }

