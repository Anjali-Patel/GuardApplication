package com.gss.chs.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gss.chs.Model.SocietyModel;
import com.gss.chs.R;
import com.gss.chs.Utility.CheckNetwork;
import com.gss.chs.Utility.CommonUtils;
import com.gss.chs.Utility.RestJsonClient;
import com.gss.chs.Utility.SharedPreferenceUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {
    LinearLayout login_button;
    TextView forgot;
EditText pasword,email;
Spinner society_spinner;
    String selectedSociety;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
String str_email,str_psw;
ArrayList<String> society_list;
String selectedSocietyId="";
    SharedPreferenceUtils preferances;
ArrayList<SocietyModel> SocietyModelList;
ArrayList<SocietyModel>SocietiesArrayListTmp;
String dbname;
    String[] allReqPermissions =
            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_PHONE_STATE};
    public final static int PERMISSIONS_REQUEST_CODE_1 = 2;
    FrameLayout progressBarHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_button=findViewById(R.id.login_button);
        forgot=findViewById(R.id.forgot);
        pasword=findViewById(R.id.word);
        email=findViewById(R.id.email);
        progressBarHolder=findViewById(R.id.progressBarHolder);
        society_list= new ArrayList<>();
        SocietyModelList= new ArrayList<>();
        SocietiesArrayListTmp=new ArrayList<>();
        checkAllNecessaryPermissions();
        if (CheckNetwork.isInternetAvailable(LoginActivity.this)) {
            getSociety();
        }else{
            Toast.makeText(LoginActivity.this, "No Internet Connection.Please Check Your Intent Connection", Toast.LENGTH_LONG).show();
        }

        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        dbname = SharedPreferenceUtils.getInstance(LoginActivity.this).getStringValue(CommonUtils.DBNAME, "");

        preferances=SharedPreferenceUtils.getInstance(LoginActivity.this);
        society_spinner=findViewById(R.id.society_spinner);
//        new GetProfileTask(LoginActivity.this).execute();
        society_list.add("Select Society");
        ArrayAdapter<String> Societyadapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,society_list);
        society_spinner.setAdapter(Societyadapter);
        society_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SelectedType = parent.getItemAtPosition(position).toString();

                for(int i = 0; i<SocietyModelList.size();i++) {
                    if (SocietyModelList.get(i).getName().contains(SelectedType)) {
                        SocietiesArrayListTmp.add(SocietyModelList.get(i));
                        selectedSocietyId = SocietyModelList.get(position - 1).getSociety_id();

                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

                 forgot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (email.getText().toString().trim() == null ||  email.getText().toString().trim().equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.create();
                        builder.setMessage("Please enter Email id!");

                        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                        builder.show();

                    }
                    else if (society_spinner.getSelectedItem().toString().equalsIgnoreCase("Select society")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.create();
                        builder.setMessage("Please select society!");

                        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                        builder.show();

                    }
                    else
                    {
                        ForgotPassword();
                    }
                }
            });
                login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    str_email = email.getText().toString().trim();
                    str_psw = pasword.getText().toString().trim();
                    if(str_email.equalsIgnoreCase("")){
     Toast.makeText(LoginActivity.this,"Please enter email address",Toast.LENGTH_LONG).show();
                    }else if(str_psw.equalsIgnoreCase("")){
        Toast.makeText(LoginActivity.this,"Please enter password",Toast.LENGTH_LONG).show();

  } else if(society_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Society")){
    Toast.makeText(LoginActivity.this,"Please select society",Toast.LENGTH_LONG).show();

  } else  if (CheckNetwork.isInternetAvailable(LoginActivity.this)) {
                        guardLogin();
                    }else{
                        Toast.makeText(LoginActivity.this, "No Internet Connection.Please Check Your Intent Connection", Toast.LENGTH_LONG).show();
                    }


