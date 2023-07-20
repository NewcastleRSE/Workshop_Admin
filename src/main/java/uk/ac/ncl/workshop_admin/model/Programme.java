package uk.ac.ncl.workshop_admin.model;

import javax.persistence.*;
import java.util.Set;
  @Entity
  @Table(name="programme")
  public class Programme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="programme")
    private String programme;

    @ManyToMany(mappedBy = "programmes")
    private Set<Person> people;

    public Programme() {

    }

    public Programme(String programme) {
      this.programme = programme;
    }

    public int getId() {
      return id;
    }

    public String getProgramme() {
      return programme;
    }

    public void setProgramme(String programme) {
      this.programme = programme;
    }

    public Set<Person> getPeople() {
      return people;
    }

    public void clearPeople() {
      people.clear();
    }

    public void addPerson(Person programme) {
      people.add(programme);
    }

    public void removePerson(Person programme) {
      people.remove(programme);
    }

    @Override
    public String toString() {
      return String.format("programme{%s}", programme);
    }

    public String toFullString() {
      return String.format("%s - %s", toString(), people);
    }
  }
