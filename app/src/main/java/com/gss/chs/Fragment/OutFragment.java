package com.gss.chs.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import com.gss.chs.Adapters.VisitorAdapter;
import com.gss.chs.Adapters.VisitorOutAdapter;
import com.gss.chs.Model.StaffModel;
import com.gss.chs.Model.VisitorModel;
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
import java.util.List;

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
@SuppressLint("ValidFragment")
public class OutFragment extends Fragment {
RecyclerView visitor_our_list;
    EditText input_search;
    RecyclerView.LayoutManager layoutmanager;
    VisitorOutAdapter adapter;
    ArrayList<VisitorModel> visitorOutList;
    ArrayList<VisitorModel> visitorOutListTemp;
    String dbname,sci_id,guard_id;
FrameLayout progressBarHolder;
RelativeLayout r1,r2,r3;
    int visitor_type=0;
    public OutFragment(int visitor_type) {
        this.visitor_type=visitor_type;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_out3, container, false);
        visitor_our_list=view.findViewById(R.id.visitor_our_list);
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        input_search=view.findViewById(R.id.input_search);
        visitorOutList = new ArrayList<>();
        visitorOutListTemp= new ArrayList<>();
        guard_id = SharedPreferenceUtils.getInstance(getContext()).getStringValue(CommonUtils.GUARD_ID, "");
        dbname = SharedPreferenceUtils.getInstance(getContext()).getStringValue(CommonUtils.DBNAME, "");
        sci_id = SharedPreferenceUtils.getInstance(getContext()).getStringValue(CommonUtils.SOCIETY, "");

        input_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String strText = s.toString().trim();
                if (strText.length() != 0) {
                    visitorOutListTemp.clear();
                    for (int i = 0; i < visitorOutList.size(); i++) {
                        if (visitorOutList.get(i).getName().toLowerCase().startsWith(strText.toLowerCase())) {
                            visitorOutListTemp.add(visitorOutList.get(i));
                        }
                    }
                    adapter = new  VisitorOutAdapter(getActivity(), visitorOutListTemp);
                    visitor_our_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter = new  VisitorOutAdapter(getActivity(), visitorOutList);
                    visitor_our_list.setAdapter(adapter);
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
                    visitorOutListTemp.clear();
                    for (int i = 0; i < visitorOutList.size(); i++) {
                        if (visitorOutList.get(i).getName().toLowerCase().startsWith(strText.toLowerCase())) {
                            visitorOutListTemp.add(visitorOutList.get(i));
                        }
                    }
                    adapter = new VisitorOutAdapter(getActivity(), visitorOutListTemp);
                    visitor_our_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter = new VisitorOutAdapter(getActivity(), visitorOutList);
                    visitor_our_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        });
          if (CheckNetwork.isInternetAvailable(getContext())) {
              getOutVisitorList();
        }else{
            Toast.makeText(getContext(), "No Internet Connection.Please Check Your Intent Connection", Toast.LENGTH_LONG).show();
        }

        NavigationDrawaerActictivity.isdashboard = false;

        return view;
    }
    private void getOutVisitorList(){
        progressBarHolder.setVisibility(View.VISIBLE);
        visitorOutList.clear();
        String url =(CommonUtils.BASE_URL)+"visitorlog";
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("sctid", sci_id)
                .add("dbname", dbname)
                .add("guardid", guard_id)
                .add("type", "1")
                .add("vstrtype", String.valueOf(visitor_type))
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
                                                JSONArray update = json.getJSONArray("visitors");
                                                for (int i = 0; i < update.length(); i++) {
                                                    JSONObject Memberdetail = update.getJSONObject(i);
//                                                    JSONObject Memberdetail = UpdateDetails.getJSONObject("User");
                                                    VisitorModel model = new VisitorModel();
                                                    model.setVisitor_id(Memberdetail.getString("id").replace("null", ""));
                                                    model.setWing_name(Memberdetail.getString("wing_name").replace("null", ""));
                                                    model.setName(Memberdetail.getString("vstr_name").replace("null", ""));
                                                    model.setIn_time(Memberdetail.getString("vstr_intime").replace("null", ""));
                                                    model.setOutTime(Memberdetail.getString("vstr_outtime").replace("null", ""));
                                                    model.setPurpose(Memberdetail.getString("vstr_purpose").replace("null", ""));
                                                    model.setMobile_number(Memberdetail.getString("vstr_mobile").replace("null", ""));
                                                    model.setUser_flatnumber(Memberdetail.getString("flat_no").replace("null", ""));
//                                                    model.setDate(Memberdetail.getString("vstr_mobile").replace("null", ""));
                                                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Memberdetail.getString("vstr_intime").replace("null", ""));
                                                    String newString = new SimpleDateFormat("MMM dd yyyy").format(date); // 25-03-2019
                                                    model.setDate(newString);
                                                    visitorOutList.add(model);
//                                                    adapter.notifyDataSetChanged();
                                                }
                                                progressBarHolder.setVisibility(View.GONE);

                                                adapter = new VisitorOutAdapter(getActivity(), visitorOutList);
                                                layoutmanager = new LinearLayoutManager(getActivity());
                                                visitor_our_list.setLayoutManager(layoutmanager);
                                                visitor_our_list.setAdapter(adapter);
                                                adapter.notifyDataSetChanged();

                                            } else {
                                                progressBarHolder.setVisibility(View.GONE);
//                                                Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_LONG).show();

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                });
    }
