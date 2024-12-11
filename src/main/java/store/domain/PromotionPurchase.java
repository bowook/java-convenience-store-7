package store.domain;

public class PromotionPurchase {
    private final PromotionProduct promotionProduct;
    private int quantity;

    public PromotionPurchase(final PromotionProduct promotionProduct, final int quantity) {
        this.promotionProduct = promotionProduct;
        this.quantity = quantity;
    }

    public int calculateGet() {
        return promotionProduct.calculateGet(quantity);
    }

    public int calculatePromotionDiscount() {
        return calculateGet() * promotionProduct.getPrice();
    }

    public String getName() {
        return promotionProduct.getName();
    }

    public void addQuantityOneMore(int quantity) {
        this.quantity += quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return promotionProduct.getPrice() * quantity;
    }

    @Override
    public String toString() {
        String priceFormat = String.format("%,d", promotionProduct.getPrice() * quantity);
        return promotionProduct.getName() + "\t\t" + quantity + "\t\t" + priceFormat
                + "\n";
    }
}
