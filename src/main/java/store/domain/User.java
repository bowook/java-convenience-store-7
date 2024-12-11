package store.domain;

import java.util.ArrayList;
import java.util.List;
import store.domain.constant.Answer;

public class User {
    private final List<PromotionPurchase> promotionPurchases = new ArrayList<>();
    private final List<GeneralPurchase> generalPurchases = new ArrayList<>();
    private boolean membership;

    public void checkMembership(Answer answer) {
        if (answer.equals(Answer.YES)) {
            this.membership = true;
            return;
        }
        this.membership = false;
    }

    public void initUser() {
        promotionPurchases.clear();
        generalPurchases.clear();
    }

    public void addPromotionPurchase(PromotionPurchase promotionPurchase) {
        this.promotionPurchases.add(promotionPurchase);
    }

    public void addRemainProduct(GeneralProduct generalProduct, int quantity) {
        this.generalPurchases.add(new GeneralPurchase(generalProduct, quantity));

    }

    public void addOneMore(String name) {
        PromotionPurchase promotionPurchase = findPromotionPurchase(name);
        promotionPurchase.addQuantityOneMore(1);
    }

    private PromotionPurchase findPromotionPurchase(String name) {
        return promotionPurchases.stream().filter(promotionPurchase -> promotionPurchase.getName().equals(name))
                .findFirst().orElseThrow();
    }

    private int totalQuantity() {
        int sum = 0;
        for (PromotionPurchase promotionPurchase : promotionPurchases) {
            sum += promotionPurchase.getQuantity();
        }
        for (GeneralPurchase generalPurchase : generalPurchases) {
            sum += generalPurchase.getQuantity();
        }
        return sum;
    }

    private int totalPrice() {
        int sum = 0;
        for (PromotionPurchase promotionPurchase : promotionPurchases) {
            sum += promotionPurchase.getTotalPrice();
        }
        for (GeneralPurchase generalPurchase : generalPurchases) {
            sum += generalPurchase.getTotalPrice();
        }
        return sum;
    }

    private int calculatePromotionDiscount() {
        int sum = 0;
        for (PromotionPurchase promotionPurchase : promotionPurchases) {
            sum += promotionPurchase.calculatePromotionDiscount();
        }
        return sum;
    }

    private int calculateMembershipDiscount() {
        if (!membership) {
            return 0;
        }
        int sum = 0;
        for (GeneralPurchase generalPurchase : generalPurchases) {
            sum += generalPurchase.getTotalPrice();
        }
        int membership = (int) (sum * 0.3);
        if (membership > 8000) {
            membership = 8000;
        }
        return membership;
    }

    private int calculateFinalPayment() {
        int totalPurchaseAmount = totalPrice();
        int promotionDiscountAmount = calculatePromotionDiscount();
        int membershipDiscountAmount = calculateMembershipDiscount();
        return totalPurchaseAmount - promotionDiscountAmount - membershipDiscountAmount;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("===========W 편의점=============\n");
        stringBuilder.append("상품명\t\t수량\t금액\n");
        for (PromotionPurchase promotionPurchase : promotionPurchases) {
            stringBuilder.append(promotionPurchase.toString());
        }
        for (GeneralPurchase generalPurchase : generalPurchases) {
            stringBuilder.append(generalPurchase.toString());
        }
        stringBuilder.append("===========증\t정=============\n");
        for (PromotionPurchase promotionPurchase : promotionPurchases) {
            stringBuilder.append(promotionPurchase.getName()).append("\t\t").append(promotionPurchase.calculateGet())
                    .append("\n");
        }
        stringBuilder.append("==============================\n");
        stringBuilder.append("총구매액").append("\t\t").append(totalQuantity()).append("\t")
                .append(String.format("%,d", totalPrice())).append("\n");
        stringBuilder.append("행사할인").append("\t\t").append(String.format("-%,d", calculatePromotionDiscount()))
                .append("\n");
        stringBuilder.append("멤버십할인").append("\t\t").append(String.format("-%,d", calculateMembershipDiscount()))
                .append("\n");
        stringBuilder.append("내실돈").append("\t\t").append(String.format("%,d", calculateFinalPayment())).append("\n");
        return stringBuilder.toString();
    }

}