//  else{
//    guardLogin();





                }
            });
            }
            private void getSociety() {
        String url1 = (CommonUtils.BASE_URL+"list_society");
        OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("request_type", "1")
                        .build();
                Request request = new Request.Builder()
                        .url(url1)
                        .post(formBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
//                        Toast.makeText(LoginActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody responseBody = response.body();
                        final String myResponse = responseBody.string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject json = null;
                                try {
                                    json = new JSONObject(myResponse);
                                    String response = json.getString("response");
                                    if (response.equalsIgnoreCase("success")) {
                                        JSONArray json2 = json.getJSONArray("list_society");
                                        for (int i = 0; i < json2.length(); i++) {
                                            JSONObject SocietyList = json2.getJSONObject(i);
                                            JSONObject SocietyListfirst = json2.getJSONObject(0);


//                                      JSONObject RegionObj = RegionList.getJSONObject("category");
                                            SocietyModel model = new SocietyModel();
                                            model.setName(SocietyList.getString("sct_name"));
                                            model.setDatabase_name(SocietyList.getString("databasename"));
                                            model.setSociety_id(SocietyList.getString("sct_id"));
                                            String a=SocietyListfirst.getString("sct_id");
                                            String b=SocietyListfirst.getString("databasename");
                                            SharedPreferenceUtils.getInstance(LoginActivity.this).setValue(CommonUtils.SOCIETY, a);
                                            SharedPreferenceUtils.getInstance(LoginActivity.this).setValue(CommonUtils.DBNAME, b);
                                            SocietyModelList.add(model);
                                        }
                                        for (int i = 0; i < SocietyModelList.size(); i++) {
                                            final SocietyModel Items = SocietyModelList.get(i);
                                            society_list.add(Items.getName());
                                        }
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this,"somthing went wrong",Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.i("Scan", "Exception-" +e);

                                }
                            }
                        });
                    }
                });

            }
            private void guardLogin(){
                progressBarHolder.setVisibility(View.VISIBLE);

                String url1 = (CommonUtils.BASE_URL+"guardlogin");
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("userid", str_email)
                        .add("password", str_psw)
                        .add("sctid",selectedSocietyId )
                        .add("dbname", dbname)
                        .build();

                Request request = new Request.Builder()
                        .url(url1)
                        .post(formBody)
                        .build();



                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
//                        Toast.makeText(LoginActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody responseBody = response.body();
                        final String myResponse = responseBody.string();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject json = null;
                                try {
                                    json = new JSONObject(myResponse);
                                    String response = json.getString("response");
                                    if (response.equalsIgnoreCase("success")) {
                                      JSONArray guard=json.getJSONArray("guarddetails");
                                       JSONObject guard_object=guard.getJSONObject(0);
                                        String guard_id=guard_object.getString("id");
                                        String guard_name=guard_object.getString("grd_name");
                                        String guard_email=guard_object.getString("grd_email");
                                        String guard_image=guard_object.getString("grd_photo");
                                        String society_mobile_number=guard_object.getString("grd_mobile");
                                        SharedPreferenceUtils.getInstance(LoginActivity.this).setValue(CommonUtils.SOCIETY_MOBILE, society_mobile_number);
                                        SharedPreferenceUtils.getInstance(LoginActivity.this).setValue(CommonUtils.GUARD_ID, guard_id);
                                        SharedPreferenceUtils.getInstance(LoginActivity.this).setValue(CommonUtils.GUARD_NAME, guard_name);
                                        SharedPreferenceUtils.getInstance(LoginActivity.this).setValue(CommonUtils.GUARD_EMAIL, guard_email);
                                        SharedPreferenceUtils.getInstance(LoginActivity.this).setValue(CommonUtils.GUARD_IMAGE, guard_image);
                                        progressBarHolder.setVisibility(View.GONE);
                                        Intent intent = new Intent(LoginActivity.this, NavigationDrawaerActictivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        progressBarHolder.setVisibility(View.GONE);
                                        Toast.makeText(LoginActivity.this,json.getString("message"),Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }
                        });
                    }
                });



            }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.create();
        builder.setMessage("Want to Exit ?");

        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });


        builder.show();
    }

    public void ForgotPassword() {

        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);
        final String Email = email.getText().toString();
        String url = (CommonUtils.BASE_URL)+"forgetpassword";

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("member_email", Email)
                .add("dbname", preferances.getStringValue(CommonUtils.DBNAME,""))

                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                final String myResponse = responseBody.string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(myResponse);

                            String success = json.getString("response");


                            if (success.equalsIgnoreCase("success")) {

                                outAnimation = new AlphaAnimation(1f, 0f);
                                outAnimation.setDuration(200);
                                progressBarHolder.setAnimation(outAnimation);
                                progressBarHolder.setVisibility(View.GONE);

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.create();
                                builder.setMessage("Your Passoword is mailed successfully on your registered email Id!");

                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                builder.show();            }

                            else if (success.equalsIgnoreCase("failure")){

                                outAnimation = new AlphaAnimation(1f, 0f);
                                outAnimation.setDuration(200);
                                progressBarHolder.setAnimation(outAnimation);
                                progressBarHolder.setVisibility(View.GONE);

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.create();
                                builder.setMessage("Invalid EmailId!");

                                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                builder.show();
                            }


                            else{
                                outAnimation = new AlphaAnimation(1f, 0f);
                                outAnimation.setDuration(200);
                                progressBarHolder.setAnimation(outAnimation);
                                progressBarHolder.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });




    }
    public void checkAllNecessaryPermissions () {

        ArrayList<String> requiredPermissionsList = new ArrayList<>();

        for (String permission : allReqPermissions){
            if (!checkPermission(permission)){
                requiredPermissionsList.add(permission);
            }
        }

        if(requiredPermissionsList.size() > 0){
            String[] requiredPermissions = requiredPermissionsList.toArray(new String[requiredPermissionsList.size()]);

            ActivityCompat.requestPermissions(LoginActivity.this, requiredPermissions, PERMISSIONS_REQUEST_CODE_1);
        }

    }

    private boolean checkPermission(String permission) {
        int permissionState = ActivityCompat.checkSelfPermission(LoginActivity.this, permission);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
}

