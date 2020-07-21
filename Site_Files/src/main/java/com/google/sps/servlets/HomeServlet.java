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


/** Servlet connect to the data store, store the comments data*/
@WebServlet("/inf")
public class HomeServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Retrieve comments based on latest timestamp.
    Query query = new Query("PostInformation").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    // Create comment object from the entity data.
    List<PostInformation> list = new ArrayList<PostInformation>();
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String email = (String) entity.getProperty("email");
      String type =(String) entity.getProperty("type");
      String name = (String) entity.getProperty("name");
      String comment = (String) entity.getProperty("comment");
      long timestamp = (long) entity.getProperty("timestamp");
      int rating = (int)(long) entity.getProperty("rating");
      PostInformation c = new PostInformation(id, email, type, name, comment, timestamp, rating);
      list.add(c);
    }
    // Convert ArrayList to Json.
    response.setContentType("application/json");
    String json = new Gson().toJson(list);
    response.getWriter().println(json); 
}

@Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String emailInput = getParameter(request, "email-input", "");
    String text = getParameter(request, "name-input", "");   
    String textType = getParameter(request, "type-input", "");   
    String text1 = getParameter(request, "comment-input", "");   
    int rating = 0; 
    long timestamp = System.currentTimeMillis();

    // Store data into entity.
    Entity commentEntity = new Entity("PostInformation");
    commentEntity.setProperty("email", emailInput);
    commentEntity.setProperty("type", textType);
    commentEntity.setProperty("name", text);
    commentEntity.setProperty("comment", text1);
    commentEntity.setProperty("timestamp", timestamp);
    commentEntity.setProperty("rating", rating);

    // Create Datastore and store comment data.
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);

    // Respond with the result.
    response.sendRedirect("/import.html");
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}