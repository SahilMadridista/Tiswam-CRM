package com.example.tiswamcrm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class StaffActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_staff);

      Spinner TeamSpinner = findViewById(R.id.team_spinner);
      ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
              R.array.team_name, R.layout.spinner_item);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      TeamSpinner.setAdapter(adapter);
      TeamSpinner.setOnItemSelectedListener(this);


   }

   @Override
   public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

   }

   @Override
   public void onNothingSelected(AdapterView<?> adapterView) {

   }
}