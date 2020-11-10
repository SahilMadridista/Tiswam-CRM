package com.example.tiswamcrm;

public class Staff {

   String name;
   String phone;
   String email;
   String team;
   String url;

   public Staff(){

   }

   public Staff(String name, String phone, String email, String team, String url) {
      this.name = name;
      this.phone = phone;
      this.email = email;
      this.team = team;
      this.url = url;
   }

   public String getName() {
      return name;
   }

   public String getPhone() {
      return phone;
   }

   public String getEmail() {
      return email;
   }

   public String getTeam() {
      return team;
   }

   public String getUrl() {
      return url;
   }
}
