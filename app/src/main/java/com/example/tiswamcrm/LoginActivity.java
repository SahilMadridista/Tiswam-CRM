package com.example.tiswamcrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText EmailET,PasswordET;
    private FirebaseAuth firebaseAuth;
    Button LoginButton;
    RelativeLayout Parent;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        EmailET = findViewById(R.id.login_email);
        PasswordET = findViewById(R.id.login_password);
        LoginButton = findViewById(R.id.login_button);
        Parent = findViewById(R.id.root_layout);
        progressBar = findViewById(R.id.progress_bar);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetails();
            }
        });



    }

    private void checkDetails() {

        if(EmailET.getText().toString().isEmpty()){
            String snack = "Please enter email address.";
            showSnack(snack);
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(EmailET.getText().toString().trim()).matches()){
            String snack = "Please enter a valid email address";
            showSnack(snack);
            return;
        }

        if(PasswordET.getText().toString().isEmpty()){
            String snack = "Please enter password.";
            showSnack(snack);
            return;
        }

        loginTheUser();




    }



    private void loginTheUser() {

        disableWidgets();

        firebaseAuth.signInWithEmailAndPassword(EmailET.getText().toString().trim(),PasswordET.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            if(Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified()){

                                Toast.makeText(LoginActivity.this,"You are logged in",
                                        Toast.LENGTH_LONG).show();

                                enaableWidgets();

                                SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("login", SharedPrefConsts.USER_LOGIN);
                                editor.apply();

                                startActivity(new Intent(getApplicationContext(),HomePage.class));
                                finish();

                            }

                            else{

                                Toast.makeText(LoginActivity.this,"Please verify your email first. You can login after verification.",
                                        Toast.LENGTH_LONG).show();

                                enaableWidgets();

                            }



                        }else {

                            Toast.makeText(LoginActivity.this,task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();

                            enaableWidgets();

                        }

                    }
                });


    }

    private void disableWidgets() {

        progressBar.setVisibility(View.VISIBLE);
        LoginButton.setText(R.string.logging);
        EmailET.setEnabled(false);
        PasswordET.setEnabled(false);
        LoginButton.setEnabled(false);

    }

    private void enaableWidgets() {

        progressBar.setVisibility(View.GONE);
        LoginButton.setText(R.string.log);
        EmailET.setEnabled(true);
        PasswordET.setEnabled(true);
        LoginButton.setEnabled(true);

    }




    private void showSnack(String snack) {
        Snackbar snackbar = Snackbar.make(Parent,snack,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onBackPressed() {

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you really want to exit ?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        LoginActivity.super.onBackPressed();
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
        alertDialog.show();*/

        LoginActivity.super.onBackPressed();
        finish();


    }

}