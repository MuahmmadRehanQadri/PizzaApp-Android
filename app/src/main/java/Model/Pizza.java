package Model;

/**
 * Created by Muhammad Rehan Qadri on 6/25/2015.
 */
public class Pizza {
    String name;
    String type;
    Double price;
    byte[] image;
    int quantity;

    public Pizza(String name, String type, Double price, byte[] image) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Double getPrice() {
        return price;
    }

    public byte[] getImage() {
        return image;
    }

}
