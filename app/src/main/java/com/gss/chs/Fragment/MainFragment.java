package com.gss.chs.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gss.chs.Activities.NavigationDrawaerActictivity;
import com.gss.chs.Adapters.FragmentAdapter;
import com.gss.chs.R;

import static com.gss.chs.Activities.NavigationDrawaerActictivity.tabID;

public class MainFragment extends Fragment {

    ViewPager viewPager;
    public static TabLayout tabLayout;
    public MainFragment() {
        // Required empty public constructor
    }

     DashBoardFragment dashBoardFragment;
    StaffFragment staffFragment;
    ResidentFragment residentFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_main, container, false);

        viewPager = v.findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        tabLayout = v.findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        View view= LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_layout, null);
        final LinearLayout linear;
        final TextView name;
        final ImageView image_view;
        name=view.findViewById(R.id.tab);
        image_view=view.findViewById(R.id.iv);
        name.setText("Visitor");
        image_view.setImageResource( R.drawable.blue_vector_visitor);
        tabLayout.getTabAt(0).setCustomView(view);

        View view1= LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_layout, null);
        final LinearLayout linear1;
        final TextView name1;
        final ImageView image_view1;
        name1=view1.findViewById(R.id.tab);
        image_view1=view1.findViewById(R.id.iv);
        name1.setText("Staff");
        image_view1.setImageResource( R.drawable.ic_white_bg_staff);
        tabLayout.getTabAt(1).setCustomView(view1);

        View view2= LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_layout, null);
        final LinearLayout linear2;
        final TextView name2;
        final ImageView image_view2;
        name2=view2.findViewById(R.id.tab);
        image_view2=view2.findViewById(R.id.iv);
        name2.setText("Resident");
        image_view2.setImageResource( R.drawable.ic_white_bg_resident);
        tabLayout.getTabAt(2).setCustomView(view2);

        tabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    image_view.setImageResource(R.drawable.blue_vector_visitor);
                    NavigationDrawaerActictivity.toolbar.setTitle("Visitor");
                }
                if(tab.getPosition() == 1){
                    image_view1.setImageResource(R.drawable.blue_vector_staff);
                    NavigationDrawaerActictivity.toolbar.setTitle("Staff");
                }
                if(tab.getPosition() == 2){
                    image_view2.setImageResource(R.drawable.blue_vector_resident);
                    NavigationDrawaerActictivity.toolbar.setTitle("Resident");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    image_view.setImageResource(R.drawable.ic_white_bg_visitor);
                }
                if(tab.getPosition() == 1){
                    image_view1.setImageResource(R.drawable.ic_white_bg_staff);
                }
                if(tab.getPosition() == 2){
                    image_view2.setImageResource(R.drawable.ic_white_bg_resident);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });

        tabLayout.getTabAt(0).setText("Visitor");
        tabLayout.getTabAt(1).setText("Staff");
        tabLayout.getTabAt(2).setText("Resident");

        if(tabID == 0){
            tabLayout.getTabAt(0).select();
        }
        if(tabID == 1){
            tabLayout.getTabAt(1).select();
        }
        if(tabID == 2){
            tabLayout.getTabAt(2).select();
        }

        return v;

    }

    private static final String TAG = "MainFragment";

    private void setupViewPager(ViewPager viewPager) {
        try {
            viewPager.invalidate();
            dashBoardFragment = new DashBoardFragment();
            staffFragment = new StaffFragment();
            residentFragment = new ResidentFragment();
            FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager());
            adapter.addFragment(dashBoardFragment, "Visitor");
            adapter.addFragment(staffFragment, "Staff");
            adapter.addFragment(residentFragment, "Resident");
            viewPager.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Log.i(TAG, "setupViewPager: ");
        } catch (SecurityException e) {

        }
    }

}
