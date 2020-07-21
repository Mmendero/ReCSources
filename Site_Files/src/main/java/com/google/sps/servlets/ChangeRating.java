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
import java.io.PrintWriter;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;


/** Servlet connect to the data store, store the comments data*/
@WebServlet("/change-rating")
public class ChangeRating extends HttpServlet {

    @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.getWriter().println(request); 
    response.getWriter().println(request.getParameter("id")); 
    response.getWriter().println(request.getParameter("rating")); 
    
  }

@Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the server.
    long id =  Long.parseLong(getParameter(request, "id", ""));
    int rating = Integer.parseInt(getParameter(request, "rating", "")); 
    response.getWriter().println(request.getParameter("id")); 
    response.getWriter().println(request.getParameter("rating")); 

    // TODO: Fix issue with restoring in the Datastore.
    // Create Datastore and store comment data.
    /*
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    commentEntity = datastore.get(KeyFactory.createKey("PostInformation", id));
    commentEntity.setProperty("rating", rating);
    datastore.put(commentEntity);
    */
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}