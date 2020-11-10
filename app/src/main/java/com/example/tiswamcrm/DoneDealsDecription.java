package com.example.tiswamcrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Iterator;

public class DoneDealsDecription extends AppCompatActivity {

   TextView BusinessName, BusinessAddress, LeadName, LeadEmail, LeadPhone, MeetingDate;
   TextView MeetingTime, ServicesPurchased, AssignedBDM, BDEName, BDEEmail, BDEPhone, PaymentMode, TotalAmount, PaidAmount, RemainingAmount;
   FirebaseFirestore firebaseFirestore;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_done_deals_decription);

      firebaseFirestore = FirebaseFirestore.getInstance();

      BusinessName = findViewById(R.id.lead_org_text);
      BusinessAddress = findViewById(R.id.lead_org_address);
      LeadName = findViewById(R.id.lead_name_text);
      LeadEmail = findViewById(R.id.lead_email_text);
      LeadPhone = findViewById(R.id.lead_phone_text);
      MeetingDate = findViewById(R.id.d_date_text);
      MeetingTime = findViewById(R.id.d_time_text);
      ServicesPurchased = findViewById(R.id.services_purchased_text);
      AssignedBDM = findViewById(R.id.bdm_name);
      BDEName = findViewById(R.id.bde_name_text);
      BDEEmail = findViewById(R.id.bde_email_text);
      BDEPhone = findViewById(R.id.bde_phone_text);
      PaymentMode = findViewById(R.id.payment_mode);
      TotalAmount = findViewById(R.id.total_amount);
      PaidAmount = findViewById(R.id.paid_amount);
      RemainingAmount = findViewById(R.id.remaining_amount);

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
      String bdm = i.getStringExtra("bdm");
      String mode = i.getStringExtra("mode");
      String total = i.getStringExtra("total");
      String advance = i.getStringExtra("advance");
      String remaining = i.getStringExtra("remaining");

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
      AssignedBDM.setText(bdm);
      PaymentMode.setText(mode);
      TotalAmount.setText(total);
      PaidAmount.setText(advance);
      RemainingAmount.setText(remaining);

      showServices(id);

   }

   private void showServices(String id) {

      DocumentReference documentReference =firebaseFirestore.collection("Leads").document(id);

      documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
         @Override
         public void onComplete(@NonNull Task<DocumentSnapshot> task) {

            if(task.isSuccessful()){
               DocumentSnapshot documentSnapshot = task.getResult();
               assert documentSnapshot != null;
               ArrayList<String> Sports = (ArrayList<String>) documentSnapshot.get("services_offered");

               assert Sports != null;

               StringBuilder sb = new StringBuilder();
               Iterator<String> iterator = Sports.iterator();

               if (iterator.hasNext()) {
                  sb.append(iterator.next());

                  while (iterator.hasNext()) {
                     sb.append(", ");
                     sb.append(iterator.next());
                  }
               }
               ServicesPurchased.setText(sb);
            }
         }
      });


   }
}