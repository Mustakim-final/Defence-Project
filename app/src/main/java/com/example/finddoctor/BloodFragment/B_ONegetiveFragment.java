package com.example.finddoctor.BloodFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finddoctor.BloodSubFragment.B_DonerFragment;
import com.example.finddoctor.BloodSubFragment.B_NeedsFragment;
import com.example.finddoctor.BloodSubFragment.B_O_N_DonnerFragment;
import com.example.finddoctor.BloodSubFragment.B_O_N_NeedsFragment;
import com.example.finddoctor.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class B_ONegetiveFragment extends Fragment {

    Button needsBtn,donnerBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_b__o_negetive, container, false);

        needsBtn=view.findViewById(R.id.bOnNeddsBtn);
        donnerBtn=view.findViewById(R.id.bOnDonnerBtn);

        needsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                B_O_N_NeedsFragment b_o_n_needsFragment=new B_O_N_NeedsFragment();
                FragmentManager fragmentManager=getChildFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.bAllFramelayout,b_o_n_needsFragment);
                fragmentTransaction.commit();
            }
        });

        donnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                B_O_N_DonnerFragment b_o_n_donnerFragment=new B_O_N_DonnerFragment();
                FragmentManager fragmentManager=getChildFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.bAllFramelayout,b_o_n_donnerFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }


}