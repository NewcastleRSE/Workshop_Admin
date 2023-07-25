package uk.ac.ncl.workshop_admin.model;

import javax.persistence.*;

@Entity
public class Instructor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "person_id") // Foreign key from Person entity
  private Person person;

  private Boolean certified; // Change the type to Boolean (wrapper class) to allow null

  public Instructor() {
    // Required no-arg constructor for Hibernate
  }

  public Instructor(Person person, Boolean certified) { // Update the constructor accordingly
    this.person = person;
    this.certified = certified;
  }

  // Getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public Boolean getCertified() { // Update the getter method name
    return certified;
  }

  public void setCertified(Boolean certified) { // Update the setter method name
    this.certified = certified;
  }

  // toString() method
  @Override
  public String toString() {
    return "Instructor{" +
        "id=" + id +
        ", person=" + person +
        ", certified=" + certified +
        '}';
  }
}


