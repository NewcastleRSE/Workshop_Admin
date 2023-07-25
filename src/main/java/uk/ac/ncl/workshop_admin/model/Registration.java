package uk.ac.ncl.workshop_admin.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "registrations")
public class Registration implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @ManyToOne
  @JoinColumn(name = "person_id")
  private Person person;

  @ManyToOne
  @JoinColumn(name = "workshop_id")
  private Workshop workshop;

  public Registration() {
  }

  public Registration(Person person, Workshop workshop) {
    this.person = person;
    this.workshop = workshop;
  }

  public int getId() {
    return id;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public Workshop getWorkshop() {
    return workshop;
  }

  public void setWorkshop(Workshop workshop) {
    this.workshop = workshop;
  }

  @Override
  public String toString() {
    return String.format("Registration{Person: %s, Workshop: %s}", person, workshop);
  }
}


