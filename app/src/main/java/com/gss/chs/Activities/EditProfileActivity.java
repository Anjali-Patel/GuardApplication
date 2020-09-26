package com.gss.chs.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gss.chs.Adapters.VisitorAdapter;
import com.gss.chs.Model.VisitorModel;
import com.gss.chs.R;
import com.gss.chs.Utility.CheckNetwork;
import com.gss.chs.Utility.CommonUtils;
import com.gss.chs.Utility.SharedPreferenceUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class EditProfileActivity extends AppCompatActivity {
    Button edit_profile,save;
    Toolbar toolbar;
    EditText mobile_number,name,date,flat_number,in_time,out_time,purpose;
    ImageView pPhoto;
    String am_pm = "",date_time,str_outTime,str_inTime,date_time1;
    int hour,  minutes;
    int mYear;
    int mMonth;
    int mDay;
    String dbname,sci_id,guard_id;
    FrameLayout progressBarHolder;
String str_name,str_date,str_intime,str_outtime,str_flatnumber,str_purpose,str_mobilenumber,str_wing,str_outDateTime="",str_visitor_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edit_profile=findViewById(R.id.edit_profile);

        str_visitor_id=getIntent().getStringExtra("visitor_id");
        str_name=getIntent().getStringExtra("name");
        str_date=getIntent().getStringExtra("date");
        str_intime=getIntent().getStringExtra("inTime");
        str_outtime=getIntent().getStringExtra("outTime");
        str_flatnumber=getIntent().getStringExtra("flatNo");
        str_purpose=getIntent().getStringExtra("purpose");
        str_wing=getIntent().getStringExtra("wing_name");
        str_mobilenumber=getIntent().getStringExtra("mobile");
        progressBarHolder=findViewById(R.id.progressBarHolder);
        save=findViewById(R.id.save);
        pPhoto=findViewById(R.id.pPhoto);
        mobile_number=findViewById(R.id.mobile_number);
        name=findViewById(R.id.name);
        date=findViewById(R.id.date);
        flat_number=findViewById(R.id.flat_number);
        in_time=findViewById(R.id.in_time);
        out_time=findViewById(R.id.out_time);
        purpose=findViewById(R.id.purpose);
        guard_id = SharedPreferenceUtils.getInstance(EditProfileActivity.this).getStringValue(CommonUtils.GUARD_ID, "");
        dbname = SharedPreferenceUtils.getInstance(EditProfileActivity.this).getStringValue(CommonUtils.DBNAME, "");
        sci_id = SharedPreferenceUtils.getInstance(EditProfileActivity.this).getStringValue(CommonUtils.SOCIETY, "");
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditProfileActivity.this,"Now You can update your profile",Toast.LENGTH_LONG).show();
                mobile_number.setFocusableInTouchMode(true);
                name.setFocusableInTouchMode(true);
                date.setFocusableInTouchMode(true);
                flat_number.setFocusableInTouchMode(true);
//                in_time.setFocusableInTouchMode(true);
//                out_time.setFocusableInTouchMode(true);
                purpose.setFocusableInTouchMode(true);
                pPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (isReadStoragePermissionGranted() && isWriteStoragePermissionGranted()) {
                            selectImage();
                        } else if (!isReadStoragePermissionGranted() && !isWriteStoragePermissionGranted()) {
                            Toast.makeText(EditProfileActivity.this, "Please give permission first", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                in_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        datePicker();
//
//                        Calendar datetime = Calendar.getInstance();
//                        datetime.set(Calendar.HOUR_OF_DAY, hour);
//                        datetime.set(Calendar.MINUTE, minutes);
//                        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
//                            am_pm = "AM";
//                        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
//                            am_pm = "PM";
//                        TimePickerDialog picker = new TimePickerDialog(EditProfileActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
//                                new TimePickerDialog.OnTimeSetListener() {
//                                    @Override
//                                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
//                                        in_time.setText(sHour + ":" + sMinute+am_pm);
//                                    }
//                                }, 2, 3, false);
//                        picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                        picker.show();
                    }
                });
                out_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        datePicker1();
