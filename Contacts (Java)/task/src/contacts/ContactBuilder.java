package contacts;

import java.util.List;

class ContactBuilder implements Builder {

    private String name;
    private String surname;
    private String phone = "";

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
