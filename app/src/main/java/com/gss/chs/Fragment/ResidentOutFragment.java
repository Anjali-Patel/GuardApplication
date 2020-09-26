package com.gss.chs.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.gss.chs.Activities.ExpectedVisitorProfileActivity;
import com.gss.chs.Activities.NavigationDrawaerActictivity;
import com.gss.chs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResidentOutFragment extends Fragment {
    RelativeLayout r1,r2,r3;

    public ResidentOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_resident_out, container, false);
        r1=view.findViewById(R.id.r1);
        r2=view.findViewById(R.id.r2);
        r3=view.findViewById(R.id.r3);
        NavigationDrawaerActictivity.isdashboard = false;

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), ExpectedVisitorProfileActivity.class);
                startActivity(intent);
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(),ExpectedVisitorProfileActivity.class);
                startActivity(intent);
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(),ExpectedVisitorProfileActivity.class);
                startActivity(intent);
            }
        });
        return  view;
    }

}
