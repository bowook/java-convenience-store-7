package store.domain;

public class PromotionProduct {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    public PromotionProduct(final String name, final int price, int quantity, final Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
