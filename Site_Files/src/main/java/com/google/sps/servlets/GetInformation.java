package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.sps.data.PostInformation;
import java.util.ArrayList;
import java.util.List;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;


/** Servlet connect to the data store, store the commnets data*/
@WebServlet("/getInf")
public class GetInformation extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String name_search= request.getParameter("name-search");
    String type_search= request.getParameter("type-search");  
    Query query = new Query("PostInformation").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<PostInformation> list = new ArrayList<PostInformation>();
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String email = (String) entity.getProperty("email");
      String type =(String) entity.getProperty("type");
      String name = (String) entity.getProperty("name");
      String comment = (String) entity.getProperty("comment");
      long timestamp = (long) entity.getProperty("timestamp");
      PostInformation c = new PostInformation(id,email,type,name,comment,timestamp);
      list.add(c);
    }
    response.setContentType("application/json");
    String json = new Gson().toJson(list);
    response.getWriter().println(json); 
    
}
private String convertToJson() {
    String json = "{";
    json += "\"acc\"";
    json += ", ";
    json += "\"acc\"";
    json += "}";
    return json;  
}


@Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
  
}



}