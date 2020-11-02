package com.example.tiswamcrm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      final ImageView img = findViewById(R.id.imageView);
      TextView one = findViewById(R.id.textView);
      TextView two = findViewById(R.id.textView2);

      img.animate().alpha(1).setDuration(1000);
      one.animate().alpha(1).setDuration(1000);
      two.animate().alpha(1).setDuration(1000);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         getWindow().getSharedElementEnterTransition().setDuration(1000);
         getWindow().getSharedElementReturnTransition().setDuration(1000)
                 .setInterpolator(new DecelerateInterpolator());
      }

      final TextView CreateAccountText = findViewById(R.id.create_acc_text);
      CreateAccountText.setPaintFlags(CreateAccountText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
      CreateAccountText.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Fade fade = new Fade();
            View decor = getWindow().getDecorView();
            fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
            Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    MainActivity.this, img, Objects.requireNonNull(ViewCompat.getTransitionName(img)));
            startActivity(intent, options.toBundle());

         }
      });

      final Button button = findViewById(R.id.button);
      button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            Fade fade = new Fade();
            View decor = getWindow().getDecorView();
            fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    MainActivity.this, img, Objects.requireNonNull(ViewCompat.getTransitionName(img)));
            startActivity(intent, options.toBundle());

         }
      });

      SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
      final int loginStatus = preferences.getInt("login", SharedPrefConsts.NO_LOGIN);

      new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {

            if(loginStatus == SharedPrefConsts.USER_LOGIN){
               Intent i = new Intent(getApplicationContext(),HomePage.class);
               startActivity(i);
               finish();
            }else {
               button.animate().alpha(1).setDuration(1000);
               CreateAccountText.animate().alpha(1).setDuration(1000);
            }
         }
      }, 1000);


   }


   @Override
   public void onBackPressed() {

      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage("Do you really want to exit ?").setCancelable(false)
              .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {

                    MainActivity.super.onBackPressed();
                    finish();

                 }
              })

              .setNegativeButton("No", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                 }
              });

      AlertDialog alertDialog = builder.create();
      alertDialog.show();


   }



}