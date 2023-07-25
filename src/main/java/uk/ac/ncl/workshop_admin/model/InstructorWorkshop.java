package uk.ac.ncl.workshop_admin.model;

import javax.persistence.*;

@Entity
@Table(name = "instructor_workshop")
public class InstructorWorkshop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "instructor_id")
  private Instructor instructor;

  @ManyToOne
  @JoinColumn(name = "workshop_id")
  private Workshop workshop;

  public InstructorWorkshop() {
  }

  public InstructorWorkshop(Instructor instructor, Workshop workshop) {
    this.instructor = instructor;
    this.workshop = workshop;
  }

  // Getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Instructor getInstructor() {
    return instructor;
  }

  public void setInstructor(Instructor instructor) {
    this.instructor = instructor;
  }

  public Workshop getWorkshop() {
    return workshop;
  }

  public void setWorkshop(Workshop workshop) {
    this.workshop = workshop;
  }

  // toString() method
  @Override
  public String toString() {
    return "InstructorWorkshop{" +
        "id=" + id +
        ", instructor=" + instructor +
        ", workshop=" + workshop +
        '}';
  }
}

