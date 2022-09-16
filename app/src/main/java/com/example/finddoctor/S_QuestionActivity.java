package com.example.finddoctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finddoctor.Model.Users;
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

import java.util.HashMap;

public class S_QuestionActivity extends AppCompatActivity {

    Toolbar toolbar;

    String[] questionType;
    Spinner spinner;
    EditText questionEdit;
    RelativeLayout questionLayout;
    ImageView imageView;
    Button questionSubmitBtn;

    private static final int IMAGE_REQUEST=1;
    private static final int GALLERY_CODE=200;
    StorageReference storageReference;
    StorageTask storageTask;
    FirebaseStorage firebaseStorage;

    private Uri imageUri=null;
    FirebaseAuth firebaseAuth;

    DatabaseReference reference;
    FirebaseUser firebaseUser;

    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squestion);

        toolbar=findViewById(R.id.tool_bar_ID);
        toolbar.setTitle("Ask Your question");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        questionType=getResources().getStringArray(R.array.question_s);
        spinner=findViewById(R.id.questionTypeSpinner);
        questionEdit=findViewById(R.id.questionEditText);
        questionLayout=findViewById(R.id.questionImageLayout);
        questionSubmitBtn=findViewById(R.id.questionPostBtn);
        imageView=findViewById(R.id.selectImage);

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        userID=firebaseUser.getUid();

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(S_QuestionActivity.this,R.layout.spinner_item,R.id.spinner_ID,questionType);
        spinner.setAdapter(arrayAdapter);


        questionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallary();
            }
        });

        questionSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question=questionEdit.getText().toString();
                String questionType=spinner.getSelectedItem().toString();
                submitData(userID,question,questionType);
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
            Glide.with(this).load(imageUri).into(imageView);
        }
    }

    public String getFileExtension(Uri imageUri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }


    private void submitData(String userID, String question, String questionType) {


        if (imageUri==null){

            DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("Users").child(userID);
            reference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users users=snapshot.getValue(Users.class);
                    String username=users.getUsername();
                    String imageUrl=users.getImageUrl();



                    reference= FirebaseDatabase.getInstance().getReference();
                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("id",userID);
                    hashMap.put("username",username);
                    hashMap.put("imageUrl",imageUrl);
                    hashMap.put("question",question);
                    hashMap.put("questionType",questionType);
                    hashMap.put("questionImage","questionImage");

                    reference.child("user_question").push().setValue(hashMap);

                    Toast.makeText(S_QuestionActivity.this,"Submit",Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else {
            storageReference = FirebaseStorage.getInstance().getReference("user_question");
            StorageReference sreference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            sreference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUri = uri.toString();

                            DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("Users").child(userID);
                            reference1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    Users users=snapshot.getValue(Users.class);
                                    String username=users.getUsername();
                                    String imageUrl=users.getImageUrl();


                                    reference=FirebaseDatabase.getInstance().getReference();
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("id",userID);
                                    hashMap.put("username",username);
                                    hashMap.put("imageUrl",imageUrl);
                                    hashMap.put("question",question);
                                    hashMap.put("questionType",questionType);
                                    hashMap.put("questionImage",imageUri);
                                    reference.child("user_question").push().setValue(hashMap);

                                    Toast.makeText(S_QuestionActivity.this,"Submit",Toast.LENGTH_SHORT).show();

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