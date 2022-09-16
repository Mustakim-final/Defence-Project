package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finddoctor.Model.Users;
import com.example.finddoctor.R;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditActivity extends AppCompatActivity {
    CircleImageView circleImageViewProfile;
    EditText editTextName,editTextPhone,editTextNid;
    TextView textViewBirth,profileAddress;
    Spinner spinnerBlood,spinnerGender,spinnerDivision,spinnerSubDivision,spinnerDistrict;

    ImageButton imageButtonCalendar;
    Button submitBtn;


    private Uri imageUri = null;
    private static final int IMAGE_REQUEST = 1;
    private static final int CAMERA_CODE = 200;
    private static final int GALLERY_CODE = 200;
    private static final int GALLERY_CODE1 = 300;
    StorageReference storageReference;
    StorageTask storageTask;
    FirebaseStorage firebaseStorage;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    String myID;

    private String[] blood,gender,division,sub_for_mymensinghdivision,district_for_jamalpur;
    private String[] sub_for_dhakaDivision,district_for_dhaka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        Permission();

        blood=getResources().getStringArray(R.array.profile_bloodSpinner);
        gender=getResources().getStringArray(R.array.profile_genderSpinner);

        circleImageViewProfile=findViewById(R.id.profilemainImage);
        editTextName=findViewById(R.id.profile_name);
        editTextPhone=findViewById(R.id.profile_Phone);
        spinnerBlood=findViewById(R.id.profile_BloodSpinner);
        editTextNid=findViewById(R.id.profile_nid);
        textViewBirth=findViewById(R.id.profile_birthText);
        imageButtonCalendar=findViewById(R.id.profile_birthBtn);
        spinnerGender=findViewById(R.id.profile_gender);
        spinnerDivision=findViewById(R.id.profile_divisionSpinner);
        spinnerSubDivision=findViewById(R.id.profile_sub_divisionSpinner);
        spinnerDistrict=findViewById(R.id.profile_districtSpinner);
        profileAddress=findViewById(R.id.profile_address);
        submitBtn=findViewById(R.id.profile_UpdateBtn);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        myID=firebaseUser.getUid();

        reference= FirebaseDatabase.getInstance().getReference("Users").child(myID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);

                editTextName.setText(users.getUsername());
                editTextPhone.setText(users.getPhone());
                editTextNid.setText(users.getNid());
                textViewBirth.setText(users.getBirthDate());
                profileAddress.setText(users.getDistrict()+" / "+users.getSubdivision()+" / "+users.getDivision());


                if (users.getImageUrl().equals("imageUrl")) {
                    circleImageViewProfile.setImageResource(R.drawable.ic_baseline_perm_identity_24);

                } else {
                    Glide.with(getApplicationContext()).load(users.getImageUrl()).into(circleImageViewProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        imageButtonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker=new DatePicker(getApplicationContext());

                int currentDay=datePicker.getDayOfMonth();
                int currentMonth=(datePicker.getMonth())+1;
                int currentYear=datePicker.getYear();

                DatePickerDialog datePickerDialog=new DatePickerDialog(ProfileEditActivity.this,

                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                textViewBirth.setText(day+"/"+(month+1)+"/"+year);
                            }
                        },currentYear,currentMonth,currentDay);

                datePickerDialog.show();



            }
        });

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.spinner_item,R.id.spinner_ID,blood);
        spinnerBlood.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(this,R.layout.spinner_item,R.id.spinner_ID,gender);
        spinnerGender.setAdapter(arrayAdapter1);

        division=getResources().getStringArray(R.array.division);
        sub_for_mymensinghdivision=getResources().getStringArray(R.array.sub_for_mymensingh);
        district_for_jamalpur=getResources().getStringArray(R.array.sub_for_jamalpur);

        sub_for_dhakaDivision=getResources().getStringArray(R.array.sub_for_Dhaka);
        district_for_dhaka=getResources().getStringArray(R.array.sub_for_dhaka);

        ArrayAdapter<String> arrayAdapterDivision=new ArrayAdapter<String>(this,R.layout.spinner_item,R.id.spinner_ID,division);

        ArrayAdapter<String> arrayAdapterDhaSubDivision=new ArrayAdapter<String>(this,R.layout.spinner_item,R.id.spinner_ID,sub_for_dhakaDivision);
        ArrayAdapter<String> arrayAdapterDhaDisDivision=new ArrayAdapter<String>(this,R.layout.spinner_item,R.id.spinner_ID,district_for_dhaka);

        ArrayAdapter<String> arrayAdapterSubDivision=new ArrayAdapter<String>(this,R.layout.spinner_item,R.id.spinner_ID,sub_for_mymensinghdivision);
        ArrayAdapter<String> arrayAdapterDistrict=new ArrayAdapter<String>(this,R.layout.spinner_item,R.id.spinner_ID,district_for_jamalpur);
        spinnerDivision.setAdapter(arrayAdapterDivision);

        spinnerDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position==0){
                    //
                }else if (position==1){
                    spinnerSubDivision.setAdapter(arrayAdapterDhaSubDivision);
                    spinnerSubDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i==0){
                                //
                            }else if (i==1){
                                spinnerDistrict.setAdapter(arrayAdapterDhaDisDivision);
                            }else if (i==2){

                            }else if (i==3){

                            }else if (i==4){

                            }else if (i==5){

                            }else if (i==6){

                            }else if (i==7){

                            }else if (i==8){

                            }else if (i==9){

                            }else if (i==10){

                            }else if (i==11){

                            }else if (i==12){

                            }else if (i==13){

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }else if (position==2){
                    //
                }else if (position==3){
                    //
                }else if (position==4){
                    //
                }else if (position==5){
                    //
                }else if (position==6){
                    //
                }else if (position==7){

                }else if (position==8) {
                    spinnerSubDivision.setAdapter(arrayAdapterSubDivision);
                    spinnerSubDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if (i==0){
                                //
                            }else if (i==1){
                                spinnerDistrict.setAdapter(arrayAdapterDistrict);
                            }else if (i==2){
                                //
                            }else if (i==3){
                                //
                            }else if (i==4){
                                //
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                myID=firebaseUser.getUid();

                String name=editTextName.getText().toString();
                String phone=editTextPhone.getText().toString();
                String blood=spinnerBlood.getSelectedItem().toString();
                String nid=editTextNid.getText().toString();
                String birthDate=textViewBirth.getText().toString();
                String gender=spinnerGender.getSelectedItem().toString();
                String division=spinnerDivision.getSelectedItem().toString();
                String subdivision=spinnerSubDivision.getSelectedItem().toString();
                String district=spinnerDistrict.getSelectedItem().toString();

                submitData(myID,name,phone,blood,nid,birthDate,gender,division,subdivision,district);
            }
        });




        circleImageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });

    }


    private void openImage() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEditActivity.this);
        builder.setTitle("Choose an options");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    openCamera();
                }
                if (i == 1) {
                    openGallery();
                }
            }

        });

        builder.create().show();
    }

    private void openCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp Pick");
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp Desc");
        imageUri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_CODE);
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType(("image/*"));
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, GALLERY_CODE);
    }

    private void Permission() {
        Dexter.withContext(getApplicationContext())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.RECORD_AUDIO
                ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK &&data!=null && data.getData()!=null ) {
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(circleImageViewProfile);
        }else if (requestCode==CAMERA_CODE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(circleImageViewProfile);
        }
    }

    public String getFileExtension(Uri imageUri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    private void submitData(String myID,String name, String phone, String blood, String nid, String birthDate, String gender, String division, String subdivision, String district) {
        if (imageUri==null){
            reference= FirebaseDatabase.getInstance().getReference("Users").child(myID);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("username",name);
            hashMap.put("phone",phone);
            hashMap.put("blood",blood);
            hashMap.put("nid",nid);
            hashMap.put("birthDate",birthDate);
            hashMap.put("gender",gender);
            hashMap.put("division",division);
            hashMap.put("subdivision",subdivision);
            hashMap.put("district",district);

            reference.updateChildren(hashMap);

            Toast.makeText(ProfileEditActivity.this,"Submit",Toast.LENGTH_SHORT).show();
        }else {
            storageReference = FirebaseStorage.getInstance().getReference("Users");
            StorageReference sreference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            sreference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUri = uri.toString();

                            reference=FirebaseDatabase.getInstance().getReference("Users").child(myID);
                            HashMap<String,Object> hashMap=new HashMap<>();
                            hashMap.put("username",name);
                            hashMap.put("phone",phone);
                            hashMap.put("blood",blood);
                            hashMap.put("nid",nid);
                            hashMap.put("birthDate",birthDate);
                            hashMap.put("gender",gender);
                            hashMap.put("division",division);
                            hashMap.put("subdivision",subdivision);
                            hashMap.put("district",district);
                            hashMap.put("imageUrl",imageUri);
                            reference.updateChildren(hashMap);

                            Toast.makeText(ProfileEditActivity.this,"Submit",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}