package com.gss.chs.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gss.chs.Activities.NavigationDrawaerActictivity;
import com.gss.chs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragmentNew extends Fragment {
LinearLayout new_visitor,regular_visitor,expected_visitor;
FragmentManager fragmentManager;
    String flag;


    public DashBoardFragmentNew() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dash_board_fragment_new, container, false);
        new_visitor=view.findViewById(R.id.new_visitor);
        regular_visitor=view.findViewById(R.id.regular_visitor);
        expected_visitor=view.findViewById(R.id.expected_visitor);
        expected_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getActivity().setTitle("Expected Visitor");
//                    flag="expected_visitor";
                    Fragment fragment = new ExpectedVisitorFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace (R.id.bodyFrag, fragment,"expected_visitor");
//                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit ();
                    NavigationDrawaerActictivity.isdashboard=false;

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        new_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                    flag="new_visitor";
                    getActivity().setTitle("New Visitor");
                    Fragment fragment = new NewVisitorFragment();
                    FragmentManager fragmentManager = getFragmentManager ();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace (R.id.bodyFrag, fragment,"new_visitor");
//                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit ();
                    NavigationDrawaerActictivity.isdashboard=false;


                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        regular_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                    flag="regular_visitor";
                    getActivity().setTitle("Regular Visitor");
                    Fragment fragment = new RegularVisitorFragment();
                    FragmentManager fragmentManager = getFragmentManager ();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace (R.id.bodyFrag, fragment,"regular_visitor");
//                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit ();
                    NavigationDrawaerActictivity.isdashboard=false;


                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

}
