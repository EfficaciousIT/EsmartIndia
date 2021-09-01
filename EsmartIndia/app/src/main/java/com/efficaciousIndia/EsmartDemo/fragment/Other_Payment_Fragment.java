package com.efficaciousIndia.EsmartDemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.efficaciousIndia.EsmartDemo.R;

/**
 * Created by Rahul on 16,June,2020
 */
public class Other_Payment_Fragment extends Fragment {
    View mview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview=inflater.inflate(R.layout.fragment_payment,null);
        return mview;
    }
}
