import contacts.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;

public class BookTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;
    private static final InputStream originalIn = System.in;


    @BeforeAll
    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    @Test
    public void shouldAddPerson() {
        System.setIn(new ByteArrayInputStream("person\nDmitrii\nBoronin\n1992-08-17\nM\n+48 (111) 333".getBytes()));

        Book book = new Book();

        book.add();
        Person expected = new Person("+48 (111) 333", "Dmitrii", "Boronin", LocalDate.parse("1992-08-17"), Gender.MALE);
        Person saved = (Person) book.list.get(0);

        Assertions.assertEquals(saved.getPhone(), expected.getPhone());
        Assertions.assertEquals(saved.getName(), expected.getName());
        Assertions.assertEquals(saved.getSurname(), expected.getSurname());
        Assertions.assertEquals(saved.getBirthDateString(), expected.getBirthDateString());
        Assertions.assertEquals(saved.getGenderString(), expected.getGenderString());
    }

    @Test
    public void shouldAddOrganization() {
        System.setIn(new ByteArrayInputStream("organization\nKFC\nLenin Street 1\n+48 (111) 333".getBytes()));

        Book book = new Book();

        book.add();
        Organisation expected = new Organisation("+48 (111) 333", "KFC", "Lenin Street 1");
        Organisation saved = (Organisation) book.list.get(0);

        Assertions.assertEquals(saved.getPhone(), expected.getPhone());
        Assertions.assertEquals(saved.getTitle(), expected.getTitle());
        Assertions.assertEquals(saved.getAddress(), expected.getAddress());
    }

    @Test
    public void shouldEditContact() {
        System.setIn(new ByteArrayInputStream("person\nDmitrii\nBoronin\n1992-08-17\nM\n+48 (111) 333".getBytes()));

        Book book = new Book();

        book.add();
        Person saved = (Person) book.list.get(0);

        Assertions.assertEquals("Dmitrii", saved.getName());
        book.selectedRecordNumber = 1;
        System.setIn(new ByteArrayInputStream("name\nSasha".getBytes()));
        book.edit();
        Assertions.assertEquals("Sasha", saved.getName());
    }

    @Test
    public void shouldEditOrganization() {
        System.setIn(new ByteArrayInputStream("organization\nKFC\nLenin Street 1\n+48 (111) 333".getBytes()));

        Book book = new Book();

        book.add();
        Organisation saved = (Organisation) book.list.get(0);

        Assertions.assertEquals("KFC", saved.getTitle());
        book.selectedRecordNumber = 1;
        System.setIn(new ByteArrayInputStream("name\nBurger King".getBytes()));
        book.edit();
        Assertions.assertEquals("Burger King", saved.getTitle());
    }

    @Test
    public void shouldListRecords() {
        System.setIn(new ByteArrayInputStream("organization\nKFC\nLenin Street 1\n+48 (111) 333".getBytes()));
        Book book = new Book();
        book.add();
        System.setIn(new ByteArrayInputStream("person\nDmitrii\nBoronin\n1992-08-17\nM\n+48 (111) 333".getBytes()));
        book.add();

        book.list();
        Assertions.assertTrue(outContent.toString().contains("1. KFC"));
        Assertions.assertTrue(outContent.toString().contains("2. Dmitrii Boronin"));
    }

    @Test
    public void shouldSearch() {
        System.setIn(new ByteArrayInputStream("organization\nKFC\nLenin Street 1\n+48 (111) 333".getBytes()));
        Book book = new Book();
        book.add();
        System.setIn(new ByteArrayInputStream("person\nDmitrii\nBoronin\n1992-08-17\nM\n+48 (111) 333".getBytes()));
        book.add();
        System.setIn(new ByteArrayInputStream("len".getBytes()));
        book.search();
        Assertions.assertTrue(outContent.toString().contains("Found 1 results: 1. KFC"));
    }
}
