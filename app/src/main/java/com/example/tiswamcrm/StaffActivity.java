package com.example.tiswamcrm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class StaffActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

   FirebaseFirestore firebaseFirestore;
   androidx.appcompat.widget.Toolbar toolbar;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_staff);

      firebaseFirestore = FirebaseFirestore.getInstance();

      toolbar = findViewById(R.id.top_text);
      setSupportActionBar(toolbar);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      Spinner TeamSpinner = findViewById(R.id.team_spinner);
      ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
              R.array.team_name, R.layout.spinner_item);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      TeamSpinner.setAdapter(adapter);
      TeamSpinner.setOnItemSelectedListener(this);


   }

   @Override
   public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

      String Choice = adapterView.getItemAtPosition(i).toString().trim();
      CollectionReference collectionReference = firebaseFirestore.collection("Employees");

      if(Choice.equals("All")){

         Query query = collectionReference.orderBy("name", Query.Direction.ASCENDING);
         FirestoreRecyclerOptions<Staff> options = new FirestoreRecyclerOptions.Builder<Staff>()
                 .setQuery(query, Staff.class)
                 .build();
         StaffAdapter staffAdapter = new StaffAdapter(options);
         RecyclerView recyclerView = findViewById(R.id.staff_recyclerview);
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerView.setAdapter(staffAdapter);
         staffAdapter.startListening();

      }

      else{

         Query query = collectionReference.whereEqualTo("team",Choice);
         FirestoreRecyclerOptions<Staff> options = new FirestoreRecyclerOptions.Builder<Staff>()
                 .setQuery(query, Staff.class)
                 .build();
         StaffAdapter staffAdapter = new StaffAdapter(options);
         RecyclerView recyclerView = findViewById(R.id.staff_recyclerview);
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerView.setAdapter(staffAdapter);
         staffAdapter.startListening();

      }

   }

   @Override
   public void onNothingSelected(AdapterView<?> adapterView) {

   }
   
}