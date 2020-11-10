package com.example.tiswamcrm;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class AssignedMeetingsDescription extends AppCompatActivity {

   TextView BusinessName, BusinessAddress, LeadName, LeadEmail, LeadPhone, MeetingDate;
   TextView MeetingTime, AssignedBDM, BDEName, BDEEmail, BDEPhone;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_assigned_meetings_description);

      BusinessName = findViewById(R.id.lead_org_text);
      BusinessAddress = findViewById(R.id.lead_org_address);
      LeadName = findViewById(R.id.lead_name_text);
      LeadEmail = findViewById(R.id.lead_email_text);
      LeadPhone = findViewById(R.id.lead_phone_text);
      MeetingDate = findViewById(R.id.d_date_text);
      MeetingTime = findViewById(R.id.d_time_text);
      AssignedBDM = findViewById(R.id.bdm_name);
      BDEName = findViewById(R.id.bde_name_text);
      BDEEmail = findViewById(R.id.bde_email_text);
      BDEPhone = findViewById(R.id.bde_phone_text);


   }
}