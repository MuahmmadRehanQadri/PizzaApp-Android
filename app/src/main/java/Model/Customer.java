package Model;

/**
 * Created by Muhammad Rehan Qadri
 */
public class Customer {
    String name;
    String phoneNumber;
    String address;
    String email;

    public Customer(String name, String phoneNumber, String address, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
}
