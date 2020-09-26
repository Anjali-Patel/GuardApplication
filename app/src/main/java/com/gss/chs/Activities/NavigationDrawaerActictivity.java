package com.gss.chs.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gss.chs.Fragment.AboutUsFragment;
import com.gss.chs.Fragment.ContactUsFragment;
import com.gss.chs.Fragment.DashBoardFragment;
import com.gss.chs.Fragment.DashBoardFragmentNew;
import com.gss.chs.Fragment.InFragment;
import com.gss.chs.Fragment.MainFragment;
import com.gss.chs.Fragment.ResidentFragment;
import com.gss.chs.Fragment.StaffFragment;
import com.gss.chs.R;
import com.gss.chs.Utility.CommonUtils;
import com.gss.chs.Utility.SharedPreferenceUtils;
import com.theartofdev.edmodo.cropper.CropImage;

import static com.gss.chs.Fragment.RegularRegisterFragment.img_camera;
import static com.gss.chs.Fragment.RegularRegisterFragment.imageurl;
public class NavigationDrawaerActictivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Toolbar toolbar;
    public static boolean isdashboard = false, isAboutUs=false,contactUs=false;
    ViewPager viewPager;
    TabLayout tabLayout;
    Fragment test, test1, test2, test3, test4;
    MainFragment mainFragment;
    public static ImageView user_profile;
    ContactUsFragment contactUsFragment;
  AboutUsFragment aboutUsFragment;

    public static int tabID = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_navigation_drawaer_actictivity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainFragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainFragment, mainFragment, "Main");
        fragmentTransaction.commit();





        /*viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        View view= LayoutInflater.from(this).inflate(R.layout.custom_tab_layout, null);
        final LinearLayout linear;
        final TextView name;
        final ImageView image_view;
        name=view.findViewById(R.id.tab);
        image_view=view.findViewById(R.id.iv);
        name.setText("Visitor");
        image_view.setImageResource( R.drawable.blue_vector_visitor);
        tabLayout.getTabAt(0).setCustomView(view);

        View view1= LayoutInflater.from(this).inflate(R.layout.custom_tab_layout, null);
        final LinearLayout linear1;
        final TextView name1;
        final ImageView image_view1;
        name1=view1.findViewById(R.id.tab);
        image_view1=view1.findViewById(R.id.iv);
        name1.setText("Staff");
        image_view1.setImageResource( R.drawable.ic_white_bg_staff);
        tabLayout.getTabAt(1).setCustomView(view1);

        View view2= LayoutInflater.from(this).inflate(R.layout.custom_tab_layout, null);
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
                    toolbar.setTitle("Visitor");
                }
                if(tab.getPosition() == 1){
                    image_view1.setImageResource(R.drawable.blue_vector_staff);
                    toolbar.setTitle("Staff");
                }
                if(tab.getPosition() == 2){
                    image_view2.setImageResource(R.drawable.blue_vector_resident);
                    toolbar.setTitle("Resident");
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
        tabLayout.getTabAt(2).setText("Resident");*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.openDrawer(Gravity.LEFT);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView userName = (TextView)header.findViewById(R.id.UserName);
        TextView userEmail = (TextView)header.findViewById(R.id.UserEmail);
        user_profile= (ImageView) header.findViewById(R.id.UserImage);
        // LinearLayout SignOutLinear = (LinearLayout) navigationView.findViewById(R.id.UserImage);

  String guard_name=SharedPreferenceUtils.getInstance(NavigationDrawaerActictivity.this).getStringValue(CommonUtils.GUARD_NAME, "");
  String guard_email=SharedPreferenceUtils.getInstance(NavigationDrawaerActictivity.this).getStringValue(CommonUtils.GUARD_EMAIL, "");
        userName.setText(guard_name);
        userEmail.setText(guard_email);
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .error(R.drawable.noimage)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .priority(Priority.HIGH);


        if (!SharedPreferenceUtils.getInstance(NavigationDrawaerActictivity.this).getStringValue(CommonUtils.GUARD_IMAGE, "").equalsIgnoreCase("")) {
            Glide.with(NavigationDrawaerActictivity.this).load("http://demo1.geniesoftsystem.com/newweb/coperativehousingsystem/assets/upload/documents/guard/guardphoto/"+SharedPreferenceUtils.getInstance(NavigationDrawaerActictivity.this).getStringValue(CommonUtils.GUARD_IMAGE, "")).into(user_profile);
        }
        setTitle("Visitor");
    }
    Menu menu;
    /*private void setupViewPager(ViewPager viewPager) {
        try {

            FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
            adapter.addFragment(new DashBoardFragment(), "Visitor");
            adapter.addFragment(new StaffFragment(), "Staff");
            adapter.addFragment(new ResidentFragment(), "Resident");
            viewPager.setAdapter(adapter);
        } catch (SecurityException e) {

        }
    }*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (isdashboard) {
                gotoback();
            } else {
                if(contactUs){
                    toolbar.setTitle("Visitor");
                    mainFragment = new MainFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mainFragment, mainFragment, "Main");
                    fragmentTransaction.commit();
                    contactUs = false;
                }else{
                    MainFragment.tabLayout.getTabAt(0).select();
                    gotoDashboard();
                }
            }
        } }
    private void gotoback() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setMessage("Want to Exit ?");
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });


        builder.show();
    }

    private void gotoDashboard() {

        DashBoardFragmentNew dashBoardFragmentNew = new DashBoardFragmentNew();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.bodyFrag, dashBoardFragmentNew, "dashboardnew");
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.visitor) {
            if (contactUs) {
                toolbar.setTitle("Visitor");
                mainFragment = new MainFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.remove(mainFragment);
                fragmentTransaction.add(R.id.mainFragment, mainFragment, "Main");
                fragmentTransaction.commit();
                contactUs = false;
                tabID = 0;
            } else {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.bodyFrag, new DashBoardFragmentNew());
                toolbar.setTitle("Visitor");
                transaction.commit();
                MainFragment.tabLayout.getTabAt(0).select();
                isdashboard = true;
                tabID = -1;
            }

        } else if (id == R.id.staff) {
            if (contactUs) {
                toolbar.setTitle("Staff");
                mainFragment = new MainFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFragment, mainFragment, "Main");
                fragmentTransaction.commit();
                contactUs = false;
                tabID=1;
            } else {
                mainFragment = new MainFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.staff_frag, new StaffFragment());
                toolbar.setTitle("Staff");
                transaction.commit();
                MainFragment.tabLayout.getTabAt(1).select();
                isdashboard = false;
                tabID = -1;
            }

        } else if (id == R.id.resident) {
            if (contactUs) {
                toolbar.setTitle("Resident");
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFragment, mainFragment, "Main");
                fragmentTransaction.commit();
                contactUs = false;
                tabID = 2;
            } else {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.resident_frag, new ResidentFragment());
                toolbar.setTitle("Resident");
                transaction.commit();
                MainFragment.tabLayout.getTabAt(2).select();
                isdashboard = false;
                tabID = -1;
            }

        } else if (id == R.id.contact_us) {
            contactUsFragment = new ContactUsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFragment, contactUsFragment);
//            transaction.addToBackStack(null);
            toolbar.setTitle("Contact us");
            transaction.commit();
            contactUs = true;
        } else if (id == R.id.about_us) {

            aboutUsFragment = new AboutUsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFragment, aboutUsFragment);
//            transaction.addToBackStack(null);
            toolbar.setTitle("About Society");
            transaction.commit();
            contactUs = true;
        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.create();
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  SharedPreferenceUtils.getInstance(NavigationDrawaerActictivity.this).setValue(CommonUtils.GUARD_ID, "");
                  Intent i = new Intent(NavigationDrawaerActictivity.this, LoginActivity.class);
                    startActivity(i);
                }
            });
            builder.show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    public void onActivityResult(int requestcode, int resultcode, Intent data) {
//        if (data != null) {
//            if (requestcode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//                CropImage.ActivityResult result = CropImage.getActivityResult(data);
//                Uri resultUri = result.getUri();
//                img_camera.setImageURI(resultUri);
//                imageurl = resultUri.getPath();
//            }
//        }
//    }
}
