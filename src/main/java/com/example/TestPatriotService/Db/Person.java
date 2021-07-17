package com.example.TestPatriotService.Db;


public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String midName;
    private String phone;
    private String sfId;
    private String email;

    public int getId() {
        return id;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", midName='" + midName + '\'' +
                ", phone='" + phone + '\'' +
                ", sfId='" + sfId + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Person(int id, String firstName, String lastName, String midName, String phone, String sfId, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.midName = midName;
        this.phone = phone;
        this.sfId = sfId;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSfId() {
        return sfId;
    }

    public void setSfId(String sfId) {
        this.sfId = sfId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