//    private void getStaffList(){
//        progressBarHolder.setVisibility(View.VISIBLE);
//        staffmemberList.clear();
//        String url =(CommonUtils.BASE_URL)+"stafflist";
//        OkHttpClient client = new OkHttpClient();
//        RequestBody formBody = new FormBody.Builder()
//                .add("sctid", UserId)
//                .add("dbname", UserId)
//
//                .build();
//        Request request = new Request.Builder()
//                .url(url)
//                .post(formBody)
//                .build();
//        client.newCall(request).enqueue(
//                new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        call.cancel();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        ResponseBody responseBody = response.body();
//                        final String myResponse = responseBody.string();
//
//                        getActivity().runOnUiThread(
//                                new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        JSONObject json = null;
//                                        try {
//                                            json = new JSONObject(myResponse);
//                                            String success = json.getString("status");
//                                            if (success.equalsIgnoreCase("success")) {
//
//                                                ProfiulePicBaseURL = json.getString("profile_pic_url");
//
//                                                JSONArray update = json.getJSONArray("members");
//                                                for (int i = 0; i < update.length(); i++) {
//                                                    JSONObject Memberdetail = update.getJSONObject(i);
////                                                    JSONObject Memberdetail = UpdateDetails.getJSONObject("User");
//                                                    MemberDataModel model = new MemberDataModel();
//                                                    model.setMemberGender(Memberdetail.getString("gender").replace("null", ""));
//                                                    model.setMemberStatus(Memberdetail.getString("professional_status").replace("null", ""));
//                                                    model.setMemberName(Memberdetail.getString("name").replace("null", ""));
//                                                    model.setMemberLocation(Memberdetail.getString("Country").replace("null", ""));
//                                                    model.setMemberId(Memberdetail.getString("user_id").replace("null", ""));
//                                                    model.setMemberImage(Memberdetail.getString("profile_picture").replace("null", ""));
//                                                    model.setRequestStatus(Memberdetail.getString("request_status").replace("null", ""));
//
//                                                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Memberdetail.getString("join_on"));
//                                                    String newString = new SimpleDateFormat("MMM dd yyyy").format(date); // 25-03-2019
//                                                    model.setMemberJoined(newString);
//
//
//                                                    MembersDataModelArrayList.add(model);
////                                                    adapter.notifyDataSetChanged();
//                                                }
//                                                progressBarHolder.setVisibility(View.GONE);
//
//                                                adapter = new AllConnectionAdapter(getActivity(), MembersDataModelArrayList, ProfiulePicBaseURL, freindList);
//                                                layoutmanager = new LinearLayoutManager(getActivity());
//                                                friendlist.setLayoutManager(layoutmanager);
//                                                friendlist.setAdapter(adapter);
//
//                                            } else {
//
//                                            }
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        } catch (ParseException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                });
//                    }
//                });
//    }

}
