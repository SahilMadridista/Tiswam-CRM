package com.example.tiswamcrm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ProfilePhotoActivity extends AppCompatActivity {

   private FirebaseAuth firebaseAuth;
   private FirebaseFirestore firebaseFirestore;
   private Uri imageURIOne;
   private static final int PICK_IMAGE_REQUEST_ONE = 1001;
   public static final int STORAGE_REQUEST_CODE = 400;
   public static final int IMAGE_PICK_GALLERY_CODE = 1000;
   ImageView ProfileImage;
   String Name, Phone, Email, Password;
   ProgressDialog progressDialog;
   private FirebaseStorage firebaseStorage;
   private StorageReference reference;
   String URL;
   ConstraintLayout parent;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_profile_photo);

      firebaseAuth = FirebaseAuth.getInstance();
      firebaseFirestore = FirebaseFirestore.getInstance();
      reference = FirebaseStorage.getInstance().getReference("Photos/");
      firebaseStorage = FirebaseStorage.getInstance();
      firebaseFirestore = FirebaseFirestore.getInstance();

      Intent i = getIntent();

      Name = i.getStringExtra("name");
      Phone = i.getStringExtra("phone");
      Email = i.getStringExtra("email");
      Password = i.getStringExtra("password");
      ProfileImage = findViewById(R.id.circleImageView);

      parent = findViewById(R.id.par);


      progressDialog = new ProgressDialog(this);
      progressDialog.setCancelable(false);

      Button ChooseImage = findViewById(R.id.choose_image_btn);
      ChooseImage.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            openFileChooserOne();
         }
      });

      Button CreateAccount = findViewById(R.id.create_account_btn);
      CreateAccount.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            if(ProfileImage.getDrawable() == null){
               String snack = "Please select an image.";
               showSnack(snack);
               return;
            }

            progressDialog.setMessage("Creating account...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {

                  if(task.isSuccessful()){
                     Objects.requireNonNull(firebaseAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                              storeData();
                           }
                           else {
                              Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                              progressDialog.dismiss();
                           }

                        }
                     });
                  }
                  else {
                     Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                     progressDialog.dismiss();
                  }


               }
            });

         }
      });

   }

   private void openFileChooserOne() {
      Intent intent = new Intent();
      intent.setType("image/*");
      intent.setAction(Intent.ACTION_GET_CONTENT);
      startActivityForResult(intent,PICK_IMAGE_REQUEST_ONE);
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == PICK_IMAGE_REQUEST_ONE && resultCode == RESULT_OK
              && data != null && data.getData() != null) {
         imageURIOne = data.getData();
         Picasso.get().load(imageURIOne).fit().centerCrop().into(ProfileImage);

         try {
            upLoadImageOne(imageURIOne);
         } catch (IOException e) {
            e.printStackTrace();
         }

      }

   }

   private void upLoadImageOne(Uri imageURIOne) throws IOException {

      progressDialog.setMessage("Uploading image...");
      progressDialog.show();

      if(imageURIOne !=null){

         progressDialog.show();


         StorageReference image_ref = reference.child("Admin").child(Email).child("profile_photo");

         Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageURIOne);

         ByteArrayOutputStream baos = new ByteArrayOutputStream();

         bmp.compress(Bitmap.CompressFormat.JPEG,25,baos);

         byte[] data = baos.toByteArray();

         image_ref.putBytes(data)
                 .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                       StorageReference storageReference = firebaseStorage.getReference();
                       storageReference.child("Photos/").child("Admin")
                               .child(Email).child("profile_photo")
                               .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                          @Override
                          public void onSuccess(Uri uri) {
                             URL = uri.toString();
                             progressDialog.dismiss();

                          }
                       });

                    }
                 }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

               progressDialog.dismiss();
               Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

            }
         });

      }

      else {
         progressDialog.dismiss();
         Toast.makeText(getApplicationContext(),"Please choose an image", Toast.LENGTH_LONG).show();
      }


   }

   private void storeData() {

      Admin admin = new Admin();

      admin.name = Name;
      admin.phone = Phone;
      admin.email = Email;
      admin.password = Password;
      admin.url = URL;

      firebaseFirestore.collection("Admin").document(Email).set(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
         @Override
         public void onSuccess(Void aVoid) {

            Toast.makeText(getApplicationContext(),"Account created and verification email sent. You can login only after verifying your email",
                    Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();

         }
      }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {

            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

         }
      });

   }

   private void showSnack(String snack) {

      Snackbar snackbar = Snackbar.make(parent,snack,Snackbar.LENGTH_LONG);
      snackbar.show();

   }


}