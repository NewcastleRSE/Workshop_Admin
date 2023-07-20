package uk.ac.ncl.workshop_admin.model;

import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name="stage")
public class Stage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private int id;

  @Column(name="stage")
  private String stage;

  @ManyToMany(mappedBy = "stages")
  private Set<Person> people;

  public Stage() {

  }

  public Stage(String stage) {
    this.stage = stage;
  }

  public int getId() {
    return id;
  }

  public String getStage() {
    return stage;
  }

  public void setStage(String stage) {
    this.stage = stage;
  }

  public Set<Person> getPeople() {
    return people;
  }

  public void clearPeople() {
    people.clear();
  }

  public void addPerson(Person stage) {
    people.add(stage);
  }

  public void removePerson(Person stage) {
    people.remove(stage);
  }

  @Override
  public String toString() {
    return String.format("stage{%s}", stage);
  }

  public String toFullString() {
    return String.format("%s - %s", toString(), people);
  }
}

