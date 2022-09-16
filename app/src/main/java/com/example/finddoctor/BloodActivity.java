package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.finddoctor.Adapter.BloodAdapter;
import com.example.finddoctor.BloodFragment.B_ABNegetiveFragment;
import com.example.finddoctor.BloodFragment.B_ABPositeveFragment;
import com.example.finddoctor.BloodFragment.B_APositiveFragment;
import com.example.finddoctor.BloodFragment.B_AllFragment;
import com.example.finddoctor.BloodFragment.B_AnegetiveFragment;
import com.example.finddoctor.BloodFragment.B_BNegetiveFragment;
import com.example.finddoctor.BloodFragment.B_BPositiveFragment;
import com.example.finddoctor.BloodFragment.B_ONegetiveFragment;
import com.example.finddoctor.BloodFragment.B_OPositiveFragment;


public class BloodActivity extends AppCompatActivity {


    Toolbar toolbar;

    RecyclerView recyclerView;
    String[] title;
    BloodAdapter bloodAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("রক্ত দান");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title=getResources().getStringArray(R.array.blood);


        recyclerView=findViewById(R.id.bloodRecyclerView);

        bloodAdapter=new BloodAdapter(BloodActivity.this,title);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bloodAdapter);

        bloodAdapter.setOnClickListener(new BloodAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {


                if (position==0){


                    B_AllFragment b_allFragment=new B_AllFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoyt,b_allFragment);
                    fragmentTransaction.commit();

                }else if (position==1){


                    B_APositiveFragment b_aPositiveFragment=new B_APositiveFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoyt,b_aPositiveFragment);
                    fragmentTransaction.commitNow();
                }else if (position==2){

                    B_AnegetiveFragment b_anegetiveFragment=new B_AnegetiveFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoyt,b_anegetiveFragment);
                    fragmentTransaction.commitNow();

                }else if (position==3){

                    B_BPositiveFragment b_bPositiveFragment=new B_BPositiveFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoyt,b_bPositiveFragment);
                    fragmentTransaction.commitNow();
                }else if (position==4){
                    B_BNegetiveFragment b_bNegetiveFragment=new B_BNegetiveFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoyt,b_bNegetiveFragment);
                    fragmentTransaction.commitNow();
                }else if (position==5){
                    B_ABPositeveFragment b_abPositeveFragment=new B_ABPositeveFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoyt,b_abPositeveFragment);
                    fragmentTransaction.commitNow();
                }else if (position==6){
                    B_ABNegetiveFragment b_abNegetiveFragment=new B_ABNegetiveFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoyt,b_abNegetiveFragment);
                    fragmentTransaction.commitNow();
                }else if (position==7){
                    B_OPositiveFragment b_oPositiveFragment=new B_OPositiveFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoyt,b_oPositiveFragment);
                    fragmentTransaction.commitNow();
                }else if (position==8){
                    B_ONegetiveFragment b_oNegetiveFragment=new B_ONegetiveFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoyt,b_oNegetiveFragment);
                    fragmentTransaction.commitNow();
                }
            }
        });



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