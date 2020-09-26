package com.gss.chs.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gss.chs.Activities.NavigationDrawaerActictivity;
import com.gss.chs.R;
import com.gss.chs.Utility.CommonUtils;
import com.gss.chs.Utility.SharedPreferenceUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {
String mobile_number,email;
TextView society_phone,society_email;
ImageView society_logo;
public ContactUsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_contact_us, container, false);
        society_phone=view.findViewById(R.id.society_phone);
        society_logo=view.findViewById(R.id.society_logo);
        society_email=view.findViewById(R.id.society_email);
        mobile_number= SharedPreferenceUtils.getInstance(getContext()).getStringValue(CommonUtils.SOCIETY_MOBILE, "");
        email=SharedPreferenceUtils.getInstance(getContext()).getStringValue(CommonUtils.GUARD_EMAIL, "");
        society_phone.setText(mobile_number);
        society_email.setText(email);

        if (!SharedPreferenceUtils.getInstance(getContext()).getStringValue(CommonUtils.GUARD_IMAGE, "").equalsIgnoreCase("")) {
            Glide.with(getContext()).load("http://demo1.geniesoftsystem.com/newweb/coperativehousingsystem/assets/upload/documents/guard/guardphoto/"+SharedPreferenceUtils.getInstance(getContext()).getStringValue(CommonUtils.GUARD_IMAGE, "")).into(society_logo);
        }

        NavigationDrawaerActictivity.isdashboard = false;
        society_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{society_email.getText().toString()});
                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent, "Select Email Sending App :"));

            }
        });
        society_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + mobile_number));
                startActivity(callIntent);
            }
        });


        return view;
    }

}
