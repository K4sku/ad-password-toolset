package pl.tbs.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private final StringProperty year = new SimpleStringProperty();
    private final StringProperty form = new SimpleStringProperty();
    private final StringProperty upn = new SimpleStringProperty();
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final StringProperty displayName = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    public Student(){

    }

    public Student(String year, String form, String upn, String firstName, String lastName, String displayName, String email, String password){
        setYear(year);
        setForm(form);
        setUpn(upn);
        setFirstName(firstName);
        setLastName(lastName);
        setDisplayName(displayName);
        setEmail(email);
        setPassword(password);
    }

    public final StringProperty yearProperty() {
        return this.year;
    }

    public final String getYear() {
        return this.year.get();
    }

    public final void setYear(String year) {
        this.yearProperty().set(year);
    }

    public final StringProperty formProperty() {
        return this.form;
    }

    public final String getForm() {
        return this.form.get();
    }

    public final void setForm(String form) {
        this.formProperty().set(form);
    }

    public final StringProperty upnProperty(){
        return this.upn;
    }

    public final String getUpn() {
        return this.upn.get();
    }

    public final void setUpn(String upn) {
        this.upnProperty().set(upn);
    }

    public final StringProperty firstNameProperty(){
        return this.firstName;
    }

    public final String getFirstName() {
        return this.firstNameProperty().get();
    }

    public final void setFirstName(String firstName) {
        this.firstNameProperty().set(firstName);
    }

    public final StringProperty lastNameProperty(){
        return this.lastName;
    }

    public final String getLastName() {
        return this.lastNameProperty().get();
    }

    public final void setLastName(String lastName) {
        this.lastNameProperty().set(lastName);
    }

    public final StringProperty displayNameProperty(){
        return this.displayName;
    }

    public final String getDisplayName() {
        return this.displayNameProperty().get();
    }

    public final void setDisplayName(String displayName) {
        this.displayNameProperty().set(displayName);
    }

    public final StringProperty emailProperty(){
        return this.email;
    }

    public final String getEmail() {
        return this.emailProperty().get();
    }

    public final void setEmail(String email) {
        this.emailProperty().set(email);
    }

    public final StringProperty passwordProperty(){
        return this.password;
    }

    public final String getPassword(){
        return passwordProperty().get();
    }

    public final void setPassword(String password) {
        this.passwordProperty().set(password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        //cast object to Student
        Student other = (Student) obj;
        //calls compare to ensure null safety, fields are ordered from most prominent diffrences
        if (!compare(upn.get(),other.upn.get())) return false;
        if (!compare(displayName.get(),other.displayName.get())) return false;
        if (!compare(password.get(),other.password.get())) return false;
        if (!compare(form.get(),other.form.get())) return false;
        if (!compare(year.get(),other.year.get())) return false;
        if (!compare(email.get(),other.email.get())) return false;
        if (!compare(firstName.get(),other.firstName.get())) return false;
        if (!compare(lastName.get(),other.lastName.get())) return false; 
        return true;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    // copy of Apache Commons StringUtils#equals method 
    private static boolean compare(String str1, String str2) {
        return (str1 == null ? str2 == null : str1.equals(str2));
    }

}


