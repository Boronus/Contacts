package contacts;

import java.time.LocalDateTime;

public abstract class Contact {
    private String phone;
    public LocalDateTime timeCreated;
    public LocalDateTime timeLastEdit;

    Contact(String phone) {
        this.phone = phone;
        this.timeCreated = LocalDateTime.now();
        this.timeLastEdit = LocalDateTime.now();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPhoneValid() {
        String phoneRexEx = "^[+]?(\\w+[-\\s]?)?([(]?\\w{2,}[)]?[-\\s]?)?(\\w{2,}[-\\s])?(\\w{2,}[-\\s])?(\\w{2,})?$";
        return this.phone.matches(phoneRexEx);
    }

    public boolean isPerson() {
        return this instanceof Person;
    }

    public abstract void info();

    public abstract void list(int index);
    public abstract void edit();

    public abstract String getSearchString();
}
