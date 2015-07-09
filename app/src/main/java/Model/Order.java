package Model;

import java.util.ArrayList;

/**
 * Created by Muhammad Rehan Qadri
 */
public class Order {
    int orderId;
    Customer customer;
    ArrayList<Pizza> pizzasOrdered;

    public Order(int orderId, Customer customer, ArrayList<Pizza> pizzasOrdered) {
        this.orderId = orderId;
        this.customer = customer;
        this.pizzasOrdered = pizzasOrdered;
    }
}
