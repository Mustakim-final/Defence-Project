package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.finddoctor.Fragment.All_doctor_Fragment;
import com.example.finddoctor.Fragment.CorunaFragment;
import com.example.finddoctor.Fragment.DentistFragment;
import com.example.finddoctor.Fragment.DermatologistFragment;
import com.example.finddoctor.Fragment.Diabetes_and_hormonesFragment;
import com.example.finddoctor.Fragment.Heart_diseaseFragment;
import com.example.finddoctor.Fragment.Nose_ear_and_throat_specialistFragment;
import com.example.finddoctor.Fragment.PilesFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class EmergencyDoctorActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    Toolbar toolbar;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_doctor);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("জরুরী ডাক্তার");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        tabLayout=findViewById(R.id.emergency_doctor_tabLayout_ID);
        viewPager=findViewById(R.id.emergency_doctor_viewPager_ID);

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new All_doctor_Fragment(),"সব");
        viewPagerAdapter.addFragment(new CorunaFragment(),"করোনা");
        viewPagerAdapter.addFragment(new Heart_diseaseFragment(),"হৃদরোগ");
        viewPagerAdapter.addFragment(new PilesFragment(),"কোলোরেক্টাল সার্জারি(পাইলস)");
        viewPagerAdapter.addFragment(new DentistFragment(),"ডেন্টিষ্ট");
        viewPagerAdapter.addFragment(new DermatologistFragment(),"চর্মরোগ বিশেষজ্ঞ");
        viewPagerAdapter.addFragment(new Diabetes_and_hormonesFragment(),"ডায়াবেটিস ও হরমোন");
        viewPagerAdapter.addFragment(new Nose_ear_and_throat_specialistFragment(),"নাক,কান ও গলা বিশেষজ্ঞ");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    class ViewPagerAdapter extends FragmentPagerAdapter{

        ArrayList<Fragment> fragments;
        ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);

            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_form_left,R.anim.slide_to_right);
    }


}