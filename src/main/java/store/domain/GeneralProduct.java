package store.domain;

public class GeneralProduct {
    private final String name;
    private final int price;
    private int quantity;

    public GeneralProduct(final String name, final int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void subtractQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
