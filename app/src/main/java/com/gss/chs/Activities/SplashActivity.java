package com.gss.chs.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gss.chs.R;
import com.gss.chs.Utility.CommonUtils;
import com.gss.chs.Utility.SharedPreferenceUtils;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    String guard_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_splash);
        }catch(Exception e){
            e.printStackTrace();
        }
        guard_id= SharedPreferenceUtils.getInstance(SplashActivity.this).getStringValue(CommonUtils.GUARD_ID, "");
        final ImageView im1 = (ImageView)findViewById(R.id.im1);
        final Animation zoomanimation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.im1);
        im1.startAnimation(zoomanimation);
        zoomanimation.setAnimationListener(new Animation.AnimationListener()
            {
                public void onAnimationStart(Animation animation){
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            if(!guard_id.equalsIgnoreCase("")) {
                                Intent intent = new Intent(SplashActivity.this, NavigationDrawaerActictivity.class);
                                startActivity(intent);
                                SplashActivity.this.startActivity(intent);
                                SplashActivity.this.finish();
//                                handler.removeCallbacks(this);
                            } else{
                                Intent intent =new Intent(SplashActivity.this,LoginActivity.class);
                                startActivity(intent);
                                SplashActivity.this.startActivity(intent);
                                SplashActivity.this.finish();
//                                handler.removeCallbacks(this);
                            }

//                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                                finish();



                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(timerTask,3000);

                }

                public void onAnimationEnd(Animation animation){


                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }


            });
        }


    }

