package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.example.finddoctor.Fragment.ChatFragment;
import com.example.finddoctor.Fragment.DListFragment;
import com.example.finddoctor.Model.Users;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorChatListActivity extends AppCompatActivity {

    TextView userNameText,patentName;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_chat_list);

        patentName=findViewById(R.id.patientName_ID);
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);
                patentName.setText(users.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tabLayout=findViewById(R.id.tabLayout_ID);
        viewPager=findViewById(R.id.viewPager_ID);
        ViewPaggerAdapter viewPaggerAdapter=new ViewPaggerAdapter(getSupportFragmentManager());
        viewPaggerAdapter.addFragment(new ChatFragment(),"Chat");
        viewPaggerAdapter.addFragment(new DListFragment(),"DoctorList");
        viewPager.setAdapter(viewPaggerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    class ViewPaggerAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;

        private ArrayList<String> titles;

        public ViewPaggerAdapter(FragmentManager fm){
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
}