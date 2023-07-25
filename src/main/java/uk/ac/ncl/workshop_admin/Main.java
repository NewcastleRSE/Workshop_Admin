package uk.ac.ncl.workshop_admin;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import uk.ac.ncl.workshop_admin.model.*;
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
    printAllWorkshops(databaseManager.read(Workshop.class));
    printAllRegistrations(databaseManager.read(Registration.class));
    printAllInstructors(databaseManager.read(Instructor.class));
    printAllInstructorWorkshops(databaseManager.read(InstructorWorkshop.class));
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

  public static void printAllWorkshops(List<Workshop> objects) {
    System.out.println("Workshop:");
    for (Workshop obj : objects)
      System.out.println("    " + obj.toString());
  }

  public static void printAllRegistrations(List<Registration> objects) {
    System.out.println("Registrations:");
    for (Registration obj : objects)
      System.out.println("    " + obj.toString());
  }

  public static void printAllInstructors(List<Instructor> objects) {
    System.out.println("Instructor:");
    for (Instructor obj : objects)
      System.out.println("    " + obj.toString());
  }

  public static void printAllInstructorWorkshops(List<InstructorWorkshop> objects) {
    System.out.println("InstructorWorkshops:");
    for (InstructorWorkshop obj : objects)
      System.out.println("    " + obj.toString());
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
        String schoolName = record.get("School");
        String workshopName = record.get("occurrence");
        String login = record.get("username");
        String contactNumber = record.get("contact number");
        String email = record.get("email");


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
        parameters.put("login", login);
        List<Person> existingPeople = databaseManager.read(Person.class, "firstName = :firstName AND lastName = :lastName AND login = :login", parameters);

        Person person;
        if (existingPeople.isEmpty()) {
          // Create and save the person to the database if it doesn't exist
          person = new Person(firstName, lastName, login);
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

        // Check if the school already exists in the database
        parameters = new HashMap<>();
        parameters.put("school", schoolName);
        List<School> existingSchools = databaseManager.read(School.class, "school = :school", parameters);

        School school;
        if (existingSchools.isEmpty()) {
          // Create and save the school to the database if it doesn't exist
          school = new School(schoolName);
          databaseManager.create(school);
        } else {
          // Use the existing school if it already exists
          school = existingSchools.get(0);
        }
        databaseManager.update(school);

        // Check if the workshop already exists in the database
        parameters = new HashMap<>();
        parameters.put("workshop", workshopName);
        List<Workshop> existingWorkshops = databaseManager.read(Workshop.class, "workshop = :workshop", parameters);

        Workshop workshop;
        if (existingWorkshops.isEmpty()) {
          // Create and save the workshop to the database if it doesn't exist
          workshop = new Workshop(workshopName);
          databaseManager.create(workshop);
        } else {
          // Use the existing programme if it already exists
          workshop = existingWorkshops.get(0);
        }
        databaseManager.update(programme);

        databaseManager.close();

        databaseManager.open();
        // Associate the person with the programme, stage and school
        if (!person.getProgrammes().contains(programme)) {
          person.addProgramme(programme);
        }

        if (!person.getStages().contains(stage)) {
          person.addStage(stage);
        }

        if (!person.getSchools().contains(school)) {
          person.addSchool(school);
        }
        databaseManager.update(person);

        // Create and save the registration to the database
        Registration registration = new Registration(person, workshop);
        databaseManager.create(registration);

        databaseManager.update(registration);

        // Check if the instructor already exists in the database
        parameters = new HashMap<>();
        parameters.put("firstName", firstName);
        parameters.put("lastName", lastName);
        parameters.put("login", login);
        List<Instructor> existingInstructors = databaseManager.read(Instructor.class,
            "person.firstName = :firstName AND person.lastName = :lastName AND person.login = :login", parameters);

        Instructor instructor;
        if (existingInstructors.isEmpty()) {
          // Create and save the instructor to the database if it doesn't exist
          // 'certified' field left empty (null) for now
          instructor = new Instructor(person, null);
          databaseManager.create(instructor);
        } else {
          // Use the existing instructor if it already exists
          instructor = existingInstructors.get(0);
        }
        databaseManager.update(instructor);

        // Create and save the InstructorWorkshop association
        InstructorWorkshop instructorWorkshop = new InstructorWorkshop(instructor, workshop);
        databaseManager.create(instructorWorkshop);


        databaseManager.close();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    }

}