package com.example.tiswamcrm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

   private FirebaseAuth firebaseAuth;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_home_page);

      firebaseAuth = FirebaseAuth.getInstance();

      Button SignOut = findViewById(R.id.sign_out);
      SignOut.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("login", SharedPrefConsts.NO_LOGIN);
            editor.apply();

            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();


         }
      });


   }


}