package uk.ac.ncl.workshop_admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import uk.ac.ncl.workshop_admin.model.Programme;
import uk.ac.ncl.workshop_admin.model.Stage;
import uk.ac.ncl.workshop_admin.util.DatabaseManager;
import uk.ac.ncl.workshop_admin.model.School;
import uk.ac.ncl.workshop_admin.model.Person;


public class Main {

        private static final ArrayList<Person> PEOPLE_DATA = new ArrayList<>(Arrays.asList(
            new Person("Harry", "Potter"),
            new Person("James", "Potter"),
            new Person("Lilly", "Potter"),
            new Person("Hermione", "Granger"),
            new Person("Arthur", "Weasley"),
            new Person("Bill", "Weasley"),
            new Person("Fred", "Weasley")
        ));
        private static final ArrayList<School> SCHOOL_DATA = new ArrayList<>(Arrays.asList(
            new School("Medical School"),
            new School("Engineering"),
            new School("Business"),
            new School("Law"),
            new School("Witchcraft"),
            new School("Real or Cake"),
            new School("Dark Arts")
        ));

        private static final ArrayList<Programme> PROGRAMME_DATA = new ArrayList<>(Arrays.asList(
            new Programme("BSc"),
            new Programme("CompSci"),
            new Programme("MSc"),
            new Programme("PhD")
        ));

        private static final ArrayList<Stage> STAGE_DATA = new ArrayList<>(Arrays.asList(
            new Stage("I"),
            new Stage("II"),
            new Stage("III"),
            new Stage("IV"),
            new Stage("V"),
            new Stage("VI"),
            new Stage("VII"),
            new Stage("VIII"),
            new Stage("IX"),
            new Stage("X")
        ));

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

            databaseManager.open();
            System.out.println("\n---Insert initial dataset---\n");
            for (Person person : PEOPLE_DATA) {
                databaseManager.create(person);
            }
            for (School school : SCHOOL_DATA) {
                databaseManager.create(school);
            }

            for (Programme programme : PROGRAMME_DATA) {
                databaseManager.create(programme);
            }

            for (Stage stage : STAGE_DATA) {
                databaseManager.create(stage);
            }

            databaseManager.close();

            databaseManager.open();
            // Set school for harry
            parameters = new HashMap<>();
            parameters.put("school", "Medical School");
            School medical = databaseManager.read(School.class, "school = :school", parameters).get(0);

            parameters = new HashMap<>();
            parameters.put("name", "Harry");
            Person harry = databaseManager.read(Person.class, "firstName = :name", parameters).get(0);
            harry.addSchool(medical);

            // Set programme for harry
            parameters = new HashMap<>();
            parameters.put("programme", "CompSci");
            Programme compsci = databaseManager.read(Programme.class, "programme = :programme", parameters).get(0);
            harry.addProgramme(compsci);
            databaseManager.update(harry);

            databaseManager.close();
        }
}