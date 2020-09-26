package com.gss.chs.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gss.chs.Activities.NavigationDrawaerActictivity;
import com.gss.chs.R;

//import static com.gss.chs.Activities.NavigationDrawaerActictivity.isdashboard;

public class DashBoardFragment extends Fragment {
    private static final String TAG = "DashBoardFragment";
    TextView in, out;
    DashBoardFragmentNew dashBoardFragmentNew = null;

    public DashBoardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        in = view.findViewById(R.id.in);
        out = view.findViewById(R.id.out);
        in.setBackgroundDrawable(getResources().getDrawable(R.color.yellow));
        out.setBackgroundDrawable(getResources().getDrawable(R.color.tabtext));
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new InFragment(1);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.bodyFrag, fragment, "VisitorIn");
//                fragmentTransaction.addToBackStack(null);
                in.setBackgroundDrawable(getResources().getDrawable(R.color.yellow));
                out.setBackgroundDrawable(getResources().getDrawable(R.color.tabtext));
                fragmentTransaction.commit();
                NavigationDrawaerActictivity.isdashboard = false;

            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new OutFragment(1);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.bodyFrag, fragment, "VisitorOut");
//                fragmentTransaction.addToBackStack(null);
                out.setBackgroundDrawable(getResources().getDrawable(R.color.yellow));
                in.setBackgroundDrawable(getResources().getDrawable(R.color.tabtext));
                fragmentTransaction.commit();
                NavigationDrawaerActictivity.isdashboard = false;


            }
        });
        init();
        Log.i(TAG, "onCreateView: ");
        return view;
    }


    public void init() {

        dashBoardFragmentNew = new DashBoardFragmentNew();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.bodyFrag, dashBoardFragmentNew, "dashboardnew");
        fragmentTransaction.commit();
        NavigationDrawaerActictivity.isdashboard = true;

        Log.d(TAG, "init() returned: ");

    }





    @Override
    public void onResume() {
        super.onResume();
        init();
        Log.d(TAG, "onResume() returned: " );

    }
}
