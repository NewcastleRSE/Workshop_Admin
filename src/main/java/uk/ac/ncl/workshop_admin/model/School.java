package uk.ac.ncl.workshop_admin.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="school")
public class School {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private int id;

  @Column(name="school")
  private String school;

  @ManyToMany(mappedBy = "schools")
  private Set<Person> people;

  public School() {

  }

  public School(String school) {
    this.school = school;
  }

  public int getId() {
    return id;
  }

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public Set<Person> getPeople() {
    return people;
  }

  public void clearPeople() {
    people.clear();
  }

  public void addPerson(Person school) {
    people.add(school);
  }

  public void removePerson(Person school) {
    people.remove(school);
  }

  @Override
  public String toString() {
    return String.format("school{%s}", school);
  }

  public String toFullString() {
    return String.format("%s - %s", toString(), people);
  }
}
