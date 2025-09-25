package edu.ccrm.domain;

public final class Name {
    private final String fullName;

    public Name(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}