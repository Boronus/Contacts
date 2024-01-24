package contacts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Organisation extends Contact {
    private String title;
    private String address;

    public Organisation(String phone, String title, String address) {
        super(phone);
        this.title = title;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public void info() {
        System.out.println("Organization name: " + this.getTitle());
        System.out.println("Address: " + this.getAddress());

        String phoneText = this.getPhone().isEmpty() ? "[no number]" : this.getPhone();
        System.out.println("Number: " + phoneText);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("Time created: " + this.timeCreated.format(formatter));
        System.out.println("Time last edit: " + this.timeLastEdit.format(formatter));
    }

    public void list(int index) {
        System.out.format("%d. %s", index, this.getTitle());
    }

    public void edit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a field (name, address, number): ");

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
                System.out.println("Enter title: ");
                String title = scanner.nextLine();
                this.setTitle(title);
                break;
            case "address":
                System.out.println("Enter address: ");
                String address = scanner.nextLine();
                this.setAddress(address);
                break;
        }

        this.timeLastEdit = LocalDateTime.now();
    }

    public String getSearchString() {
        return this.getPhone() + this.title + this.getAddress();
    }

}
