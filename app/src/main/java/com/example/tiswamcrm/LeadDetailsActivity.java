package com.example.tiswamcrm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LeadDetailsActivity extends AppCompatActivity {

   TextView BusinessName, BusinessAddress, LeadName, LeadEmail, LeadPhone, MeetingDate;
   TextView MeetingTime, ServicesPurchased, AssignedBDM, BDEName, BDEEmail, BDEPhone, PaymentMode, TotalAmount, PaidAmount, RemainingAmount;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_lead_details);

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




   }
}