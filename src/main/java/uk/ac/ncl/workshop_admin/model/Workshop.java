package uk.ac.ncl.workshop_admin.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

  @Override
  public String toString() {
    return String.format("workshop{%s}", workshop);
  }

}


