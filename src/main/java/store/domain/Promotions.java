package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Promotions {
    private final List<Promotion> promotions = new ArrayList<>();

    public void addPromotion(Promotion promotion) {
        promotions.add(promotion);
    }

    public Promotion findPromotion(String name) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(name)) {
                return promotion;
            }
        }
        return null;
    }
}
