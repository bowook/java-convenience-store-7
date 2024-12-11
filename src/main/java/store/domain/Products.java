package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Products {
    private final List<PromotionProduct> promotionProducts = new ArrayList<>();
    private final List<GeneralProduct> generalProducts = new ArrayList<>();

    public void addPromotionProduct(PromotionProduct promotionProduct) {
        this.promotionProducts.add(promotionProduct);
    }

    public void addGeneralProduct(GeneralProduct generalProduct) {
        this.generalProducts.add(generalProduct);
    }

    public void checkGeneralProduct() {
        for (PromotionProduct promotionProduct : promotionProducts) {
            boolean flag = findSameGeneralProduct(promotionProduct);
            if (!flag) {
                addGeneralProduct(new GeneralProduct(promotionProduct.getName(), promotionProduct.getPrice(), 0));
            }
        }
    }
    
    private boolean findSameGeneralProduct(PromotionProduct promotionProduct) {
        for (GeneralProduct generalProduct : generalProducts) {
            if (promotionProduct.getName().equals(generalProduct.getName())) {
                return true;
            }
        }
        return false;
    }
}
