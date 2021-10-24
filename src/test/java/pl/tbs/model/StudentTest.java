package pl.tbs.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


public class StudentTest {
    Student emptyStudent = new Student();
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
    }

    @Test
    void equalsShouldFailWhenComparedToNull() {
        
        assertThat(emptyStudent).isNotEqualTo(null);
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