//                        Calendar datetime = Calendar.getInstance();
//                        datetime.set(Calendar.HOUR_OF_DAY, hour);
//                        datetime.set(Calendar.MINUTE, minutes);
//                        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
//                            am_pm = "AM";
//                        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
//                            am_pm = "PM";
//                        TimePickerDialog picker = new TimePickerDialog(EditProfileActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
//                                new TimePickerDialog.OnTimeSetListener() {
//                                    @Override
//                                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
//                                        out_time.setText(sHour + ":" + sMinute+am_pm);
//                                    }
//                                }, 2, 3, false);
//                        picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                        picker .show();
                    }
                });


            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(str_outDateTime.equalsIgnoreCase("")){
                    Toast.makeText(EditProfileActivity.this,"Please enter out time",Toast.LENGTH_LONG).show();

                } else  if (CheckNetwork.isInternetAvailable(EditProfileActivity.this)) {
                    saveOutTime();
                }else{
                    Toast.makeText(EditProfileActivity.this, "No Internet Connection.Please Check Your Intent Connection", Toast.LENGTH_LONG).show();
                }

            }
        });
        name.setText(str_name);
        mobile_number.setText(str_mobilenumber);
        date.setText(str_date);
        flat_number.setText(str_wing+","+str_flatnumber);
        in_time.setText(str_intime);
        out_time.setText(str_outtime);
        purpose.setText(str_purpose);

    }
    private void selectImage() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }
    public boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("", "Permission is granted1");
                return true;
            } else {

                Log.v("", "Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("", "Permission is granted1");
            return true;
        }
    }

    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("", "Permission is granted2");
                return true;
            } else {

                Log.v("", "Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("", "Permission is granted2");
            return true;
        }
    }
    private void datePicker1(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time1 = year+ "-" + (monthOfYear + 1) + "-" +dayOfMonth ;
                        //*************Call Time Picker Here ********************
                        tiemPicker1();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    private void tiemPicker1(){
        Calendar datetime = Calendar.getInstance();

        datetime.set(Calendar.HOUR_OF_DAY, hour);
        datetime.set(Calendar.MINUTE, minutes);

        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";

        TimePickerDialog picker = new TimePickerDialog(EditProfileActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        str_outTime=sHour + ":" + sMinute;
                        str_outDateTime=date_time1  +"  "  +"  "+str_outTime;
                        out_time.setText(str_outDateTime);
                    }
                }, 2, 3, false);
        picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        picker .show();
    }
    private void datePicker(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
                        tiemPicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    private void tiemPicker(){
        Calendar datetime = Calendar.getInstance();

        datetime.set(Calendar.HOUR_OF_DAY, hour);
        datetime.set(Calendar.MINUTE, minutes);

        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";

        TimePickerDialog picker = new TimePickerDialog(EditProfileActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        str_inTime=sHour + ":" + sMinute+am_pm;
                        in_time.setText(date_time  +"  "  +"  "+str_inTime);
                    }
                }, 2, 3, false);
        picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        picker .show();
    }
    private void saveOutTime(){
        progressBarHolder.setVisibility(View.VISIBLE);
        String url =(CommonUtils.BASE_URL)+"visitorlogoff";
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("sctid", sci_id)
                .add("dbname", dbname)
                .add("guardid", guard_id)
                .add("vstrid", str_visitor_id)
                .add("logouttime", str_outDateTime)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        call.cancel();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody responseBody = response.body();
                        final String myResponse = responseBody.string();
                        runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        JSONObject json = null;
                                        try {
                                            json = new JSONObject(myResponse);
                                            String success = json.getString("response");
                                            if (success.equalsIgnoreCase("success")) {
                                                progressBarHolder.setVisibility(View.GONE);
                                                Intent intent= new Intent(EditProfileActivity.this,NavigationDrawaerActictivity.class);
                                                Toast.makeText(EditProfileActivity.this,json.getString("message"),Toast.LENGTH_LONG).show();
                                                startActivity(intent);
                                            } else {
                                                progressBarHolder.setVisibility(View.GONE);
                                                Toast.makeText(EditProfileActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                });
    }



}
