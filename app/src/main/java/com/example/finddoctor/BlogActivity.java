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

import com.example.finddoctor.BlogFragment.Blog_ChildFragment;
import com.example.finddoctor.BlogFragment.Blog_ColorectalFragment;
import com.example.finddoctor.BlogFragment.Blog_EyeFragment;
import com.example.finddoctor.BlogFragment.Blog_GynFragment;
import com.example.finddoctor.BlogFragment.Blog_MedichenFragment;
import com.example.finddoctor.BlogFragment.Blog_OrthopedicFragment;
import com.example.finddoctor.BlogFragment.Blog_ScreenFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class BlogActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("হেলথ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout=findViewById(R.id.blog_tabLayout_ID);
        viewPager=findViewById(R.id.blog_viewPager_ID);

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new Blog_GynFragment(),"GYN AND OBS");
        viewPagerAdapter.addFragment(new Blog_ScreenFragment(),"ত্বক ও এলার্জি");
        viewPagerAdapter.addFragment(new Blog_ColorectalFragment(),"COLORECTAL SURGERY");
        viewPagerAdapter.addFragment(new Blog_MedichenFragment(),"মেডিসিন");
        viewPagerAdapter.addFragment(new Blog_ChildFragment(),"CHILD DOCTOR");
        viewPagerAdapter.addFragment(new Blog_EyeFragment(),"EYE");
        viewPagerAdapter.addFragment(new Blog_OrthopedicFragment(),"অর্থোপেডিক চিকিনৎসা");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

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