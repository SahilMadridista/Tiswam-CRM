package com.example.tiswamcrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomePage extends AppCompatActivity {

   private FirebaseAuth firebaseAuth;
   private FirebaseFirestore firebaseFirestore;
   androidx.appcompat.widget.Toolbar toolbar;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_home_page);

      firebaseAuth = FirebaseAuth.getInstance();
      firebaseFirestore = FirebaseFirestore.getInstance();
      toolbar = findViewById(R.id.homepage_toolbar);
      setSupportActionBar(toolbar);

      showLeads();


   }

   private void showLeads() {

      CollectionReference collectionReference = firebaseFirestore.collection("Leads");
      Query query = collectionReference.whereEqualTo("bdm","no");
      FirestoreRecyclerOptions<Lead> options = new FirestoreRecyclerOptions.Builder<Lead>()
              .setQuery(query, Lead.class)
              .build();
      NotAssignedBDMAdapter notAssignedBDMAdapter = new NotAssignedBDMAdapter(options);

      RecyclerView recyclerView = findViewById(R.id.non_bdm_meetings_recyclerview);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(notAssignedBDMAdapter);
      notAssignedBDMAdapter.startListening();

   }



   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.admin_menu,menu);
      return super.onCreateOptionsMenu(menu);
   }


   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {

      switch (item.getItemId()) {

         case R.id.profile:

            // Add code here

            Toast.makeText(getApplicationContext(),"Profile button clicked",Toast.LENGTH_SHORT).show();
            break;

         case R.id.done_deals:

            // Add code here

            startActivity(new Intent(getApplicationContext(),DoneDealsActivity.class));
            break;


         case R.id.assignedMeetings:

            // Add Code here

            startActivity(new Intent(getApplicationContext(), AssignedMeetingsActivity.class));
            break;

         case R.id.staff:

            // Add Code here

            startActivity(new Intent(getApplicationContext(),StaffActivity.class));
            break;

         case R.id.sign_out:

            // Add code here

            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("login", SharedPrefConsts.NO_LOGIN);
            editor.apply();

            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

         default:
            return super.onOptionsItemSelected(item);

      }

      return true;
   }



}