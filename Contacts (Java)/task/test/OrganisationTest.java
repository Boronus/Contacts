import contacts.Organisation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class OrganisationTest {
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
    public void shouldShowInfo() {
        Organisation organisation = new Organisation("+48 (111) 333", "KFC", "Lenin Street 1");
        organisation.info();

        Assertions.assertTrue(outContent.toString().contains("Organization name: KFC"));
        Assertions.assertTrue(outContent.toString().contains("Address: Lenin Street 1"));
        Assertions.assertTrue(outContent.toString().contains("Number: +48 (111) 333"));
    }

    @Test
    public void shouldEditFields() {
        Organisation organisation = new Organisation("+48 (111) 333", "KFC", "Lenin Street 1");

        System.setIn(new ByteArrayInputStream("name\nBurger King".getBytes()));
        organisation.edit();

        Assertions.assertEquals("Burger King", organisation.getTitle());

        System.setIn(new ByteArrayInputStream("number\n+123 456".getBytes()));
        organisation.edit();

        Assertions.assertEquals("+123 456", organisation.getPhone());

        System.setIn(new ByteArrayInputStream("address\nGogol street 13".getBytes()));
        organisation.edit();

        Assertions.assertEquals("Gogol street 13", organisation.getAddress());
    }
}
