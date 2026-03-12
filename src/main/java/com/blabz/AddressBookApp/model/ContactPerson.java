package com.blabz.AddressBookApp.model;

import java.util.Objects;

public class ContactPerson {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String email;

    public ContactPerson() {}

    public ContactPerson(String firstName, String lastName, String address, String city, String state, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    // Getters and setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactPerson)) return false;
        ContactPerson that = (ContactPerson) o;
        return Objects.equals(normalize(firstName), normalize(that.firstName)) &&
               Objects.equals(normalize(lastName), normalize(that.lastName));
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalize(firstName), normalize(lastName));
    }

    private String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }

    @Override
    public String toString() {
        return String.format("%s %s, %s, %s, %s, %s, %s",
                firstName, lastName, address, city, state, zip, phone);
    }
}
