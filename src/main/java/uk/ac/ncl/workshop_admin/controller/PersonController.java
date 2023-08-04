package uk.ac.ncl.workshop_admin.controller;

import spark.Request;
import spark.Response;
import uk.ac.ncl.workshop_admin.model.Person;
import uk.ac.ncl.workshop_admin.util.DatabaseManager;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

public class PersonController {

  private final DatabaseManager databaseManager;

  public PersonController(DatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  public List<Person> getAllPeople() {
    return databaseManager.read(Person.class);
  }

  public String getPerson(Request request, Response response) {
    try {
      int personId = Integer.parseInt(request.queryParams("personID"));
      List<Person> people = databaseManager.read(Person.class, "id = :id", Collections.singletonMap("id", personId));
      if (!people.isEmpty()) {
        response.status(200);
        response.type("application/json");
        return new Gson().toJson(people.get(0));
      } else {
        response.status(404);
        return "Person not found";
      }
    } catch (NumberFormatException e) {
      response.status(400);
      return "Invalid person ID";
    }
  }

  public String addPerson(Request request, Response response) {
    try {
      String firstName = request.queryParams("firstname");
      String lastName = request.queryParams("lastname");

      // Create a new Person object and set its properties
      Person newPerson = new Person(firstName, lastName, null);

      // Persist the new person in the database
      databaseManager.open();
      databaseManager.create(newPerson);
      databaseManager.close();

      response.status(200);
      return "Person added successfully: " + newPerson.toString();
    } catch (Exception e) {
      response.status(500);
      return "Failed to add person";
    }
  }
}








