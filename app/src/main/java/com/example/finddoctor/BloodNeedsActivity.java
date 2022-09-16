package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.finddoctor.Model.Users;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class BloodNeedsActivity extends AppCompatActivity {

    Spinner groupSpinner, typeSpinner, requireSpinner;
    TextView date_and_timeText;
    ImageButton dateBtn;
    EditText numberEdit, locationEdit,giftEdit,descriptionEdit;
    Button submitBtn;

    FusedLocationProviderClient fusedLocationProviderClient;

    String[] group, type, require;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    String myId;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_needs);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("রক্তের অনুরোধ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        group = getResources().getStringArray(R.array.blood);
        type = getResources().getStringArray(R.array.blood_type1);
        require = getResources().getStringArray(R.array.blood_required);

        groupSpinner = findViewById(R.id.bloodGroupSpiner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.spinner_ID, group);
        groupSpinner.setAdapter(arrayAdapter);

        typeSpinner = findViewById(R.id.bloodTypeSpinner);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.spinner_ID, type);
        typeSpinner.setAdapter(arrayAdapter1);

        requireSpinner = findViewById(R.id.bloodRequiredSpinner);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.spinner_ID, require);
        requireSpinner.setAdapter(arrayAdapter2);


        date_and_timeText = findViewById(R.id.date_and_time);
        dateBtn = findViewById(R.id.date_and_timehBtn);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker = new DatePicker(BloodNeedsActivity.this);

                int currentDay = datePicker.getDayOfMonth();
                int currentMonth = (datePicker.getMonth()) + 1;
                int currentYear = datePicker.getYear();

                DatePickerDialog datePickerDialog = new DatePickerDialog(BloodNeedsActivity.this,

                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                TimePicker timePicker = new TimePicker(BloodNeedsActivity.this);

                                int currentHour = timePicker.getCurrentHour();
                                int currentMinute = timePicker.getCurrentMinute();

                                TimePickerDialog timePickerDialog = new TimePickerDialog(BloodNeedsActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                                                date_and_timeText.setText(day + "/" + (month + 1) + "/" + year + "  " + hour + " : " + minute);
                                            }
                                        }, currentHour, currentMinute, true);

                                timePickerDialog.show();

                            }
                        }, currentYear, currentMonth, currentDay);

                datePickerDialog.show();
            }
        });


        numberEdit = findViewById(R.id.bloodPhoneNumber);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myId = firebaseUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(myId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);

                numberEdit.setText(users.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        locationEdit = findViewById(R.id.bloodLocationText_ID);
        setLocation();

        giftEdit=findViewById(R.id.bloodGiftEdit_ID);
        descriptionEdit=findViewById(R.id.bloodDescriptionEdit_ID);
        submitBtn=findViewById(R.id.bloodSubmitBtn);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String group=groupSpinner.getSelectedItem().toString();
                String type=typeSpinner.getSelectedItem().toString();
                String require=requireSpinner.getSelectedItem().toString();
                String date_and_time=date_and_timeText.getText().toString();
                String number=numberEdit.getText().toString();
                String location=locationEdit.getText().toString();
                String gift=giftEdit.getText().toString();
                String description=descriptionEdit.getText().toString();

                reference=FirebaseDatabase.getInstance().getReference("Users").child(myId);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users users=snapshot.getValue(Users.class);
                        String imageUrl=users.getImageUrl();
                        String name=users.getUsername();
                        String id=users.getId();

                        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference();
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("b_group",group);
                        hashMap.put("b_type",type);
                        hashMap.put("b_require",require);
                        hashMap.put("b_datetime",date_and_time);
                        hashMap.put("b_number",number);
                        hashMap.put("b_location",location);
                        hashMap.put("b_gift",gift);
                        hashMap.put("b_description",description);
                        hashMap.put("id",id);
                        hashMap.put("imageUrl",imageUrl);
                        hashMap.put("name",name);
                        reference1.child("B_Require").push().setValue(hashMap);
                        Toast.makeText(BloodNeedsActivity.this,"submit",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });




    }

    private void setLocation() {

        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location=task.getResult();

                    if (location!=null){


                        try {
                            Geocoder geocoder=new Geocoder(BloodNeedsActivity.this,Locale.getDefault());
                            List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                            locationEdit.setText(Html.fromHtml("<font color='#6200EE'><b>Address:</b></font>"+addresses.get(0).getAddressLine(0)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }
            });
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



