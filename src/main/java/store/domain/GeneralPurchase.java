package store.domain;

public class GeneralPurchase {
    private final GeneralProduct generalProduct;
    private int quantity;

    public GeneralPurchase(final GeneralProduct generalProduct, final int quantity) {
        this.quantity = quantity;
        this.generalProduct = generalProduct;
    }

    public String getName() {
        return generalProduct.getName();
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return generalProduct.getPrice() * quantity;
    }

    @Override
    public String toString() {
        String priceFormat = String.format("%,d", generalProduct.getPrice() * quantity);
        return generalProduct.getName() + "\t\t" + quantity + "\t\t" + priceFormat
                + "\n";
    }
}
