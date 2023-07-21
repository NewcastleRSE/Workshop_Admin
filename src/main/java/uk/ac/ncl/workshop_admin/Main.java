package uk.ac.ncl.workshop_admin;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import uk.ac.ncl.workshop_admin.model.Person;
import uk.ac.ncl.workshop_admin.model.Programme;
import uk.ac.ncl.workshop_admin.model.School;
import uk.ac.ncl.workshop_admin.model.Stage;
import uk.ac.ncl.workshop_admin.util.DatabaseManager;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    DatabaseManager databaseManager = new DatabaseManager();

    initDataset(databaseManager);

    databaseManager.open();
    printAllPeople(databaseManager.read(Person.class));
    printAllSchools(databaseManager.read(School.class));
    printAllProgrammes(databaseManager.read(Programme.class));
    printAllStages(databaseManager.read(Stage.class));
    databaseManager.close();
  }

  public static void printAllPeople(List<Person> objects) {
    System.out.println("People:");
    for (Person obj : objects)
      System.out.println("    " + obj.toFullString());
  }

  public static void printAllSchools(List<School> objects) {
    System.out.println("Schools:");
    for (School obj : objects)
      System.out.println("    " + obj.toFullString());
  }

  public static void printAllProgrammes(List<Programme> objects) {
    System.out.println("Programmes:");
    for (Programme obj : objects)
      System.out.println("    " + obj.toFullString());
  }

  public static void printAllStages(List<Stage> objects) {
    System.out.println("Stages:");
    for (Stage obj : objects)
      System.out.println("    " + obj.toFullString());
  }

  private static void initDataset(DatabaseManager databaseManager) {
    HashMap<String, Object> parameters;

    // Load data from the CSV file
    try (Reader reader = new FileReader("uniFormat.csv")) {
      CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()
          .withHeader("ID","user profile","username","uuid","date created","date modified","source","occurrence","contact number","attended","additional notes","attended datetime","eval email sent","eval submitted","User type","Programme","Stage","School","Attended without booking?","attendance method","synced to portfolio","email"));

      for (CSVRecord record : csvParser) {
        // Get data from the CSV record
        String userProfile = record.get("user profile");
        String programmeName = record.get("Programme");
        String stageName = record.get("Stage");

        // Remove the double quotes from the user profile string
        userProfile = userProfile.replaceAll("\"", "");

        // Split the user profile string into first name and last name
        String[] nameParts = userProfile.split(",\\s*");
        String firstName = nameParts[1]; // Index 1 contains the first name
        String lastName = nameParts[0]; // Index 0 contains the last name

        databaseManager.open();
        // Check if the person already exists in the database
        parameters = new HashMap<>();
        parameters.put("firstName", firstName);
        parameters.put("lastName", lastName);
        List<Person> existingPeople = databaseManager.read(Person.class, "firstName = :firstName AND lastName = :lastName", parameters);

        Person person;
        if (existingPeople.isEmpty()) {
          // Create and save the person to the database if it doesn't exist
          person = new Person(firstName, lastName);
          databaseManager.create(person);
        } else {
          // Use the existing person if it already exists
          person = existingPeople.get(0);
        }
        databaseManager.update(person);

        // Check if the programme already exists in the database
        parameters = new HashMap<>();
        parameters.put("programme", programmeName);
        List<Programme> existingProgrammes = databaseManager.read(Programme.class, "programme = :programme", parameters);

        Programme programme;
        if (existingProgrammes.isEmpty()) {
          // Create and save the programme to the database if it doesn't exist
          programme = new Programme(programmeName);
          databaseManager.create(programme);
        } else {
          // Use the existing programme if it already exists
          programme = existingProgrammes.get(0);
        }
        databaseManager.update(programme);

        // Check if the stage already exists in the database
        parameters = new HashMap<>();
        parameters.put("stage", stageName);
        List<Stage> existingStages = databaseManager.read(Stage.class, "stage = :stage", parameters);

        Stage stage;
        if (existingStages.isEmpty()) {
          // Create and save the stage to the database if it doesn't exist
          stage = new Stage(stageName);
          databaseManager.create(stage);
        } else {
          // Use the existing stage if it already exists
          stage = existingStages.get(0);
        }
        databaseManager.update(stage);

        databaseManager.close();

        databaseManager.open();
        // Associate the person with the programme and stage
        if (!person.getProgrammes().contains(programme)) {
          person.addProgramme(programme);
        }

        if (!person.getStages().contains(stage)) {
          person.addStage(stage);
        }
        databaseManager.update(person);

        databaseManager.close();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    }

}