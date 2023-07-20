package uk.ac.ncl.workshop_admin.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
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

  public Person(String firstName, String lastName) {
    this.firstName   = firstName;
    this.lastName    = lastName;
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

//  public Set<School> getSchools() {
//    return schools;
//  }
//
//  public void clearSchools() {
//    schools.clear();
//  }
//
  public void addSchool(School school) {

    schools.add(school);
  }
  public void addProgramme(Programme programme) {
    programmes.add(programme);
  }

//  public void removeSchool(School school) {
//    schools.remove(school);
//  }

  @Override
  public String toString() {
    return String.format("Person{%s %s}", firstName, lastName);
  }

  public String toFullString() {
    return String.format("%s - %s", toString(), schools);
  }
}