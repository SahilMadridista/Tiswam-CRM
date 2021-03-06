package com.example.tiswamcrm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class DoneDealsAdapter extends FirestoreRecyclerAdapter<Lead,DoneDealsAdapter.DoneDealsViewHolder> {

   Context context;

   public DoneDealsAdapter(@NonNull FirestoreRecyclerOptions<Lead> options) {
      super(options);
   }

   @Override
   protected void onBindViewHolder(@NonNull final DoneDealsViewHolder holder, int position, @NonNull Lead model) {

      holder.LeadOrg.setText(model.getBusiness_name());
      holder.LeadName.setText(model.getName());
      holder.Date.setText(model.getMeeting_date());

      holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());

            String id = snapshot.getId();
            String org = snapshot.getString("business_name");
            String address = snapshot.getString("business_address");
            String name = snapshot.getString("name");
            String phone = snapshot.getString("phone");
            String email = snapshot.getString("email");
            String date = snapshot.getString("meeting_date");
            String time = snapshot.getString("meeting_time");
            String bde_name = snapshot.getString("bde_name");
            String bde_email = snapshot.getString("bde_email");
            String bde_phone = snapshot.getString("bde_phone");
            String bdm = snapshot.getString("bdm");
            String mode = snapshot.getString("payment_mode");
            String total = snapshot.getString("deal_amount");
            String advance = snapshot.getString("advance_amount");
            String remaining = snapshot.getString("remaining_amount");

            Intent i = new Intent(context,DoneDealsDecription.class);

            i.putExtra("id",id);
            i.putExtra("org",org);
            i.putExtra("address",address);
            i.putExtra("name",name);
            i.putExtra("phone",phone);
            i.putExtra("email",email);
            i.putExtra("date",date);
            i.putExtra("time",time);
            i.putExtra("bde_name",bde_name);
            i.putExtra("bde_phone",bde_phone);
            i.putExtra("bde_email",bde_email);
            i.putExtra("bdm",bdm);
            i.putExtra("mode",mode);
            i.putExtra("total",total);
            i.putExtra("advance",advance);
            i.putExtra("remaining",remaining);

            context.startActivity(i);


         }
      });

   }

   @NonNull
   @Override
   public DoneDealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_lead_card,
              parent, false);
      context = v.getContext();
      return new DoneDealsViewHolder(v);
   }

   static class DoneDealsViewHolder extends RecyclerView.ViewHolder{

      TextView LeadOrg,LeadName,Date;
      ImageView DeleteLead;

      public DoneDealsViewHolder(@NonNull View itemView) {
         super(itemView);

         LeadOrg = itemView.findViewById(R.id.lead_org);
         LeadName = itemView.findViewById(R.id.lead_name);
         Date = itemView.findViewById(R.id.date_text);

         DeleteLead = itemView.findViewById(R.id.delete_icon);

      }

   }

}
