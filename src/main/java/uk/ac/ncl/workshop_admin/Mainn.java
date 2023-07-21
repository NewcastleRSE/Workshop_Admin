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

public class Mainn {

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
          .withHeader("user profile", "username", "Programme", "Stage"));

      for (CSVRecord record : csvParser) {
        // Get data from the CSV record
        String firstName = record.get("user profile");
        String lastName = record.get("username");
        String programmeName = record.get("Programme");
        String stageName = record.get("Stage");

        // Create and save the objects to the database
        Person person = new Person(firstName, lastName);
        Programme programme = new Programme(programmeName);
        Stage stage = new Stage(stageName);

        databaseManager.open();
        databaseManager.create(person);
        databaseManager.create(programme);
        databaseManager.create(stage);
        databaseManager.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    }

}