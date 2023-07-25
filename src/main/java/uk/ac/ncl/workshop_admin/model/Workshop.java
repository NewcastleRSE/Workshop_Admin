package uk.ac.ncl.workshop_admin.model;

import javax.persistence.*;

@Entity
@Table(name="workshops")
public class Workshop {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private int id;

  @Column(name="workshop")
  private String workshop;

  public Workshop() {
  }

  public Workshop(String workshop) {
    this.workshop = workshop;
  }

  public String getWorkshop() {
    return workshop;
  }

  public void setWorkshop(String workshop) {
    this.workshop = workshop;
  }

  @Override
  public String toString() {
    return String.format("workshop{%s}", workshop);
  }

}


