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
import com.google.sps.data.Information;
import java.util.ArrayList;
import java.util.List;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;


/** Servlet connect to the data store, store the commnets data*/
@WebServlet("/inf")
public class HomeServlet extends HttpServlet {

    //private List<CommentHistory> history = new ArrayList<>();

  
  //return the commnet history data
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Information").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Information> list = new ArrayList<Information>();
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String email = (String) entity.getProperty("email");
      ArrayList<String> type =(ArrayList<String>) entity.getProperty("type");
      String name = (String) entity.getProperty("name");
      String comment = (String) entity.getProperty("comment");
      long timestamp = (long) entity.getProperty("timestamp");
      Information c = new Information(id,email,type,name,comment,timestamp);
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

    String emailInput = getParameter(request, "email-input", "");
    String text = getParameter(request, "name-input", "");
    
    String textType = getParameter(request, "type-input", "");
   
    String text1 = getParameter(request, "comment-input", "");
    
    long timestamp = System.currentTimeMillis();

    Entity commentEntity = new Entity("CommentHistory");
    commentEntity.setProperty("email",emailInput);
    commentEntity.setProperty("type",textType);
    commentEntity.setProperty("name",text);
    commentEntity.setProperty("comment", text1);
    commentEntity.setProperty("timestamp", timestamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);
    response.sendRedirect("/inport.html");
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

}