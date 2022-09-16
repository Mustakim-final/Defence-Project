package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finddoctor.Adapter.DateAdapter;
import com.example.finddoctor.Adapter.TimeAdapter;
import com.example.finddoctor.Model.DatePik;
import com.example.finddoctor.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class General_ApplyActivity extends AppCompatActivity {
    Spinner doctorChamberSpinner,patientTypeSpinner;
    String[] doctorChamber,patientType;
    RelativeLayout dateRLayout;
    String userID;
    String myID;
    Intent intent;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    DateAdapter dateAdapter;
    TimeAdapter timeAdapter;
    List<DatePik> datePikList;
    RecyclerView recyclerView,timeRecycler;

    TextView doctorFeeText,testText,testText1,problemEdit;

    RelativeLayout timeRelative;
    TextView patientNameText;
    Toolbar toolbar;
    Button submitBtn;
    String chamber,type,problem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_apply);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("অ্যাপয়েন্টমেন্ট");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        intent=getIntent();
        userID=intent.getStringExtra("userID");

        // patient name show
        patientNameText=findViewById(R.id.patientName);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        myID=firebaseUser.getUid();
        reference=FirebaseDatabase.getInstance().getReference("Users").child(myID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);

                patientNameText.setText(users.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //patient name show end

        doctorChamber=getResources().getStringArray(R.array.doctor_chamber);
        patientType=getResources().getStringArray(R.array.patient_type);

        doctorFeeText=findViewById(R.id.doctorFee_ID);
        timeRelative=findViewById(R.id.timeRelative);
        submitBtn=findViewById(R.id.gen_appointmentBtn);
        testText=findViewById(R.id.testText);
        testText1=findViewById(R.id.testText1);
        problemEdit=findViewById(R.id.problemEdit);

        doctorChamberSpinner=findViewById(R.id.doctorChamberSpinner);
        patientTypeSpinner=findViewById(R.id.patientTypeSpinner);

        dateRLayout=findViewById(R.id.dateRelativeLayout);


        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.spinner_item,R.id.spinner_ID,doctorChamber);
        doctorChamberSpinner.setAdapter(arrayAdapter);


        doctorChamberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position==0){
                    dateRLayout.setVisibility(View.GONE);
                    doctorFeeText.setVisibility(View.GONE);
                    timeRelative.setVisibility(View.GONE);
                }else if (position==1){
                    dateRLayout.setVisibility(View.VISIBLE);
                    String d=adapterView.getSelectedItem().toString();
                    testText.setText(d);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(General_ApplyActivity.this,R.layout.spinner_item,R.id.spinner_ID,patientType);
        patientTypeSpinner.setAdapter(arrayAdapter1);

        patientTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                testText1.setText(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });







        recyclerView=findViewById(R.id.date_recycler_ID);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        datePikList=new ArrayList<>();



        timeRecycler=findViewById(R.id.time_recycler_ID);
        timeRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager1=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        timeRecycler.setLayoutManager(layoutManager1);
        datePikList=new ArrayList<>();
        readDate(userID);








    }




    private void readDate(String userID){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        reference= FirebaseDatabase.getInstance().getReference("Doctor List");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datePikList.clear();

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    DatePik datePik=dataSnapshot.getValue(DatePik.class);

                    if (datePik.getId().equals(userID)){
                        datePikList.add(datePik);
                    }


                }

                dateAdapter=new DateAdapter(General_ApplyActivity.this,datePikList);
                recyclerView.setAdapter(dateAdapter);


                dateAdapter.setOnClickListener(new DateAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(int position,String date1) {
                        chamber=testText.getText().toString();
                        type=testText1.getText().toString();


                        reference=FirebaseDatabase.getInstance().getReference("Doctor List").child(userID);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                DatePik datePik=snapshot.getValue(DatePik.class);
                                doctorFeeText.setVisibility(View.VISIBLE);
                                doctorFeeText.setText("ডাক্তারের ফিঃ "+datePik.getFees()+" টাকা");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        timeRelative.setVisibility(View.VISIBLE);


                        reference= FirebaseDatabase.getInstance().getReference("Doctor List");
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                datePikList.clear();
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    DatePik datePik=dataSnapshot.getValue(DatePik.class);
                                    if (datePik.getId().equals(userID)){
                                        datePikList.add(datePik);
                                    }

                                }

                                timeAdapter=new TimeAdapter(General_ApplyActivity.this,datePikList);
                                timeRecycler.setAdapter(timeAdapter);

                                timeAdapter.setOnClickListener(new TimeAdapter.onItemClickListener() {
                                    @Override
                                    public void onItemClick(int position, String time1, String time2, String time3, String time4, String time5, String time6, String time7,String fee) {
                                        sentData(date1,time1,fee,chamber,type,userID);
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });




                    }

                    @Override
                    public void onItmeCLick1(int position, String date2) {
                        chamber=testText.getText().toString();
                        type=testText1.getText().toString();


                        reference=FirebaseDatabase.getInstance().getReference("Doctor List").child(userID);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                DatePik datePik=snapshot.getValue(DatePik.class);
                                doctorFeeText.setVisibility(View.VISIBLE);
                                String fee=datePik.getFees();
                                doctorFeeText.setText("ডাক্তারের ফিঃ "+fee+" টাকা");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        timeRelative.setVisibility(View.VISIBLE);

                        reference= FirebaseDatabase.getInstance().getReference("Doctor List");
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                datePikList.clear();
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    DatePik datePik=dataSnapshot.getValue(DatePik.class);

                                    if (datePik.getId().equals(userID)){
                                        datePikList.add(datePik);
                                    }

                                }

                                timeAdapter=new TimeAdapter(General_ApplyActivity.this,datePikList);
                                timeRecycler.setAdapter(timeAdapter);

                                timeAdapter.setOnClickListener(new TimeAdapter.onItemClickListener() {
                                    @Override
                                    public void onItemClick(int position, String time1, String time2, String time3, String time4, String time5, String time6, String time7,String fee) {
                                        sentData(date2,time2,fee,chamber,type,userID);
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onItmeCLick2(int position, String date3) {

                        chamber=testText.getText().toString();
                        type=testText1.getText().toString();


                        reference=FirebaseDatabase.getInstance().getReference("Doctor List").child(userID);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                DatePik datePik=snapshot.getValue(DatePik.class);
                                doctorFeeText.setVisibility(View.VISIBLE);
                                String fee=datePik.getFees();
                                doctorFeeText.setText("ডাক্তারের ফিঃ "+fee+" টাকা");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        timeRelative.setVisibility(View.VISIBLE);

                        reference= FirebaseDatabase.getInstance().getReference("Doctor List");
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                datePikList.clear();
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    DatePik datePik=dataSnapshot.getValue(DatePik.class);

                                    if (datePik.getId().equals(userID)){
                                        datePikList.add(datePik);
                                    }

                                }

                                timeAdapter=new TimeAdapter(General_ApplyActivity.this,datePikList);
                                timeRecycler.setAdapter(timeAdapter);

                                timeAdapter.setOnClickListener(new TimeAdapter.onItemClickListener() {
                                    @Override
                                    public void onItemClick(int position, String time1, String time2, String time3, String time4, String time5, String time6, String time7,String fee) {
                                        sentData(date3,time3,fee,chamber,type,userID);
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void onItmeCLick3(int position, String date4) {
                        chamber=testText.getText().toString();
                        type=testText1.getText().toString();


                        reference=FirebaseDatabase.getInstance().getReference("Doctor List").child(userID);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                DatePik datePik=snapshot.getValue(DatePik.class);
                                doctorFeeText.setVisibility(View.VISIBLE);
                                String fee=datePik.getFees();
                                doctorFeeText.setText("ডাক্তারের ফিঃ "+fee+" টাকা");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        timeRelative.setVisibility(View.VISIBLE);

                        reference= FirebaseDatabase.getInstance().getReference("Doctor List");
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                datePikList.clear();
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    DatePik datePik=dataSnapshot.getValue(DatePik.class);

                                    if (datePik.getId().equals(userID)){
                                        datePikList.add(datePik);
                                    }

                                }

                                timeAdapter=new TimeAdapter(General_ApplyActivity.this,datePikList);
                                timeRecycler.setAdapter(timeAdapter);

                                timeAdapter.setOnClickListener(new TimeAdapter.onItemClickListener() {
                                    @Override
                                    public void onItemClick(int position, String time1, String time2, String time3, String time4, String time5, String time6, String time7,String fee) {
                                        sentData(date4,time4,fee,chamber,type,userID);
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void onItmeCLick4(int position, String date5) {
                        chamber=testText.getText().toString();
                        type=testText1.getText().toString();


                        reference=FirebaseDatabase.getInstance().getReference("Doctor List").child(userID);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                DatePik datePik=snapshot.getValue(DatePik.class);
                                doctorFeeText.setVisibility(View.VISIBLE);
                                String fee=datePik.getFees();
                                doctorFeeText.setText("ডাক্তারের ফিঃ "+fee+" টাকা");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        timeRelative.setVisibility(View.VISIBLE);

                        reference= FirebaseDatabase.getInstance().getReference("Doctor List");
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                datePikList.clear();
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    DatePik datePik=dataSnapshot.getValue(DatePik.class);

                                    if (datePik.getId().equals(userID)){
                                        datePikList.add(datePik);
                                    }

                                }

                                timeAdapter=new TimeAdapter(General_ApplyActivity.this,datePikList);
                                timeRecycler.setAdapter(timeAdapter);

                                timeAdapter.setOnClickListener(new TimeAdapter.onItemClickListener() {
                                    @Override
                                    public void onItemClick(int position, String time1, String time2, String time3, String time4, String time5, String time6, String time7,String fee) {
                                        sentData(date5,time5,fee,type,chamber,userID);
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void onItmeCLick5(int position, String date6) {

                        chamber=testText.getText().toString();
                        type=testText1.getText().toString();


                        reference=FirebaseDatabase.getInstance().getReference("Doctor List").child(userID);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                DatePik datePik=snapshot.getValue(DatePik.class);
                                doctorFeeText.setVisibility(View.VISIBLE);
                                String fee=datePik.getFees();
                                doctorFeeText.setText("ডাক্তারের ফিঃ "+fee+" টাকা");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        timeRelative.setVisibility(View.VISIBLE);

                        reference= FirebaseDatabase.getInstance().getReference("Doctor List");
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                datePikList.clear();
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    DatePik datePik=dataSnapshot.getValue(DatePik.class);

                                    if (datePik.getId().equals(userID)){
                                        datePikList.add(datePik);
                                    }

                                }

                                timeAdapter=new TimeAdapter(General_ApplyActivity.this,datePikList);
                                timeRecycler.setAdapter(timeAdapter);

                                timeAdapter.setOnClickListener(new TimeAdapter.onItemClickListener() {
                                    @Override
                                    public void onItemClick(int position, String time1, String time2, String time3, String time4, String time5, String time6, String time7,String fee) {
                                        sentData(date6,time6,fee,chamber,type,userID);
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void onItmeCLick6(int position, String date7) {

                        chamber=testText.getText().toString();
                        type=testText1.getText().toString();


                        reference=FirebaseDatabase.getInstance().getReference("Doctor List").child(userID);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                DatePik datePik=snapshot.getValue(DatePik.class);
                                doctorFeeText.setVisibility(View.VISIBLE);
                                String fee=datePik.getFees();
                                doctorFeeText.setText("ডাক্তারের ফিঃ "+fee+" টাকা");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        timeRelative.setVisibility(View.VISIBLE);

                        reference= FirebaseDatabase.getInstance().getReference("Doctor List");
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                datePikList.clear();
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    DatePik datePik=dataSnapshot.getValue(DatePik.class);

                                    if (datePik.getId().equals(userID)){
                                        datePikList.add(datePik);
                                    }

                                }

                                timeAdapter=new TimeAdapter(General_ApplyActivity.this,datePikList);
                                timeRecycler.setAdapter(timeAdapter);

                                timeAdapter.setOnClickListener(new TimeAdapter.onItemClickListener() {
                                    @Override
                                    public void onItemClick(int position, String time1, String time2, String time3, String time4, String time5, String time6, String time7,String fee) {
                                        sentData(date7,time7,fee,chamber,type,userID);
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    private void sentData(String date1,String time1,String fee,String chamber,String type,String userID) {
        //Toast.makeText(General_ApplyActivity.this,""+problem,Toast.LENGTH_SHORT).show();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                problem=problemEdit.getText().toString();

                Intent intent=new Intent(General_ApplyActivity.this,General_Apply_FormActivity.class);
                intent.putExtra("date",date1);
                intent.putExtra("time",time1);
                intent.putExtra("fee",fee);
                intent.putExtra("chamber",chamber);
                intent.putExtra("type",type);
                intent.putExtra("userID",userID);
                intent.putExtra("problem",problem);
                startActivity(intent);

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

}