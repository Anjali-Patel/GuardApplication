package com.gss.chs.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.gss.chs.Activities.NavigationDrawaerActictivity;
import com.gss.chs.R;
import com.gss.chs.Utility.CommonUtils;
import com.gss.chs.Utility.SharedPreferenceUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {
WebView webview;
    private SharedPreferenceUtils preferances;
    FrameLayout progressBarHolder;
    public AboutUsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_about_us, container, false);
        webview=view.findViewById(R.id.webview);
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        progressBarHolder.setVisibility(View.VISIBLE);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().getAllowContentAccess();
        webview.setWebViewClient(new WebViewClient());
        preferances= SharedPreferenceUtils.getInstance(getContext());
        String url = (CommonUtils.BASE_URL)+"aboutsociety?dbname="+preferances.getStringValue(CommonUtils.DBNAME,"");
        webview.loadUrl(url);
        progressBarHolder.setVisibility(View.GONE);
        NavigationDrawaerActictivity.isdashboard = false;
        return  view;
    }

}
