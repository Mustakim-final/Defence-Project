package com.example.finddoctor.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.finddoctor.Adapter.Profile.PharmacyAdapter;
import com.example.finddoctor.Model.Pharmacy;
import com.example.finddoctor.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Pro_PharmacyActivity extends AppCompatActivity {

    PharmacyAdapter pharmacyAdapter;
    RecyclerView recyclerView;
    List<Pharmacy> pharmacyList;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    FirebaseStorage firebaseStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_pharmacy);


        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String myId=firebaseUser.getUid();

        recyclerView=findViewById(R.id.recycler_ID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pharmacyList=new ArrayList<>();

        readData(myId);
    }

    private void readData(String myId) {
        reference= FirebaseDatabase.getInstance().getReference("pharmacy_apply");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pharmacyList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Pharmacy pharmacy=dataSnapshot.getValue(Pharmacy.class);
                    pharmacy.setKey(dataSnapshot.getKey());
                    if (pharmacy.getId().equals(myId)){
                        pharmacyList.add(pharmacy);
                    }
                }

                pharmacyAdapter=new PharmacyAdapter(Pro_PharmacyActivity.this,pharmacyList);
                recyclerView.setAdapter(pharmacyAdapter);

                pharmacyAdapter.setOnClickListener(new PharmacyAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }

                    @Override
                    public void onDelete(int position) {
                        Pharmacy pharmacy=pharmacyList.get(position);
                        String key=pharmacy.getKey();
                        reference.child(key).removeValue();

//                        StorageReference storageReference=firebaseStorage.getReferenceFromUrl(pharmacy.getPrescription());
//                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                reference.child(key).removeValue();
//                            }
//                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}