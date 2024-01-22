package contacts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Book {
    public ArrayList<Contact> list;
    ArrayList<Contact> lastSearch = new ArrayList<>();
    Integer selectedRecordNumber = null;

    Book() {
        this.list = new ArrayList<>();
    }

    public void add() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the type (person, organization):");
        String type = scanner.nextLine();

        switch (type) {
            case "person":
                this.addPerson();
                break;
            case "organization":
                this.addOrganisation();
                break;
            default:
                System.out.println("Wrong type!");
        }
    }

    private void addPerson() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name:");
        String name = scanner.nextLine();

        System.out.println("Enter the surname:");
        String surname = scanner.nextLine();

        System.out.println("Enter the birth date:");
        String birthDateString = scanner.nextLine();
        LocalDate birthDate = null;

        try {
            birthDate = LocalDate.parse(birthDateString);
        } catch (Exception exception) {
            System.out.println("Bad birth date!");
        }

        System.out.println("Enter the gender (M, F):");
        String genderString = scanner.nextLine();
        Boolean gender = null;

        switch (genderString) {
            case "M":
                gender = true;
                break;
            case "F":
                gender = false;
                break;
            default:
                System.out.println("Bad gender!");
        }

        System.out.println("Enter the number:");
        String phone = scanner.nextLine();

        Contact contact = new Person(phone, name, surname, birthDate, gender);

        if (!contact.isPhoneValid()) {
            contact.setPhone("");
            System.out.println("Wrong number format!");
        }

        System.out.println("The record added.");

        this.list.add(contact);
    }

    private void addOrganisation() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the organization name:");
        String title = scanner.nextLine();

        System.out.println("Enter the address:");
        String address = scanner.nextLine();

        System.out.println("Enter the number:");
        String phone = scanner.nextLine();

        Contact contact = new Organisation(phone, title, address);

        if (!contact.isPhoneValid()) {
            contact.setPhone("");
            System.out.println("Wrong number format!");
        }

        System.out.println("The record added.");

        this.list.add(contact);
    }

    public void remove() {
        if (this.list.isEmpty()) {
            System.out.println("No records to remove!");
            return;
        }

        this.list();

        System.out.println("Select a record: ");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        this.list.remove(index - 1);
        System.out.println("The record removed!");
    }

    public void delete() {
        this.list.remove(this.selectedRecordNumber);
        System.out.println("The record removed!");
    }

    public void edit() {
        Contact contact = this.list.get(this.selectedRecordNumber - 1);

        contact.edit();

        System.out.println("The record updated!");
    }

    public void count() {
        System.out.format("The Phone Book has %d records.", this.list.size());
    }

    public void info() {
        this.list();

        System.out.println("Enter index to show info: ");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        scanner.nextLine();
        Contact contact = this.list.get(index - 1);
        contact.info();
    }

    public void list() {
        int index = 1;
        for (Contact contact: this.list) {
            contact.list(index);
            System.out.println();
            index++;
        }
    }

    public void search() {
        System.out.println("Enter search query: ");
        Scanner scanner = new Scanner(System.in);
        String query = scanner.nextLine().toLowerCase();
        this.lastSearch.clear();

        for (Contact contact: this.list) {
            if (contact.getSearchString().toLowerCase().contains(query)) {
                this.lastSearch.add(contact);
            }
        }

        System.out.format("Found %d results: ", this.lastSearch.size());

        int index = 1;

        for (Contact contact: this.lastSearch) {
            contact.list(index);
            System.out.println();
            index++;
        }
    }
}