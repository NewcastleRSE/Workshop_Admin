package uk.ac.ncl.workshop_admin.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="people")
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private int id;

  @Column(name="first_name")
  private String firstName;
  @Column(name="last_name")
  private String lastName;

  @Column(name="contact_number")
  private String contactNumber;

  @Column(name="email")
  private String email;

  @Column(name="login")
  private String login;

  @OnDelete(action = OnDeleteAction.CASCADE)
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "person_school",
      joinColumns = {@JoinColumn(name = "person_id")},
      inverseJoinColumns = {@JoinColumn(name = "school_id")}
  )
  private Set<School> schools;

  @OnDelete(action = OnDeleteAction.CASCADE)
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "person_programme",
      joinColumns = {@JoinColumn(name = "person_id")},
      inverseJoinColumns = {@JoinColumn(name = "programme_id")}
  )
  private Set<Programme> programmes;

  @OnDelete(action = OnDeleteAction.CASCADE)
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "person_stage",
      joinColumns = {@JoinColumn(name = "person_id")},
      inverseJoinColumns = {@JoinColumn(name = "stage_id")}
  )
  private Set<Stage> stages;

  public Person() {

  }

  public Person(String firstName, String lastName, String login) {
    this.firstName   = firstName;
    this.lastName    = lastName;
    this.login       = login;
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  // Lazy initialization for the programmes set
  public Set<Programme> getProgrammes() {
    if (this.programmes == null) {
      this.programmes = new HashSet<>();
    }
    return programmes;
  }

  public void setProgrammes(Set<Programme> programmes) {
    this.programmes = programmes;
  }

  public void addProgramme(Programme programme) {
    getProgrammes().add(programme);
  }

  public void removeProgramme(Programme programme) {
    getProgrammes().remove(programme);
  }

  // Lazy initialization for the stages set
  public Set<Stage> getStages() {
    if (this.stages == null) {
      this.stages = new HashSet<>();
    }
    return stages;
  }

  public void setStages(Set<Stage> stages) {
    this.stages = stages;
  }

  public void addStage(Stage stage) {
    getStages().add(stage);
  }

  public void removeStage(Stage stage) {
    getStages().remove(stage);
  }

  // Lazy initialization for the school set
  public Set<School> getSchools() {
    if (this.schools == null) {
      this.schools = new HashSet<>();
    }
    return schools;
  }

  public void setSchools(Set<School> schools) {
    this.schools = schools;
  }

  public void addSchool(School school) {
    getSchools().add(school);
  }

  public void removeSchool(School school) {
    getSchools().remove(school);
  }

  @Override
  public String toString() {
    return String.format("Person{%s %s %s}", firstName, lastName, login);
  }

  public String toFullString() {
    return String.format("%s - %s", toString(), schools);
  }
}