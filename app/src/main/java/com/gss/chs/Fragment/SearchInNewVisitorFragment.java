package com.gss.chs.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gss.chs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchInNewVisitorFragment extends Fragment {


    public SearchInNewVisitorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_in_new_visitor, container, false);
    }

}
