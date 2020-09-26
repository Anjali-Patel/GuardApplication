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
import com.gss.chs.Adapters.StaffAdapter;
import com.gss.chs.Adapters.StaffOutAdapter;
import com.gss.chs.Adapters.VisitorOutAdapter;
import com.gss.chs.Model.StaffModel;
import com.gss.chs.R;
import com.gss.chs.Utility.CheckNetwork;
import com.gss.chs.Utility.CommonUtils;
import com.gss.chs.Utility.SharedPreferenceUtils;

import org.json.JSONArray;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class OutStaffFragment extends Fragment {
    RecyclerView staff_list;
    EditText input_search;
    RecyclerView.LayoutManager layoutmanager;
    StaffOutAdapter adapter;
    ArrayList<StaffModel> staffmemberList;
    ArrayList<StaffModel> staffmemberListTemp;
    FrameLayout progressBarHolder;
    String dbname,sci_id;
    RelativeLayout r1,r2,r3;
    public OutStaffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_out_staff2, container, false);
        input_search=view.findViewById(R.id.input_search);
        staffmemberListTemp= new ArrayList<>();
        staffmemberList = new ArrayList<>();
        staff_list=view.findViewById(R.id.staff_list);
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        dbname = SharedPreferenceUtils.getInstance(getContext()).getStringValue(CommonUtils.DBNAME, "");
        sci_id = SharedPreferenceUtils.getInstance(getContext()).getStringValue(CommonUtils.SOCIETY, "");
        input_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String strText = s.toString().trim();
                if (strText.length() != 0) {
                    staffmemberListTemp.clear();
                    for (int i = 0; i < staffmemberList.size(); i++) {
                        if (staffmemberList.get(i).getName().toLowerCase().startsWith(strText.toLowerCase())) {
                            staffmemberListTemp.add(staffmemberList.get(i));
                        }
                    }
                    adapter = new StaffOutAdapter(getActivity(), staffmemberListTemp);
                    staff_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter = new  StaffOutAdapter(getActivity(), staffmemberList);
                    staff_list.setAdapter(adapter);
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
                    staffmemberListTemp.clear();
                    for (int i = 0; i < staffmemberList.size(); i++) {
                        if (staffmemberList.get(i).getName().toLowerCase().startsWith(strText.toLowerCase())) {
                            staffmemberListTemp.add(staffmemberList.get(i));
                        }
                    }
                    adapter = new StaffOutAdapter(getActivity(), staffmemberListTemp);
                    staff_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter = new StaffOutAdapter(getActivity(), staffmemberList);
                    staff_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        });
          if (CheckNetwork.isInternetAvailable(getContext())) {
              getOutStaffList();
        }else{
            Toast.makeText(getContext(), "No Internet Connection.Please Check Your Intent Connection", Toast.LENGTH_LONG).show();
        }

        return view;
    }
    private void getOutStaffList(){
        progressBarHolder.setVisibility(View.VISIBLE);
        staffmemberList.clear();
        String url =(CommonUtils.BASE_URL)+"stafflist";
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("sctid", sci_id)
                .add("dbname", dbname)
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
                                                JSONArray update = json.getJSONArray("staffdetail");
                                                for (int i = 0; i < update.length(); i++) {
                                                    JSONObject Memberdetail = update.getJSONObject(i);
//                                                    JSONObject Memberdetail = UpdateDetails.getJSONObject("User");
                                                    StaffModel model = new StaffModel();
                                                    model.setName(Memberdetail.getString("VendorName").replace("null", ""));
                                                    model.setOccupation(Memberdetail.getString("title").replace("null", ""));
                                                    model.setServiceName(Memberdetail.getString("ServiceName").replace("null", ""));
                                                    model.setMobileNumber(Memberdetail.getString("PhoneNo").replace("null", ""));
                                                    model.setAddress(Memberdetail.getString("Add").replace("null", ""));
                                                    model.setEmail(Memberdetail.getString("Email").replace("null", ""));
                                                    model.setIn_time(Memberdetail.getString("VendorName").replace("null", ""));


//                                                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Memberdetail.getString("join_on"));
//                                                    String newString = new SimpleDateFormat("MMM dd yyyy").format(date); // 25-03-2019
//                                                    model.setDate(newString);
                                                    staffmemberList.add(model);
//                                                    adapter.notifyDataSetChanged();
                                                }
                                                progressBarHolder.setVisibility(View.GONE);

                                                adapter = new StaffOutAdapter(getActivity(), staffmemberList);
                                                layoutmanager = new LinearLayoutManager(getActivity());
                                                staff_list.setLayoutManager(layoutmanager);
                                                staff_list.setAdapter(adapter);
                                                adapter.notifyDataSetChanged();

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


