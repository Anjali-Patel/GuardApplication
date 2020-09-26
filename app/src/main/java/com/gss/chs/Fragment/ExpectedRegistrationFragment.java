package com.gss.chs.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gss.chs.Activities.LoginActivity;
import com.gss.chs.Model.FlatNumberModel;
import com.gss.chs.Model.WingModel;
import com.gss.chs.R;
import com.gss.chs.Utility.CheckNetwork;
import com.gss.chs.Utility.CommonUtils;
import com.gss.chs.Utility.MyUtility;
import com.gss.chs.Utility.RestJsonClient;
import com.gss.chs.Utility.SharedPreferenceUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpectedRegistrationFragment extends Fragment {
    private static final int SELECT_FILE = 100;
    private static final int REQUEST_CAMERA = 1888;
    Bitmap theImage;
    int mYear;
    int mMonth;
    Button       check_visitor;
    String date_time_in;
    int mDay;
    FrameLayout progressBarHolder;
    Button save;
    String userChoosenTask,visitor_type,str_inTime,imageurl="",guard_id,date_time;
    String sci_id,dbname;
    TimePickerDialog picker;
    String am_pm = "";
    int hour,  minutes;
    Spinner society_spinner;
    ImageView img_camera;
    String    photo;
    Spinner select_wing,select_flat_number;
    ArrayList<String> wing_list;
    ArrayList<String> flat_list;
    String selectedWingId="",selectedFlatId="",SelectedVisitorId="";
    ArrayList<WingModel> WingModelList;
    ArrayList<FlatNumberModel> FlatNoModelList;
    ArrayList<WingModel> WingListTemp;
    ArrayList<String> FlatNoListTemp;
    ArrayList<String>VisitorType;
    EditText name,mobile_number,in_time,out_time,purpose;
    String str_name,str_mobile,str_purpose;
//    String []VisitorType={"Select Visitor Type","New Visitor","Regular Visitor","Expected Visitor"};

    public ExpectedRegistrationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_expected_registration, container, false);
        img_camera=view.findViewById(R.id.img_camera);
        name=view.findViewById(R.id.name);
        check_visitor=view.findViewById(R.id.check_visitor);
        VisitorType= new ArrayList<>();
        VisitorType.add("Select Visitor Type");
        VisitorType.add("New Visitor");

        VisitorType.add("Regular Visitor");

        VisitorType.add("Expected Visitor");

        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        guard_id = SharedPreferenceUtils.getInstance(getContext()).getStringValue(CommonUtils.GUARD_ID, "");
        mobile_number=view.findViewById(R.id.mobile_number);
        in_time=view.findViewById(R.id.in_time);
        out_time=view.findViewById(R.id.out_time);
        save=view.findViewById(R.id.save);
//        check_visitor=view.findViewById(R.id.check_visitor);
        purpose=view.findViewById(R.id.purpose);
        society_spinner=view.findViewById(R.id.society_spinner);
        select_wing=view.findViewById(R.id.select_wing);
        select_flat_number=view.findViewById(R.id.select_flat_number);
        dbname = SharedPreferenceUtils.getInstance(getContext()).getStringValue(CommonUtils.DBNAME, "");
        sci_id = SharedPreferenceUtils.getInstance(getContext()).getStringValue(CommonUtils.SOCIETY, "");
        wing_list=new ArrayList<>();
        flat_list=new ArrayList<>();
        WingModelList=new ArrayList<>();
        FlatNoModelList=new ArrayList<>();
        WingListTemp=new ArrayList<>();
        FlatNoListTemp=new ArrayList<String>();
        wing_list.add("Select Wing");
        flat_list.add("Select Flat No.");
        FlatNoListTemp.add("Select Flat No.");
          if (CheckNetwork.isInternetAvailable(getContext())) {
              getWing();
        }else{
            Toast.makeText(getContext(), "No Internet Connection.Please Check Your Intent Connection", Toast.LENGTH_LONG).show();
        }
          ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, VisitorType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        society_spinner.setAdapter(adapter);
        society_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SelectedVisitorId=VisitorType.get(i);
                if(SelectedVisitorId.equalsIgnoreCase("New Visitor")){
                    visitor_type="1";
                }else if(SelectedVisitorId.equalsIgnoreCase("Regular Visitor")){
                    visitor_type="2";
                }else if(SelectedVisitorId.equalsIgnoreCase("Expected Visitor")){
                    visitor_type="3";
                }else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        in_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePicker();
            }
        });
        out_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar datetime = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, hour);
                datetime.set(Calendar.MINUTE, minutes);

                if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";

                TimePickerDialog picker = new TimePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                out_time.setText(sHour + ":" + sMinute+am_pm);
                            }
                        }, 2, 3, false);
                picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                picker .show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_name=name.getText().toString().trim();
                str_mobile=mobile_number.getText().toString().trim();

                str_purpose=purpose.getText().toString().trim();
