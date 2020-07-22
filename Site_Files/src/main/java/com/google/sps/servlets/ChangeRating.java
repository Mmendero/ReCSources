package com.google.sps.servlets;

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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;


/** Servlet connect to the data store, store the comments data*/
@WebServlet("/change-rating")
public class ChangeRating extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the server.
    if(request.getParameterMap().get("id").length > 1){
        response.sendError(418, "Length of id greater than 1");
    }

    long id = Long.parseLong(request.getParameterMap().get("id")[0]);
    int rating =  (int) Long.parseLong(request.getParameterMap().get("rating")[0]);

    if(rating != 0){
        // Create Datastore and update new rating.
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        //Query query = new Query("PostInformation").addFilter("email", Query.FilterOperator.EQUAL, "email");
        //PreparedQuery pq = datastore.prepare(query);
        
        Key personKey = KeyFactory.createKey("PostInformation", id);
        try{
            Entity postEntity = datastore.get(personKey);
            postEntity.setProperty("rating", rating);
            datastore.put(postEntity);
        }catch(EntityNotFoundException e) {
            response.sendError(404, "No matching id in DataStore " + id);
        }
    }
  }
}