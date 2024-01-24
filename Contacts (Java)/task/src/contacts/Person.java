package contacts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Person extends Contact {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Gender gender;

    public Person(String phone, String name, String surname, LocalDate birthDate, Gender gender) {
        super(phone);
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthDateString() {
        if (birthDate != null) {
            return birthDate.toString();
        }

        return "[no data]";
    }

    public String getGenderString() {
        if (this.gender == null) {
            return "[no data]";
        }

        return gender == Gender.MALE ? "M" : "F";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void info() {
        System.out.println("Name: " + this.getName());
        System.out.println("Surname: " + this.getSurname());
        System.out.println("Birth date: " + this.getBirthDateString());
        System.out.println("Gender: " + this.getGenderString());

        String phoneText = this.getPhone().isEmpty() ? "[no number]" : this.getPhone();
        System.out.println("Number: " + phoneText);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("Time created: " + this.timeCreated.format(formatter));
        System.out.println("Time last edit: " + this.timeLastEdit.format(formatter));
    }

    public void list(int index) {
        System.out.format("%d. %s %s", index, this.getName(), this.getSurname());
    }

    public void edit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a field (name, surname, birth, gender, number): ");

        String editAction = scanner.nextLine();

        if (editAction.equals("number")) {
            System.out.println("Enter name: ");
            String phone = scanner.nextLine();
            this.setPhone(phone);
            if (!this.isPhoneValid()) {
                this.setPhone("");
                System.out.println("Wrong number format!");
            }
        }

        switch (editAction) {
            case "name":
                System.out.println("Enter name: ");
                String name = scanner.nextLine();
                this.setName(name);
                break;
            case "surname":
                System.out.println("Enter name: ");
                String surname = scanner.nextLine();
                this.setSurname(surname);
                break;
            case "birth":
                System.out.println("Enter the birth date:");
                String birthDateString = scanner.nextLine();

                try {
                    this.setBirthDate(LocalDate.parse(birthDateString));
                } catch (Exception exception) {
                    System.out.println("Bad birth date!");
                }
                break;
            case "gender":
                System.out.println("Enter the gender (M, F):");
                String genderString = scanner.nextLine();
                Gender gender = null;

                switch (genderString) {
                    case "M":
                        gender = Gender.MALE;
                        break;
                    case "F":
                        gender = Gender.FEMALE;
                        break;
                    default:
                        System.out.println("Bad gender!");
                }

                this.setGender(gender);
                break;
        }

        this.timeLastEdit = LocalDateTime.now();
    }

    public String getSearchString() {
        return this.getPhone() + this.name + this.surname + this.getBirthDateString() + this.getGenderString();
    }
}
