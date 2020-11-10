package com.example.tiswamcrm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class StaffAdapter extends FirestoreRecyclerAdapter<Staff,StaffAdapter.StaffViewHolder> {

   Context context;

   public StaffAdapter(@NonNull FirestoreRecyclerOptions<Staff> options) {
      super(options);
   }

   @Override
   protected void onBindViewHolder(@NonNull final StaffViewHolder holder, int position, @NonNull final Staff model) {

      holder.StaffName.setText(model.getName());
      holder.StaffTeam.setText(model.getTeam());
      if(model.getUrl()!=null){

         Picasso.get().load(Uri.parse(model.getUrl()))
                 .fetch(new Callback() {
                    @Override
                    public void onSuccess() {

                       Picasso.get()
                               .load(Uri.parse(model.getUrl()))
                               .fit()
                               .centerCrop().into(holder.StaffImage);

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                 });
      }

      holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());

            String id = snapshot.getId();
            String name = snapshot.getString("name");
            String email = snapshot.getString("email");
            String phone = snapshot.getString("phone");
            String team = snapshot.getString("team");
            String url = snapshot.getString("url");

            Intent i = new Intent(context,StaffDetails.class);

            i.putExtra("name",name);
            i.putExtra("email",email);
            i.putExtra("phone",phone);
            i.putExtra("team",team);
            i.putExtra("id",id);
            i.putExtra("url",url);

            context.startActivity(i);


         }
      });

   }

   @NonNull
   @Override
   public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_item,
              parent, false);
      context = v.getContext();
      return new StaffViewHolder(v);
   }

   static class StaffViewHolder extends RecyclerView.ViewHolder{

      ImageView StaffImage;
      TextView StaffName, StaffTeam;

      public StaffViewHolder(@NonNull View itemView) {
         super(itemView);

         StaffImage = itemView.findViewById(R.id.staff_image);
         StaffName = itemView.findViewById(R.id.staff_member_name);
         StaffTeam = itemView.findViewById(R.id.staff_member_team);

      }

   }


}
