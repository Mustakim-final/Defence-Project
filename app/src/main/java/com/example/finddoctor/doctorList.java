package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


public class doctorList extends AppCompatActivity implements View.OnClickListener {
    private CardView korunaCard,heartCard,dentalCard,eyeCard;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("বিভাগ নির্বাচন করুন");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        korunaCard=findViewById(R.id.coruna_ID);
        korunaCard.setOnClickListener(this);
        heartCard=findViewById(R.id.heart_ID);
        heartCard.setOnClickListener(this);
        dentalCard=findViewById(R.id.dentel_ID);
        dentalCard.setOnClickListener(this);
        eyeCard=findViewById(R.id.eye_ID);
        eyeCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.coruna_ID){
            Intent intent=new Intent(doctorList.this,CorunaDoctor.class);
            intent.putExtra("coruna","করোনা ভাইরাস");
            startActivity(intent);
            overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
        }else if (v.getId()==R.id.heart_ID){
            Intent intent=new Intent(doctorList.this,HeartDoctorActivity.class);
            intent.putExtra("heart","হৃদরোগ");
            startActivity(intent);
            overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
        }else if (v.getId()==R.id.dentel_ID){
            Intent intent=new Intent(doctorList.this,DentistActivity.class);
            intent.putExtra("dentist","ডেন্টিষ্ট");
            startActivity(intent);
            overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
        }else if (v.getId()==R.id.eye_ID){
            Intent intent=new Intent(doctorList.this,OphthalmologistActivity.class);
            intent.putExtra("Optha","চক্ষু বিশেষজ্ঞ");
            startActivity(intent);
            overridePendingTransition(R.anim.slide_form_right,R.anim.slide_to_left);
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