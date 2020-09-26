package com.gss.chs.Fragment;


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
public class RegularVisitorFragment extends Fragment {

    FloatingActionButton fab;

    TextView in,out;

    public RegularVisitorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_regular_visitor, container, false);
//        in=view.findViewById(R.id.in);
////        in.setVisibility(View.GONE);
//        out=view.findViewById(R.id.out);
//        out.setVisibility(View.GONE);
        fab=view.findViewById(R.id.fab);
        Fragment  fragment= new InFragment(3);
        FragmentManager fragmentManager = getChildFragmentManager ();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace (R.id.regular_visitor_frag, fragment,"in");
        fragmentTransaction.addToBackStack(null);
//        in.setBackgroundDrawable(getResources().getDrawable(R.color.yellow));
//        out.setBackgroundDrawable(getResources().getDrawable(R.color.tabtext));
//        getActivity().setTitle("Staff");
        NavigationDrawaerActictivity.isdashboard = false;
        fragmentTransaction.commit ();

//        in.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new InFragment();
//                FragmentManager fragmentManager = getFragmentManager ();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace (R.id.regular_visitor_frag, fragment);
//                fragmentTransaction.addToBackStack(null);
//                in.setBackgroundDrawable(getResources().getDrawable(R.color.yellow));
//                out.setBackgroundDrawable(getResources().getDrawable(R.color.tabtext));
//                fragmentTransaction.commit ();
//
//            }
//        });
//        out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new OutFragment();
//                FragmentManager fragmentManager = getFragmentManager ();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace (R.id.regular_visitor_frag, fragment);
//                fragmentTransaction.addToBackStack(null);
//                in.setBackgroundDrawable(getResources().getDrawable(R.color.tabtext));
//                out.setBackgroundDrawable(getResources().getDrawable(R.color.yellow));
//                fragmentTransaction.commit ();
//            }
//        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().setTitle("Regular Visitor Entry");

                Fragment  fragment  = new RegularRegisterFragment();
           FragmentManager     fragmentManager= getFragmentManager();

                FragmentTransaction fragmentTransaction = ((FragmentManager) fragmentManager).beginTransaction();

                fragmentTransaction.replace (R.id.bodyFrag, fragment,"regular_visitor_fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit ();
            }
        });

        return  view;
    }

}
