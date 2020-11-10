package com.example.tiswamcrm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DoneDealsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

   androidx.appcompat.widget.Toolbar toolbar;
   String date="";
   Button DateButton;
   FirebaseFirestore firebaseFirestore;
   TextView Clear;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_done_deals);

      firebaseFirestore = FirebaseFirestore.getInstance();

      toolbar = findViewById(R.id.done_deals_toolbar);
      setSupportActionBar(toolbar);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      Clear = findViewById(R.id.clear);

      DateButton = findViewById(R.id.select_date_button);
      DateButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");

         }
      });

      // showAllDeals();

      Clear.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            // showAllDeals();
            Clear.setVisibility(View.GONE);
            DateButton.setText(R.string.selectDate);

         }
      });

   }

   private void showAllDeals(){
      CollectionReference collectionReference = firebaseFirestore.collection("Leads");
      Query query = collectionReference.whereEqualTo("deal_status","yes");
      FirestoreRecyclerOptions<Lead> options = new FirestoreRecyclerOptions.Builder<Lead>()
              .setQuery(query, Lead.class)
              .build();

      DoneDealsAdapter doneDealsAdapter = new DoneDealsAdapter(options);
      RecyclerView recyclerView = findViewById(R.id.done_deals_recyclerview);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(doneDealsAdapter);
      doneDealsAdapter.startListening();
   }

   @Override
   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
      Calendar c = Calendar.getInstance();
      c.set(Calendar.YEAR, year);
      c.set(Calendar.MONTH, month);
      c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
      date = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
      DateButton.setText(date);
      showDeals(date);
      Clear.setVisibility(View.VISIBLE);

   }

   private void showDeals(String date) {

      CollectionReference collectionReference = firebaseFirestore.collection("Leads");
      Query query = collectionReference.whereEqualTo("meeting_date",date)
              .whereEqualTo("deal_status","yes");
      FirestoreRecyclerOptions<Lead> options = new FirestoreRecyclerOptions.Builder<Lead>()
              .setQuery(query, Lead.class)
              .build();

      DoneDealsAdapter doneDealsAdapter = new DoneDealsAdapter(options);
      RecyclerView recyclerView = findViewById(R.id.done_deals_recyclerview);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(doneDealsAdapter);
      doneDealsAdapter.startListening();





   }
}