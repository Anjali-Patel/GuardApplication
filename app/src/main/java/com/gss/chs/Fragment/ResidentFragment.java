package com.gss.chs.Fragment;


import android.os.Bundle;
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
public class ResidentFragment extends Fragment {
    TextView in,out;

    public ResidentFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_resident, container, false);

//        in=view.findViewById(R.id.in);
//        out=view.findViewById(R.id.out);

        Fragment  fragment= new ResidentInFragment();
        FragmentManager fragmentManager = getFragmentManager ();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add (R.id.resident_frag, fragment,"residentinfragment");
//        in.setBackgroundDrawable(getResources().getDrawable(R.color.yellow));
//        out.setBackgroundDrawable(getResources().getDrawable(R.color.tabtext));
//        getActivity().setTitle("Staff");
        fragmentTransaction.commit ();
        NavigationDrawaerActictivity.isdashboard=false;

//
//
//        in.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new ResidentInFragment();
//                FragmentManager fragmentManager = getFragmentManager ();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace (R.id.resident_frag, fragment,"residentinfragment");
////                fragmentTransaction.addToBackStack(null);
//                in.setBackgroundDrawable(getResources().getDrawable(R.color.yellow));
//                out.setBackgroundDrawable(getResources().getDrawable(R.color.tabtext));
//                fragmentTransaction.commit ();
//                NavigationDrawaerActictivity.isdashboard=false;
//
//            }
//        });
//        out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new ResidentOutFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace (R.id.resident_frag, fragment,"residentoutfragment");
////                fragmentTransaction.addToBackStack(null);
//                in.setBackgroundDrawable(getResources().getDrawable(R.color.tabtext));
//                out.setBackgroundDrawable(getResources().getDrawable(R.color.yellow));
//                fragmentTransaction.commit ();
//                NavigationDrawaerActictivity.isdashboard=false;
//            }
//        });

        return view;
    }


}
