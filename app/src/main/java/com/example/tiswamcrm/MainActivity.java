package com.example.tiswamcrm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      /*final TextView LoadingText = findViewById(R.id.loading_text);

      new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
            LoadingText.setText(R.string.loading_);
         }
      },500);

      new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
            LoadingText.setText(R.string.loading__);
         }
      },1000);

      new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
            LoadingText.setText(R.string.loading____);
         }
      },1500);*/

      TextView CreateAccountText = findViewById(R.id.create_acc_text);
      CreateAccountText.setPaintFlags(CreateAccountText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
      CreateAccountText.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
         }
      });

      Button button = findViewById(R.id.button);
      button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            LoginBottomSheet bottomSheetDialog = new LoginBottomSheet();
            bottomSheetDialog.show(getSupportFragmentManager(), "exampleBottomSheet");
         }
      });

   }

}