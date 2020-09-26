package com.gss.chs.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gss.chs.R;
import com.gss.chs.Utility.CommonUtils;
import com.gss.chs.Utility.SharedPreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class StaffProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    FrameLayout progressBarHolder;
    TextView mobile_number,staff_name,service_name,address,in_time,out_time,email,occupation;
    ImageView pPhoto;
    String dbname,sci_id;
    String str_name,str_occupation,str_intime,str_outtime,str_email,str_address,str_mobilenumber,str_service_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        staff_name=findViewById(R.id.staff_name);
        occupation=findViewById(R.id.occupation);
        progressBarHolder=findViewById(R.id.progressBarHolder);
        pPhoto=findViewById(R.id.pPhoto);
        mobile_number=findViewById(R.id.mobile_number);
        service_name=findViewById(R.id.service_name);
        address=findViewById(R.id.address);
        in_time=findViewById(R.id.in_time);
        out_time=findViewById(R.id.out_time);
        email=findViewById(R.id.email);
        str_name=getIntent().getStringExtra("name");
        str_occupation=getIntent().getStringExtra("title");
//        str_intime=getIntent().getStringExtra("inTime");
//        str_outtime=getIntent().getStringExtra("outTime");
        str_email=getIntent().getStringExtra("Email");
        str_address=getIntent().getStringExtra("address");
        str_service_name=getIntent().getStringExtra("service_name");
        str_mobilenumber=getIntent().getStringExtra("mobile");
        staff_name.setText(str_name);
        mobile_number.setText(str_mobilenumber);
        occupation.setText(str_occupation);
        service_name.setText(str_service_name);
        address.setText(str_address);
        email.setText(str_email);

//        in_time.setText(str_intime);
//        out_time.setText(str_outtime);
        dbname = SharedPreferenceUtils.getInstance(StaffProfileActivity.this).getStringValue(CommonUtils.DBNAME, "");
        sci_id = SharedPreferenceUtils.getInstance(StaffProfileActivity.this).getStringValue(CommonUtils.SOCIETY, "");


    }

   }
