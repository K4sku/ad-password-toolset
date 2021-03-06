package pl.tbs.model;

import org.apache.commons.lang3.StringUtils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {

    public enum Year {
        PRENURSERY, NURSERY, RECEPTION, YEAR1, YEAR2, YEAR3, YEAR4, YEAR5, YEAR6, YEAR7, YEAR8, YEAR9, YEAR10, YEAR11,
        YEAR12, YEAR13;

        public static String getDisplayText(Year arg0) {
            return switch (arg0) {
            case PRENURSERY -> "Pre-Nur";
            case NURSERY -> "Nursery";
            case RECEPTION -> "Reception";
            case YEAR1 -> "1";
            case YEAR2 -> "2";
            case YEAR3 -> "3";
            case YEAR4 -> "4";
            case YEAR5 -> "5";
            case YEAR6 -> "6";
            case YEAR7 -> "7";
            case YEAR8 -> "8";
            case YEAR9 -> "9";
            case YEAR10 -> "10";
            case YEAR11 -> "11";
            case YEAR12 -> "12";
            case YEAR13 -> "13";
            };
        }

    }

    private final IntegerProperty rowNumber = new SimpleIntegerProperty();
    private final ObjectProperty<Year> year = new SimpleObjectProperty<>();
    private final StringProperty form = new SimpleStringProperty();
    private final StringProperty upn = new SimpleStringProperty();
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final StringProperty displayName = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    public Student() {

    }

    public Student(Year year, String form, String upn, String firstName, String lastName, String displayName,
            String email, String password) {
        setYear(year);
        setForm(form);
        setUpn(upn);
        setFirstName(firstName);
        setLastName(lastName);
        setDisplayName(displayName);
        setEmail(email);
        setPassword(password);
    }

    public final IntegerProperty rowNumberProperty() {
        return this.rowNumber;
    }

    public final int getRowNumber() {
        return this.rowNumberProperty().get();
    }

    public final void setRowNumber(int rowNumber) {
        this.rowNumberProperty().set(rowNumber);
    }

    public final ObjectProperty<Year> yearProperty() {
        return this.year;
    }

    public final Year getYear() {
        return this.year.get();
    }

    public final void setYear(Year year) {
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

    public final StringProperty upnProperty() {
        return this.upn;
    }

    public final String getUpn() {
        return this.upn.get();
    }

    public final void setUpn(String upn) {
        this.upnProperty().set(upn);
    }

    public final StringProperty firstNameProperty() {
        return this.firstName;
    }

    public final String getFirstName() {
        return this.firstNameProperty().get();
    }

    public final void setFirstName(String firstName) {
        this.firstNameProperty().set(firstName);
    }

    public final StringProperty lastNameProperty() {
        return this.lastName;
    }

    public final String getLastName() {
        return this.lastNameProperty().get();
    }

    public final void setLastName(String lastName) {
        this.lastNameProperty().set(lastName);
    }

    public final StringProperty displayNameProperty() {
        return this.displayName;
    }

    public final String getDisplayName() {
        return this.displayNameProperty().get();
    }

    public final void setDisplayName(String displayName) {
        this.displayNameProperty().set(displayName);
    }

    public final StringProperty emailProperty() {
        return this.email;
    }

    public final String getEmail() {
        return this.emailProperty().get();
    }

    public final void setEmail(String email) {
        this.emailProperty().set(email);
    }

    public final StringProperty passwordProperty() {
        return this.password;
    }

    public final String getPassword() {
        return passwordProperty().get();
    }

    public final void setPassword(String password) {
        this.passwordProperty().set(password);
    }

    public static final class Builder {
        private Integer rowNumber;
        private Year year;
        private String form;
        private String upn;
        private String firstName;
        private String lastName;
        private String displayName;
        private String email;
        private String password;

        public Builder rowNumber(int rowNumber) {
            this.rowNumber = rowNumber;
            return this;
        }

        public Builder year(Year year) {
            this.year = year;
            return this;
        }

        public Builder form(String form) {
            this.form = form;
            return this;
        }

        public Builder upn(String upn) {
            this.upn = upn;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Student build() {
            if (upn.isEmpty()) {
                throw new IllegalStateException("Upn is required");
            }
            if (year == null) {
                throw new IllegalStateException("Year is required");
            }
            Student student = new Student();
            student.setRowNumber(rowNumber);
            student.setYear(year);
            student.setForm(form);
            student.setUpn(upn);
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setDisplayName(displayName);
            student.setEmail(email);
            student.setPassword(password);
            return student;
        }

    }

    public boolean isStudentEmpty() {
        return StringUtils.isBlank(getUpn())
                && StringUtils.isBlank(getFirstName())
                && StringUtils.isBlank(getLastName()) 
                && StringUtils.isBlank(getDisplayName()) 
                && StringUtils.isBlank(getEmail());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        // cast object to Student
        Student other = (Student) obj;
        // calls compare to ensure null safety, fields are ordered from most prominent
        // diffrences
        if (!compare(upn.get(), other.upn.get()))
            return false;
        if (!compare(displayName.get(), other.displayName.get()))
            return false;
        if (!compare(password.get(), other.password.get()))
            return false;
        if (!compare(form.get(), other.form.get()))
            return false;
        if (yearProperty().get() != other.yearProperty().get())
            return false;
        if (!compare(email.get(), other.email.get()))
            return false;
        if (!compare(firstName.get(), other.firstName.get()))
            return false;
        if (!compare(lastName.get(), other.lastName.get()))
            return false;
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
