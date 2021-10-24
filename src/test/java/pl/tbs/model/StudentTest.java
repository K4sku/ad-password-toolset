package pl.tbs.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


public class StudentTest {
    Student nullStudent = new Student();
    Student emptyStudent = new Student(
        "",
        "",
        "",
        "", 
        "",
        "",
        "",
        ""
    );

    Student studentOne = new Student(
        "1",
        "A",
        "keanu_reeves",
        "Keanu", 
        "Reeves",
        "Keanu Reeves",
        "keanu_reeves@tbs.pl",
        "hiho12"
    );

    Student studentTwo = new Student(
        "2",
        "A",
        "keanu_reeves",
        "Keanu", 
        "Reeves",
        "Keanu Reeves",
        "keanu_reeves@tbs.pl",
        "hiho12"
    );

    @Test
    void equalsShouldPassWhenComparedToItself() {
        assertThat(studentOne).isEqualTo(studentOne);
        assertThat(nullStudent).isEqualTo(nullStudent);
    }

    @Test
    void equalsShouldNotFailWhenStringsAreEmptyOrNull() {
        assertThat(nullStudent).isNotEqualTo(studentOne);
        assertThat(studentOne).isNotEqualTo(nullStudent);
        assertThat(nullStudent).isNotEqualTo(emptyStudent);
        assertThat(emptyStudent).isNotEqualTo(nullStudent);
    }

    @Test
    void equalsShouldFailWhenComparedToNull() {
        assertThat(nullStudent).isNotEqualTo(null);
        assertThat(studentOne).isNotEqualTo(null);
    }

    @Test
    void equalsShouldFailWhenComparedToDiffrentClass() {
        assertThat(studentOne).isNotEqualTo(new String());
    }

    @Test
    void equalsShouldFailWhenComparedToDiffrentStudent() {
        assertThat(studentOne).isNotEqualTo(studentTwo);
    }
}
