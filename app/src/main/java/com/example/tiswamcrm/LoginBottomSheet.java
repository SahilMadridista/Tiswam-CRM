package com.example.tiswamcrm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LoginBottomSheet extends BottomSheetDialogFragment {

   Context context;

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {

      View v = inflater.inflate(R.layout.login_bottom_sheet, container, false);
      context = v.getContext();

      Button button = v.findViewById(R.id.admin_login_btn);
      button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Toast.makeText(context,"Admin login clicked",Toast.LENGTH_SHORT).show();
         }
      });

      Button button2 = v.findViewById(R.id.employee_login_button);
      button2.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Toast.makeText(context,"Employee login clicked",Toast.LENGTH_SHORT).show();
         }
      });



      return v;
   }

}
