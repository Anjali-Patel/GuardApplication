package com.gss.chs.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gss.chs.Activities.ExpectedVisitorProfileActivity;
import com.gss.chs.Activities.LoginActivity;
import com.gss.chs.Activities.NavigationDrawaerActictivity;
import com.gss.chs.Adapters.ResidentAdapter;
import com.gss.chs.Adapters.StaffAdapter;
import com.gss.chs.Adapters.StaffOutAdapter;
import com.gss.chs.Model.ResidentModel;
import com.gss.chs.Model.StaffModel;
import com.gss.chs.R;
import com.gss.chs.Utility.CheckNetwork;
import com.gss.chs.Utility.CommonUtils;
import com.gss.chs.Utility.SharedPreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResidentInFragment extends Fragment {
EditText input_search;
    RelativeLayout r1,r2,r3;
    RecyclerView resident_list;
    RecyclerView.LayoutManager layoutmanager;
    ResidentAdapter adapter;
    ArrayList<ResidentModel> residentModelList;
    ArrayList<ResidentModel> residentModelListTemp;
    FrameLayout progressBarHolder;
    String dbname,sci_id;
    public ResidentInFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_resident_in, container, false);
        resident_list=view.findViewById(R.id.resident_list);
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        input_search=view.findViewById(R.id.input_search);
        dbname = SharedPreferenceUtils.getInstance(getContext()).getStringValue(CommonUtils.DBNAME, "");
        sci_id = SharedPreferenceUtils.getInstance(getContext()).getStringValue(CommonUtils.SOCIETY, "");
        residentModelList = new ArrayList<>();
        residentModelListTemp=new ArrayList<>();
        input_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String strText = s.toString().trim();
                if (strText.length() != 0) {
                    residentModelListTemp.clear();
                    for (int i = 0; i < residentModelList.size(); i++) {
                        if (residentModelList.get(i).getName().toLowerCase().startsWith(strText.toLowerCase())) {
                            residentModelListTemp.add(residentModelList.get(i));
                        }
                    }
                    adapter = new ResidentAdapter(getActivity(), residentModelListTemp);
                    resident_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter = new  ResidentAdapter(getActivity(), residentModelList);
                    resident_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strText = s.toString().trim();
                if (strText.length() != 0) {
                    residentModelListTemp.clear();
                    for (int i = 0; i < residentModelList.size(); i++) {
                        if (residentModelList.get(i).getName().toLowerCase().startsWith(strText.toLowerCase())) {
                            residentModelListTemp.add(residentModelList.get(i));
                        }
                    }
                    adapter = new ResidentAdapter(getActivity(), residentModelListTemp);
                    resident_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter = new ResidentAdapter(getActivity(), residentModelList);
                    resident_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        });


  if (CheckNetwork.isInternetAvailable(getContext())) {
            getResidentList();
        }else{
            Toast.makeText(getContext(), "No Internet Connection.Please Check Your Intent Connection", Toast.LENGTH_LONG).show();
        }








//        r1=view.findViewById(R.id.r1);
//        r2=view.findViewById(R.id.r2);
//        r3=view.findViewById(R.id.r3);
        NavigationDrawaerActictivity.isdashboard = false;

//        r1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(getContext(), ExpectedVisitorProfileActivity.class);
//                startActivity(intent);
//            }
//        });
//        r2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(getContext(),ExpectedVisitorProfileActivity.class);
//                startActivity(intent);
//            }
//        });
//        r3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(getContext(),ExpectedVisitorProfileActivity.class);
//                startActivity(intent);
//            }
//        });

        return view;
    }
    private void getResidentList(){
        progressBarHolder.setVisibility(View.VISIBLE);
        residentModelList.clear();
        String url =(CommonUtils.BASE_URL)+"memberdetails";
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("dbname", dbname)
                .add("request_type", "1")
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
                        getActivity().runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        JSONObject json = null;
                                        try {
                                            json = new JSONObject(myResponse);
                                            String success = json.getString("response");
                                            if (success.equalsIgnoreCase("success")) {
                                                if(json.has("data")) {
                                                    JSONObject jsonData = json.getJSONObject("data");
                                                    JSONArray update = jsonData.getJSONArray("Memberdetails");
                                                    for (int i = 0; i < update.length(); i++) {
                                                        JSONObject Memberdetail = update.getJSONObject(i);
//                                                    JSONObject Memberdetail = UpdateDetails.getJSONObject("User");
                                                        ResidentModel model = new ResidentModel();
                                                        model.setMailingAddress(Memberdetail.getString("maddress").replace("null", ""));
                                                        model.setAllocatedWork(Memberdetail.getString("AllocatedWork").replace("null", ""));
                                                        model.setResident_photo(Memberdetail.getString("mphoto").replace("null", ""));
                                                        model.setName(Memberdetail.getString("member_name").replace("null", ""));
                                                        model.setFlat_number(Memberdetail.getString("flatno").replace("null", ""));
//                                                    model.setInTime(Memberdetail.getString("PhoneNo").replace("null", ""));
//                                                    model.setOutTime(Memberdetail.getString("member_name").replace("null", ""));
                                                        model.setMobile_number(Memberdetail.getString("phoneno").replace("null", ""));
                                                        model.setPurpose(Memberdetail.getString("email_id").replace("null", ""));
                                                        model.setWing_name(Memberdetail.getString("wing_name").replace("null", ""));
//                                                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Memberdetail.getString("JoiningDate"));
//                                                    String newString = new SimpleDateFormat("MMM dd yyyy").format(date); // 25-03-2019
//                                                        model.setDate(newString);
                                                        model.setDate(Memberdetail.getString("JoiningDate").replace("null", ""));

                                                        residentModelList.add(model);
//                                                    adapter.notifyDataSetChanged();
                                                    }
                                                    progressBarHolder.setVisibility(View.GONE);
                                                    adapter = new ResidentAdapter(getActivity(), residentModelList);
                                                    layoutmanager = new LinearLayoutManager(getActivity());
                                                    resident_list.setLayoutManager(layoutmanager);
                                                    resident_list.setAdapter(adapter);
                                                    adapter.notifyDataSetChanged();
                                                }else{
                                                    progressBarHolder.setVisibility(View.GONE);
                                                    Toast.makeText(getActivity(),"No data found",Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                progressBarHolder.setVisibility(View.GONE);
//                                                Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_LONG).show();

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
