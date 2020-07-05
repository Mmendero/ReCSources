package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;


public class Information {
    private long id;
    private String email;
    private ArrayList<String> type;
    //private String type;
    private String name;
    private String comment;
    private long timestamp;
  
  public Information()
  {

  }

   public Information(long id, String email, ArrayList<String> type, String name, String comment, long timestamp)
  {
   this.id=id;
   this.email=email;
   this.type=type;
   this.name=name;
   this.comment=comment;
   this.timestamp=timestamp;
  }

   public void  setName(String na)
   {
       name=na;
   }

   public void  setEmail(String em)
   {
       email=em;
   }
   public void  setType(ArrayList ty)
   {
       type=ty;
   }

   public void  setComment(String co)
   {
       comment=co;
   }

   public String getName()
   {
       return name;
   }

   public String getEmail()
   {
       return email;
   }
   public ArrayList<String> getType()
   {
       return type;
   }

   public String getComment()
   {
       return comment;
   }

   public void setId(long id){
       this.id=id;
   }

   public long getId(){
       return id;
   }

     public void setTimestamp(long timestamp){
       this.timestamp=timestamp;
   }

   public long getTimestamp(){
       return timestamp;
   }

}