if(str_name.equalsIgnoreCase("")){
    Toast.makeText(getContext(),"Please enter name",Toast.LENGTH_LONG).show();

}else if(str_mobile.equalsIgnoreCase("")){
    Toast.makeText(getContext(),"Please enter mobile number",Toast.LENGTH_LONG).show();

}else if(str_purpose.equalsIgnoreCase("")){
    Toast.makeText(getContext(),"Please enter purpose",Toast.LENGTH_LONG).show();

}
else if(select_wing.getSelectedItem().toString().equalsIgnoreCase("Select Wing")){
    Toast.makeText(getContext(),"Please select wing",Toast.LENGTH_LONG).show();

}else if(select_flat_number.getSelectedItem().toString().equalsIgnoreCase("Select Flat Number")){
    Toast.makeText(getContext(),"Please select flat number",Toast.LENGTH_LONG).show();

}else if(society_spinner.getSelectedItem().toString().equalsIgnoreCase("Selected Visitor type")){
    Toast.makeText(getContext(),"Please select visitor type",Toast.LENGTH_LONG).show();

} else{
    new PostUpdateAsync().execute(guard_id, imageurl, sci_id, dbname, str_name,str_mobile,selectedFlatId,selectedWingId,date_time_in,str_purpose,visitor_type);

}

            }
        });

        ArrayAdapter<String> FlatAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, flat_list);
        select_flat_number.setAdapter(FlatAdapter);
        select_flat_number.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SelectedFlatNumber = parent.getItemAtPosition(position).toString();
                for(int i = 0; i < FlatNoModelList.size();i++) {
                    if (FlatNoModelList.get(i).getFlat_number().contains(SelectedFlatNumber)) {
                        selectedFlatId = FlatNoModelList.get(position - 1).getFlat_id();

                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<String> WingAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, wing_list);
        select_wing.setAdapter(WingAdapter);
        select_wing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String SelectedWing = parent.getItemAtPosition(position).toString();
                    for (int i = 0; i < WingModelList.size(); i++) {
                        if (WingModelList.get(i).getWing_name().contains(SelectedWing)) {
                            selectedWingId = WingModelList.get(position - 1).getWing_id();
                            //getFlat();
                            for (int j = 0; j < FlatNoModelList.size(); j++) {
                                if (FlatNoModelList.get(j).getWing_id().equals(selectedWingId)) {
                                    final FlatNumberModel Items = FlatNoModelList.get(j);
                                    FlatNoListTemp.add(Items.getFlat_number());
                                }
                            }
                            flat_list.clear();
                            for(int k=0;k<FlatNoListTemp.size();k++){
                                flat_list.add(FlatNoListTemp.get(k));
                            }
                            FlatNoListTemp.clear();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();

            }
        });
