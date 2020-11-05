package com.example.tiswamcrm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NotAssignedBDMAdapter extends FirestoreRecyclerAdapter<Lead,NotAssignedBDMAdapter.NotAssignedDBMViewHolder> {

   Context context;

   public NotAssignedBDMAdapter(@NonNull FirestoreRecyclerOptions<Lead> options) {
      super(options);
   }

   @Override
   protected void onBindViewHolder(@NonNull final NotAssignedDBMViewHolder holder, int position, @NonNull Lead model) {

      holder.LeadOrg.setText(model.getBusiness_name());
      holder.LeadName.setText(model.getName());
      holder.Date.setText(model.getMeeting_date());

      holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            context.startActivity(new Intent(context,LeadDetailsWithOutBDMActivity.class));
         }
      });

      holder.DeleteLead.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());

            final String id = snapshot.getId();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setMessage("Do you really want to delete this lead ?").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(final DialogInterface dialogInterface, int i) {

                          firebaseFirestore.collection("Leads").document(id)
                                  .delete()
                                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void aVoid) {
                                        dialogInterface.cancel();
                                        Toast.makeText(context,"Lead deleted",Toast.LENGTH_SHORT).show();
                                     }
                                  }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                dialogInterface.cancel();
                             }
                          });

                       }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                  dialogInterface.cancel();
               }
            });

            AlertDialog alert = builder.create();
            alert.show();


         }
      });

   }

   @NonNull
   @Override
   public NotAssignedDBMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_lead_card,
              parent, false);
      context = v.getContext();
      return new NotAssignedDBMViewHolder(v);
   }

   static class NotAssignedDBMViewHolder extends RecyclerView.ViewHolder{

      TextView LeadOrg,LeadName,Date;
      ImageView DeleteLead;

      public NotAssignedDBMViewHolder(@NonNull View itemView) {
         super(itemView);

         LeadOrg = itemView.findViewById(R.id.lead_org);
         LeadName = itemView.findViewById(R.id.lead_name);
         Date = itemView.findViewById(R.id.date_text);

         DeleteLead = itemView.findViewById(R.id.delete_icon);

      }
   }

}
