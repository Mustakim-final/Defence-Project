package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finddoctor.Adapter.Health.SebaAdapter;
import com.example.finddoctor.Adapter.HelthAdapter;
import com.example.finddoctor.Fragment.Health.PackageDoctorFragment;
import com.example.finddoctor.Fragment.Health.SebaFragment;
import com.example.finddoctor.Model.Seba;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HelthActivity extends AppCompatActivity {
    Toolbar toolbar;

    CircleImageView profileImage;
    private TextView myname;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helth);

        profileImage=findViewById(R.id.profileImage_ID);
        myname=findViewById(R.id.profileName_ID);


        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("সেবা প্যাকেজ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        tabLayout=findViewById(R.id.health_tabLayout_ID);
        viewPager=findViewById(R.id.health_viewPager_ID);

        FragmentPager fragmentPager=new FragmentPager(getSupportFragmentManager());
        fragmentPager.addFragment(new SebaFragment(),"সেবা প্যাকেজ");
        fragmentPager.addFragment(new PackageDoctorFragment(),"প্যাকেজ ডাক্তার");

        viewPager.setAdapter(fragmentPager);
        tabLayout.setupWithViewPager(viewPager);


    }

    class FragmentPager extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public FragmentPager(FragmentManager fm){
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