package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PharmacyApplyActivity extends AppCompatActivity {

    Toolbar toolbar;
    Intent intent;
    String address,locality,title,id,note;
    TextView textView;

    ImageView uploadImage;
    EditText editTextNote;

    Button pharmacyBtn;

    DatabaseReference reference;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView textView1, textView2, textView3, textView4, textView5;


    private static final int IMAGE_REQUEST=1;
    private static final int CAMERA_CODE=200;
    private static final int GALLERY_CODE=200;
    private static final int GALLERY_CODE1=300;
    StorageReference storageReference;
    StorageTask storageTask;
    FirebaseStorage firebaseStorage;

    private Uri imageUri=null;
    FirebaseAuth firebaseAuth;


    FirebaseUser firebaseUser;
    ProgressBar progressBar;


    String pharmacy_address,pharmacy_title,pharmacy_phone,pharmacy_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_apply);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("প্রেসক্রিপশন আপলোড করুন");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent=getIntent();
        pharmacy_address=intent.getStringExtra("address");
        pharmacy_title=intent.getStringExtra("title");
        pharmacy_phone=intent.getStringExtra("phone");
        pharmacy_id=intent.getStringExtra("id");




        uploadImage=findViewById(R.id.prescriptionImage_ID);
        editTextNote=findViewById(R.id.prescriptionNote_ID);
        pharmacyBtn=findViewById(R.id.pharmacyBtn_ID);
        progressBar=findViewById(R.id.progressBar);
        editTextNote.setText(title);


        textView1 = findViewById(R.id.text_view1);

        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallary();
            }
        });


        pharmacyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                id=firebaseUser.getUid();

                note=editTextNote.getText().toString();
                getLocateion(note,id,pharmacy_address,pharmacy_title,pharmacy_phone,pharmacy_id);

                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    private void openGallary() {
        Intent intent=new Intent();
        intent.setType(("image/*"));
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,GALLERY_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK &&data!=null && data.getData()!=null ) {
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(uploadImage);
        }
    }

    public String getFileExtension(Uri imageUri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }


    private void getLocateion(String note,String id,String pharmacy_address,String pharmacy_title,String pharmacy_phone,String pharmacy_id) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {


                        try {
                            Geocoder geocoder = new Geocoder(PharmacyApplyActivity.this, Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


                            textView1.setText(Html.fromHtml("<font color='#6200EE'><b> Latitude:</b></br></br></font>" + addresses.get(0).getLatitude()));
                            textView2.setText(Html.fromHtml("<font color='#6200EE'><b> Longitude:</b></br></br></font>" + addresses.get(0).getLongitude()));
                            textView3.setText(Html.fromHtml("<font color='#6200EE'><b> Country:</b></br></br></font>" + addresses.get(0).getCountryName()));
                            textView4.setText(Html.fromHtml("<font color='#6200EE'><b> Locality:</b></br></br></font>" + addresses.get(0).getLocality()));
                            textView5.setText(Html.fromHtml("<font color='#6200EE'><b> Address:</b></br></br></font>" + addresses.get(0).getAddressLine(0)));

                            address=textView5.getText().toString();
                            locality=textView4.getText().toString();




                            if (imageUri==null){

                                DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("Users").child(id);
                                reference1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        Users users=snapshot.getValue(Users.class);
                                        String name=users.getUsername();
                                        String image=users.getImageUrl();
                                        String phone=users.getPhone();


                                        reference= FirebaseDatabase.getInstance().getReference("pharmacy_apply");
                                        String new_id=reference.push().getKey();
                                        HashMap<String,Object> hashMap=new HashMap<>();
                                        hashMap.put("id",id);
                                        hashMap.put("name",name);
                                        hashMap.put("image",image);
                                        hashMap.put("phone",phone);
                                        hashMap.put("note",note);
                                        hashMap.put("new_id",new_id);
                                        hashMap.put("address",address);
                                        hashMap.put("prescription","prescription");
                                        hashMap.put("pharmacy_address",pharmacy_address);
                                        hashMap.put("pharmacy_title",pharmacy_title);
                                        hashMap.put("pharmacy_phone",pharmacy_phone);
                                        hashMap.put("pharmacy_id",pharmacy_id);

                                        reference.child(new_id).setValue(hashMap);
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(PharmacyApplyActivity.this,"submit",Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }else {
                                storageReference = FirebaseStorage.getInstance().getReference("pharmacy_apply");
                                StorageReference sreference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                                sreference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> uriTask=taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                        uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String pharmacy=uri.toString();

                                                DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("Users").child(id);
                                                reference1.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                        Users users=snapshot.getValue(Users.class);
                                                        String name=users.getUsername();
                                                        String image=users.getImageUrl();
                                                        String phone=users.getPhone();

                                                        reference= FirebaseDatabase.getInstance().getReference("pharmacy_apply");
                                                        String new_id=reference.push().getKey();
                                                        HashMap<String,Object> hashMap=new HashMap<>();
                                                        hashMap.put("id",id);
                                                        hashMap.put("name",name);
                                                        hashMap.put("image",image);
                                                        hashMap.put("phone",phone);
                                                        hashMap.put("note",note);
                                                        hashMap.put("new_id",new_id);
                                                        hashMap.put("address",address);
                                                        hashMap.put("prescription",pharmacy);
                                                        hashMap.put("pharmacy_address",pharmacy_address);
                                                        hashMap.put("pharmacy_title",pharmacy_title);
                                                        hashMap.put("pharmacy_phone",pharmacy_phone);
                                                        hashMap.put("pharmacy_id",pharmacy_id);

                                                        reference.child(new_id).setValue(hashMap);

                                                        progressBar.setVisibility(View.GONE);

                                                        Toast.makeText(PharmacyApplyActivity.this,"submit",Toast.LENGTH_SHORT).show();

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                            }
                                        });
                                    }
                                });

                            }





                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(PharmacyApplyActivity.this,"দয়া করে লোকেশন চালু করুন",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else {
            ActivityCompat.requestPermissions(PharmacyApplyActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}