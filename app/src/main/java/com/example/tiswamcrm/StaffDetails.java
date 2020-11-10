package com.example.tiswamcrm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class StaffDetails extends AppCompatActivity {

   ImageView StaffImage;
   TextView Name, Team, Email, Phone;
   private static final int REQUEST_PHONE_CALL = 1;
   RelativeLayout CallLayout, EmailLayout;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
              WindowManager.LayoutParams.FLAG_FULLSCREEN);
      setContentView(R.layout.activity_staff_details);

      Intent i = getIntent();
      String name = i.getStringExtra("name");
      final String phone = i.getStringExtra("phone");
      final String email = i.getStringExtra("email");
      String team = i.getStringExtra("team");
      String url = i.getStringExtra("url");


      StaffImage = findViewById(R.id.staff_details_image);

      Name = findViewById(R.id.staff_name);
      Email = findViewById(R.id.staff_email_text);
      Team = findViewById(R.id.staff_team);
      Phone = findViewById(R.id.staff_phone_text);

      CallLayout = findViewById(R.id.call_layout);
      EmailLayout = findViewById(R.id.email_layout);

      Name.setText(name);
      Phone.setText(phone);
      Email.setText(email);
      Team.setText(team);

      Picasso.get().load(url).into(StaffImage);

      CallLayout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            if (ContextCompat.checkSelfPermission(StaffDetails.this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
               ActivityCompat.requestPermissions(StaffDetails
                       .this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
            }
            else
            {
               startActivity(intent);
            }

         }
      });

      EmailLayout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto",email, null));
            startActivity(Intent.createChooser(emailIntent, "Send email..."));

         }
      });

   }

}