package com.example.tiswamcrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class LeadDetailsWithOutBDMActivity extends AppCompatActivity {

    TextView BusinessName, BusinessAddress, LeadName, LeadEmail, LeadPhone, MeetingDate;
    TextView MeetingTime, BDEName, BDEEmail, BDEPhone;
    Spinner BDMSpinner;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_details_with_out_b_d_m);

        firebaseFirestore = FirebaseFirestore.getInstance();

        BusinessName = findViewById(R.id.lead_org_text);
        BusinessAddress = findViewById(R.id.lead_org_address);
        LeadName = findViewById(R.id.lead_name_text);
        LeadEmail = findViewById(R.id.lead_email_text);
        LeadPhone = findViewById(R.id.lead_phone_text);
        MeetingDate = findViewById(R.id.d_date_text);
        MeetingTime = findViewById(R.id.d_time_text);
        BDEName = findViewById(R.id.bde_name_text);
        BDEEmail = findViewById(R.id.bde_email_text);
        BDEPhone = findViewById(R.id.bde_phone_text);
        BDMSpinner = findViewById(R.id.bdm_name_spinner);

        Intent i = getIntent();

        final String id = i.getStringExtra("id");
        String business = i.getStringExtra("org");
        String address = i.getStringExtra("address");
        String name = i.getStringExtra("name");
        String phone = i.getStringExtra("phone");
        String email = i.getStringExtra("email");
        String date = i.getStringExtra("date");
        String time = i.getStringExtra("time");
        String bde_name = i.getStringExtra("bde_name");
        String bde_phone = i.getStringExtra("bde_phone");
        String bde_email = i.getStringExtra("bde_email");

        BusinessName.setText(business);
        BusinessAddress.setText(address);
        LeadName.setText(name);
        LeadEmail.setText(email);
        LeadPhone.setText(phone);
        MeetingDate.setText(date);
        MeetingTime.setText(time);
        BDEName.setText(bde_name);
        BDEPhone.setText(bde_phone);
        BDEEmail.setText(bde_email);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.loading, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BDMSpinner.setAdapter(adapter);

        loadBDM();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        Button Assignment = findViewById(R.id.assign_btn);
        Assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(BDMSpinner.getSelectedItem().toString().trim().equals("Loading...")){
                    Toast.makeText(getApplicationContext(),"Please wait for BDM list to load",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(BDMSpinner.getSelectedItem().toString().trim().equals("Select BDM")){
                    Toast.makeText(getApplicationContext(),"Please select a BDM",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("Assigning Business Development Manager...");
                progressDialog.show();

                assert id != null;
                final DocumentReference documentReference = firebaseFirestore.collection("Leads").document(id);
                documentReference.update("bdm",BDMSpinner.getSelectedItem().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            documentReference.update("bdm_assigned_status","yes")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"BDM assigned",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),HomePage.class));
                                        finish();

                                    }

                                    else{
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),
                                                Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                        }

                        else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });



    }

    private void loadBDM() {

        DocumentReference documentReference = firebaseFirestore.collection("BDM").document("bdm");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot documentSnapshot = task.getResult();
                assert documentSnapshot != null;

                if(task.isSuccessful()){
                    ArrayList<String> list = (ArrayList<String>) documentSnapshot.get("list");
                    assert list != null;

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<String>(getApplicationContext(),
                                    android.R.layout.simple_spinner_dropdown_item, list);
                    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                    BDMSpinner.setAdapter(adapter);

                }
            }
        });


    }
}