//        check_visitor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                str_mobile=mobile_number.getText().toString().trim();
//                checkVisitor();
//
//
//
//            }
//        });
        check_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_mobile=mobile_number.getText().toString().trim();
                if(str_mobile.equalsIgnoreCase("")){
                    Toast.makeText(getContext(),"Please enter visitor's mobile number",Toast.LENGTH_LONG).show();
                } else  if (CheckNetwork.isInternetAvailable(getContext())) {
                    checkVisitor();
                }else{
                    Toast.makeText(getContext(), "No Internet Connection.Please Check Your Intent Connection", Toast.LENGTH_LONG).show();
                }

            }
        });

        /*mobile_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                str_mobile=mobile_number.getText().toString().trim();
                if (str_mobile.length()==10) {
                    checkVisitor();
                }

            }

        });*/

        return  view;
    }
    class PostUpdateAsync extends AsyncTask<String, String, JSONObject> {
        JSONObject json;
        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("guardid", params[0]));
                nameValuePairs.add(new BasicNameValuePair("sctid", params[2]));
                nameValuePairs.add(new BasicNameValuePair("dbname", params[3]));
                nameValuePairs.add(new BasicNameValuePair("vstrname", params[4]));
                nameValuePairs.add(new BasicNameValuePair("vstrmobile", params[5]));
                nameValuePairs.add(new BasicNameValuePair("flatno", params[6]));
                nameValuePairs.add(new BasicNameValuePair("wingid", params[7]));
                nameValuePairs.add(new BasicNameValuePair("vstrintime", params[8]));
                nameValuePairs.add(new BasicNameValuePair("vstrpurpose", params[9]));
                nameValuePairs.add(new BasicNameValuePair("vstrtype", params[10]));
                if(!imageurl.isEmpty()){
                    nameValuePairs.add(new BasicNameValuePair("vstrpic", params[1]));

                }else{
                    nameValuePairs.add(new BasicNameValuePair("vstrpic", ""));

                }
                String Url = (CommonUtils.BASE_URL)+"guardvisitorentry";
                json = RestJsonClient.post2(Url, nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarHolder.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            //{"success":"1","message":"Data saved successfully!"}
            if (jsonObject.has("response")) {
                try {
                    String success = jsonObject.getString("response");
                    if (success.equalsIgnoreCase("success")) {
                        progressBarHolder.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                        Fragment fragment2 = new DashBoardFragmentNew();
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.bodyFrag, fragment2);
//                        fragmentTransaction.addToBackStack(null);
////                    toolbar.setTitle("UABN Home");
//                        fragmentTransaction.commit();


                    } else {

                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();


                        progressBarHolder.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "Update post failed", Toast.LENGTH_LONG).show();
                progressBarHolder.setVisibility(View.GONE);


            }

        }
    }



    private void getWing(){
        WingModelList.clear();

        String url1 = (CommonUtils.BASE_URL+"winglist");
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("sctid",sci_id )
                .add("dbname", dbname)
                .build();
        Request request = new Request.Builder()
                .url(url1)
                .post(formBody)
                .build();

//                Request request = new Request.Builder()
//                        .url(url).get().build();

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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(myResponse);
                            String response = json.getString("response");
                            if (response.equalsIgnoreCase("success")) {
                                //winglist
                                JSONArray json2 = json.getJSONArray("winglist");
                                for (int i = 0; i < json2.length(); i++) {
                                    JSONObject SocietyList = json2.getJSONObject(i);
//                                          JSONObject RegionObj = RegionList.getJSONObject("category");
                                    WingModel model = new WingModel();
                                    model.setWing_name(SocietyList.getString("wing_name"));
                                    model.setWing_id(SocietyList.getString("id"));
                                    WingModelList.add(model);
                                }
                                for (int i = 0; i < WingModelList.size(); i++) {
                                    final WingModel Items = WingModelList.get(i);
                                    wing_list.add(Items.getWing_name());
                                }
                                //flatlisst
                                FlatNoModelList.clear();
                                JSONArray json3 = json.getJSONArray("flatlist");
                                for (int i = 0; i < json3.length(); i++) {
                                    JSONObject SocietyList = json3.getJSONObject(i);
//                                          JSONObject RegionObj = RegionList.getJSONObject("category");
                                    FlatNumberModel model = new FlatNumberModel();
                                    model.setFlat_number(SocietyList.getString("flat_no"));
                                    model.setFlat_id(SocietyList.getString("flatid"));
                                    model.setWing_id(SocietyList.getString("wingid"));
                                    FlatNoModelList.add(model);
                                }
//                            for (int i = 0; i < FlatNoModelList.size(); i++) {
//                                    final FlatNumberModel Items = FlatNoModelList.get(i);
//                                    flat_list.add(Items.getFlat_number());
//                                }
                            }else{
                                Toast.makeText(getActivity(),"somthing went wrong",Toast.LENGTH_LONG).show();
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

    private void selectImage() {
        Intent intent = CropImage.activity()
                .setAspectRatio(16,9)
                .getIntent(getContext());

        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .start(getActivity());
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if (requestcode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (requestcode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.e("resultUri ->", String.valueOf(resultUri));
                img_camera.setImageURI(resultUri);
            } else if (resultcode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("error ->", String.valueOf(error));
            }
        }
    }
    private void datePicker(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_time = year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth;
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

        TimePickerDialog picker = new TimePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        str_inTime=sHour + ":" + sMinute;
                        date_time_in=date_time  +"  "  +"  "+str_inTime;
                        in_time.setText(date_time_in);
                    }
                }, 2, 3, false);
        picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        picker .show();
    }
    private void checkVisitor(){
        getActivity().runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        progressBarHolder.setVisibility(View.VISIBLE);
                    }
                }
        );

        String url =(CommonUtils.BASE_URL)+"checkvisitor";
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("sctid", sci_id)
                .add("dbname", dbname)
                .add("vstrmobile", str_mobile)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        getActivity().runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBarHolder.setVisibility(View.GONE);
                                    }
                                }
                        );
                        call.cancel();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody responseBody = response.body();
                        final String myResponse = responseBody.string();
                        getActivity().runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        JSONObject json = null;
                                        progressBarHolder.setVisibility(View.GONE);
                                        try {
                                            json = new JSONObject(myResponse);
                                            String success = json.getString("response");
                                            if (success.equalsIgnoreCase("success")) {
                                                JSONArray update = json.getJSONArray("visitordetail");
                                                if(update.length() > 0) {
                                                    JSONObject Memberdetail = update.getJSONObject(0);
                                                    name.setText(Memberdetail.getString("vstr_name").replace("null", ""));
                                                    mobile_number.setText(Memberdetail.getString("vstr_mobile").replace("null", ""));
                                                    purpose.setText(Memberdetail.getString("vstr_purpose").replace("null", ""));
                                                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Memberdetail.getString("vstr_date"));
                                                    String newString = new SimpleDateFormat("MMM dd yyyy").format(date); // 25-03-2019
                                                    in_time.setText(newString);
                                                    visitor_type = Memberdetail.getString("vstr_type");
                                                    selectedFlatId=Memberdetail.getString("vstr_flatno");
                                                    selectedWingId=Memberdetail.getString("vstr_wing");
//                                                    if(selectedFlatId != null) {
//                                                        select_flat_number.setSelection(Integer.parseInt(selectedFlatId));
//                                                    }
//                                                    if(selectedWingId != null) {
//                                                        select_wing.setSelection(Integer.parseInt((selectedWingId)));
//                                                    }

                                                    if(visitor_type != null) {
                                                        society_spinner.setSelection(Integer.parseInt(visitor_type));
                                                    }
                                                    /*for(int j = 0; j < VisitorType.size();j++) {
                                                        if (VisitorType.get(j).equalsIgnoreCase(visitor_type)) {
                                                            society_spinner.setSelection(j);
                                                        }
                                                    }*/

                                                }
                                            } else {
                                                Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_LONG).show();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                });
    }

    }

