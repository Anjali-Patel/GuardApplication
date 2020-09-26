package com.gss.chs.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gss.chs.Activities.NavigationDrawaerActictivity;
import com.gss.chs.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class StaffFragment extends Fragment {
TextView in,out;
//    FloatingActionButton fab;

    public StaffFragment() {
        // Required empty public constructor
    }


    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_staff2, container, false);
       in=view.findViewById(R.id.in);
       out=view.findViewById(R.id.out);
//        fab=view.findViewById(R.id.fab);

        Fragment  fragment= new InStaffFragment();
        FragmentManager fragmentManager = getFragmentManager ();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add (R.id.staff_frag, fragment,"StaffIN");
        in.setBackgroundDrawable(getResources().getDrawable(R.color.yellow));
        out.setBackgroundDrawable(getResources().getDrawable(R.color.tabtext));
//        fab.setVisibility(View.VISIBLE);

//        getActivity().setTitle("Staff");
        fragmentTransaction.commit ();
        NavigationDrawaerActictivity.isdashboard=false;

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new InStaffFragment();
                FragmentManager fragmentManager = getFragmentManager ();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.staff_frag, fragment,"StaffIN");
//                fragmentTransaction.addToBackStack(null);
                in.setBackgroundDrawable(getResources().getDrawable(R.color.yellow));
                out.setBackgroundDrawable(getResources().getDrawable(R.color.tabtext));
                fragmentTransaction.commit ();
                NavigationDrawaerActictivity.isdashboard=false;
//                fab.setVisibility(View.VISIBLE);


            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new OutStaffFragment();
                FragmentManager fragmentManager = getFragmentManager ();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.staff_frag, fragment,"StaffOut");
//                fragmentTransaction.addToBackStack(null);
                in.setBackgroundDrawable(getResources().getDrawable(R.color.tabtext));
                out.setBackgroundDrawable(getResources().getDrawable(R.color.yellow));
                fragmentTransaction.commit ();
                NavigationDrawaerActictivity.isdashboard=false;
//                fab.setVisibility(View.VISIBLE);


            }
        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("RestrictedApi")
//            @Override
//            public void onClick(View view) {
//                getActivity().setTitle("Staff Entry");
//                Fragment  fragment  = new StaffEntryFragment();
//                FragmentManager fragmentManager= getFragmentManager();
//                FragmentTransaction fragmentTransaction = ((FragmentManager) fragmentManager).beginTransaction();
//                fragmentTransaction.replace (R.id.staff_frag, fragment,"new_visitor_entry");
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit ();
//                NavigationDrawaerActictivity.isdashboard=false;
//                fab.setVisibility(View.GONE);
//            }
//        });

        return view;
    }

}
