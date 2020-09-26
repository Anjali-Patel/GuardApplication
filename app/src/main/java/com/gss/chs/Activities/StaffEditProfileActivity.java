package com.gss.chs.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
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

import com.gss.chs.Adapters.StaffOutAdapter;
import com.gss.chs.Model.StaffModel;
import com.gss.chs.R;
import com.gss.chs.Utility.CommonUtils;
import com.gss.chs.Utility.MyDbHandler;
import com.gss.chs.Utility.SharedPreferenceUtils;
import com.gss.chs.Utility.Temprory;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.icu.text.MessagePattern.ArgType.SELECT;

public class StaffEditProfileActivity extends AppCompatActivity {
    Button edit_profile,save;
    Toolbar toolbar;
    FrameLayout progressBarHolder;
    EditText mobile_number,staff_name,service_name,address,in_time,out_time,email,occupation;
    ImageView pPhoto;
    String am_pm = "",date_time,str_outTime,str_inTime,date_time1;
    int hour,  minutes;
    int mYear;
    int mMonth;
    int mDay;

    MyDbHandler myDbHandler;
    String dbname,sci_id;
    String str_staff_id,str_name,str_occupation,str_intime="",str_outtime="",str_email,str_address,str_mobilenumber,str_service_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_edit_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edit_profile=findViewById(R.id.edit_profile);
        save=findViewById(R.id.save);
        progressBarHolder=findViewById(R.id.progressBarHolder);
        myDbHandler = new MyDbHandler(StaffEditProfileActivity.this);
        Temprory.setMyDbHandler(myDbHandler);
      occupation=findViewById(R.id.occupation);
        pPhoto=findViewById(R.id.pPhoto);
        mobile_number=findViewById(R.id.mobile_number);
        staff_name=findViewById(R.id.staff_name);
        service_name=findViewById(R.id.service_name);
        address=findViewById(R.id.address);
        in_time=findViewById(R.id.in_time);
        out_time=findViewById(R.id.out_time);
        email=findViewById(R.id.email);
        str_staff_id=getIntent().getStringExtra("staff_id");

//                intent.putExtra("inTime",Items.getDate());
//
//                intent.putExtra("outTime",Items.getOutTime());




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

        dbname = SharedPreferenceUtils.getInstance(StaffEditProfileActivity.this).getStringValue(CommonUtils.DBNAME, "");
        sci_id = SharedPreferenceUtils.getInstance(StaffEditProfileActivity.this).getStringValue(CommonUtils.SOCIETY, "");

        in_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
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








//        getStaffinDetail();
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StaffEditProfileActivity.this,"Now You can update your profile",Toast.LENGTH_LONG).show();
                mobile_number.setFocusableInTouchMode(true);
                staff_name.setFocusableInTouchMode(true);
                service_name.setFocusableInTouchMode(true);
                address.setFocusableInTouchMode(true);
//                in_time.setFocusableInTouchMode(true);
//                out_time.setFocusableInTouchMode(true);
                email.setFocusableInTouchMode(true);
                pPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isReadStoragePermissionGranted() && isWriteStoragePermissionGranted()) {
                            selectImage();
                        } else if (!isReadStoragePermissionGranted() && !isWriteStoragePermissionGranted()) {
                            Toast.makeText(StaffEditProfileActivity.this, "Please give permission first", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


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
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(str_intime.equalsIgnoreCase("")){
//                    Toast.makeText(StaffEditProfileActivity.this, "Please enter in time ", Toast.LENGTH_LONG).show();
//                }else{
//                    SharedPreferenceUtils.getInstance(StaffEditProfileActivity.this).setValue(CommonUtils.IN_TIME, str_intime);
//                    Toast.makeText(StaffEditProfileActivity.this, "In time saved successfully ", Toast.LENGTH_LONG).show();
//
//
//                }
////                if(str_outtime.equalsIgnoreCase("")){
////                    Toast.makeText(StaffEditProfileActivity.this, "Please enter  out in time ", Toast.LENGTH_LONG).show();
////
////                }else{
////                    SharedPreferenceUtils.getInstance(StaffEditProfileActivity.this).setValue(CommonUtils.OUT_TIME, str_outtime);
////                    out_time.setText(SharedPreferenceUtils.getInstance(StaffEditProfileActivity.this).getStringValue(CommonUtils.OUT_TIME, ""));
////
////
////                }
//
//
//            }
//
//
//
//
//        });

//        in_time.setText(SharedPreferenceUtils.getInstance(StaffEditProfileActivity.this).getStringValue(CommonUtils.IN_TIME, ""));

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

        DatePickerDialog datePickerDialog = new DatePickerDialog(StaffEditProfileActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time1 =year+ "-" + (monthOfYear + 1) + "-" +dayOfMonth ;
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

        TimePickerDialog picker = new TimePickerDialog(StaffEditProfileActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        str_outTime=sHour + ":" + sMinute;
                        str_outtime=date_time1  +"  "  +"  "+str_outTime;
                        out_time.setText(str_outtime);
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(StaffEditProfileActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_time =year+ "-" + (monthOfYear + 1) + "-" +dayOfMonth ;
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
        TimePickerDialog picker = new TimePickerDialog(StaffEditProfileActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        str_inTime=sHour + ":" + sMinute;
                        str_intime=date_time  +"  "  +"  "+str_inTime;
                        in_time.setText(str_intime);
                    }
                }, 2, 3, false);
        picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        picker .show();
    }

}
