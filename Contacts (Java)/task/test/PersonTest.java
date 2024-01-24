import contacts.Gender;
import contacts.Person;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

public class PersonTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    @BeforeAll
    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void shouldReturnMessageWhenBirthDateOrGenderIsNull() {
        Person person = new Person("+48 (111) 333", "Dmitrii", "Boronin", null, null);
        Assertions.assertEquals("[no data]", person.getBirthDateString());
        Assertions.assertEquals("[no data]", person.getGenderString());
    }

    @Test
    public void shouldShowInfo() {
        Person person = new Person("+48 (111) 333", "Dmitrii", "Boronin", LocalDate.parse("1992-08-17"), Gender.MALE);
        person.info();

        Assertions.assertTrue(outContent.toString().contains("Name: Dmitrii"));
        Assertions.assertTrue(outContent.toString().contains("Surname: Boronin"));
        Assertions.assertTrue(outContent.toString().contains("Birth date: 1992-08-17"));
        Assertions.assertTrue(outContent.toString().contains("Gender: M"));
        Assertions.assertTrue(outContent.toString().contains("Number: +48 (111) 333"));
    }

    @Test
    public void shouldEditFields() {
        Person person = new Person("+48 (111) 333", "Dmitrii", "Boronin", LocalDate.parse("1992-08-17"), Gender.MALE);

        System.setIn(new ByteArrayInputStream("name\nSasha".getBytes()));
        person.edit();

        Assertions.assertEquals("Sasha", person.getName());

        System.setIn(new ByteArrayInputStream("number\n+123 456".getBytes()));
        person.edit();

        Assertions.assertEquals("+123 456", person.getPhone());

        System.setIn(new ByteArrayInputStream("surname\nAleksandrov".getBytes()));
        person.edit();

        Assertions.assertEquals("Aleksandrov", person.getSurname());

        System.setIn(new ByteArrayInputStream("birth\n2000-01-01".getBytes()));
        person.edit();

        Assertions.assertEquals("2000-01-01", person.getBirthDateString());

        System.setIn(new ByteArrayInputStream("gender\nF".getBytes()));
        person.edit();

        Assertions.assertEquals("F", person.getGenderString());
    }
}
