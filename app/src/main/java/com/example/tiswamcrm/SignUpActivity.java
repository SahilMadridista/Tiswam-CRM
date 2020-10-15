package com.example.tiswamcrm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class SignUpActivity extends AppCompatActivity {

    EditText Name,Phone,Email,Password;
    RelativeLayout Parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Name = findViewById(R.id.name_et);
        Phone = findViewById(R.id.phone_et);
        Email = findViewById(R.id.email_et);
        Password = findViewById(R.id.password_et);
        Parent = findViewById(R.id.parent);

        Button Next = findViewById(R.id.next_button);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetails();
            }
        });

    }

    private void checkDetails() {

        if(Name.getText().toString().isEmpty()){
            String snack = "Name can't be empty.";
            showSnack(snack);
            return;
        }

        if(Phone.getText().toString().isEmpty()){
            String snack = "Phone number can't be empty.";
            showSnack(snack);
            return;
        }

        if(Phone.getText().toString().length() != 10){
            String snack = "Please enter a 10 digit phone number.";
            showSnack(snack);
            return;
        }

        if(Email.getText().toString().isEmpty()){
            String snack = "Email address can't be empty.";
            showSnack(snack);
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString().trim()).matches()){
            String snack = "Please enter a valid email address";
            showSnack(snack);
            return;
        }

        if(Password.getText().toString().isEmpty()){
            String snack = "Password can't be empty.";
            showSnack(snack);
            return;
        }

        if(Password.getText().toString().length() < 6){
            String snack = "Password must have at least 6 characters.";
            showSnack(snack);
            return;
        }

        Intent intent = new Intent(getApplicationContext(),ProfilePhotoActivity.class);
        intent.putExtra("name",Name.getText().toString().trim());
        intent.putExtra("phone",Phone.getText().toString().trim());
        intent.putExtra("email",Email.getText().toString().trim());
        intent.putExtra("password",Password.getText().toString().trim());
        startActivity(intent);



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

                        SignUpActivity.super.onBackPressed();
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

        SignUpActivity.super.onBackPressed();
        finish();


    }

}