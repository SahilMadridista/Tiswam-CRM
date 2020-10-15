package com.example.tiswamcrm;

import java.util.ArrayList;

public class Admin {

   String name;
   String phone;
   String email;
   String password;
   String url;
   ArrayList<String> bdeTeam;
   ArrayList<String> bdmTeam;
   ArrayList<String> itTeam;

   public Admin(){

   }

   public Admin(String name, String phone, String email, String password,
                String url, ArrayList<String> bdeTeam, ArrayList<String> bdmTeam,
                ArrayList<String> itTeam) {
      this.name = name;
      this.phone = phone;
      this.email = email;
      this.password = password;
      this.url = url;
      this.bdeTeam = bdeTeam;
      this.bdmTeam = bdmTeam;
      this.itTeam = itTeam;
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

   public String getPassword() {
      return password;
   }

   public String getUrl() {
      return url;
   }

   public ArrayList<String> getBdeTeam() {
      return bdeTeam;
   }

   public ArrayList<String> getBdmTeam() {
      return bdmTeam;
   }

   public ArrayList<String> getItTeam() {
      return itTeam;
   }
}
