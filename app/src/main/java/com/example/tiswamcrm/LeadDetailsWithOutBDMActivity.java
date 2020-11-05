package com.example.tiswamcrm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class LeadDetailsWithOutBDMActivity extends AppCompatActivity {

    TextView BusinessName, BusinessAddress, LeadName, LeadEmail, LeadPhone, MeetingDate;
    TextView MeetingTime, BDEName, BDEEmail, BDEPhone;
    Spinner BDMSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_details_with_out_b_d_m);

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.loading, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BDMSpinner.setAdapter(adapter);


    }
}