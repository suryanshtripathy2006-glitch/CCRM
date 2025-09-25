package edu.ccrm.domain;

public abstract class Person {
    private int id;
    private Name name;
    private String email;

    public Person(int id, Name name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract String getRole();

    @Override
    public String toString() {
        return "Person{id=" + id + ", name=" + name + ", email='" + email + "'}";
    }
}