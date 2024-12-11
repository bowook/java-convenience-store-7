package store.domain;

import java.time.LocalDateTime;

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

    public void subtractQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public boolean remainPromotionBuy(int quantity) {
        int getPlusBuy = promotion.getBuy() + promotion.getGet();
        if (quantity % getPlusBuy == promotion.getBuy()) {
            return true;
        }
        return false;
    }

    public int calculateGet(int quantity) {
        int buyAndGet = promotion.getBuy() + promotion.getGet();
        return quantity / buyAndGet;
    }

    public boolean checkDate(LocalDateTime dateTime) {
        return promotion.isActive(dateTime);
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

    public String getPromotionName() {
        return promotion.getName();
    }
}
