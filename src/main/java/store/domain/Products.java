package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.ArrayList;
import java.util.List;
import store.exception.CustomException;
import store.exception.ErrorMessage;

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

    public void checkQuantity(String productName, int inputQuantity) {
        int quantity = 0;
        quantity += findPromotionQuantity(productName);
        quantity += findGeneralQuantity(productName);
        if (inputQuantity > quantity) {
            throw CustomException.from(ErrorMessage.OVER_COUNT_ERROR);
        }
    }

    public void subtractOneMore(String productName) {
        PromotionProduct promotionProduct = findPromotionProduct(productName);
        promotionProduct.subtractQuantity(1);
    }

    public boolean checkOneMorePromotion(String productName, int quantity, User user) {
        PromotionProduct promotionProduct = findPromotionProduct(productName);
        if (promotionProduct.remainPromotionBuy(quantity)) {
            promotionProduct.subtractQuantity(quantity);
            user.addPromotionPurchase(new PromotionPurchase(promotionProduct, quantity));
            return true;
        }
        promotionProduct.subtractQuantity(quantity);
        user.addPromotionPurchase(new PromotionPurchase(promotionProduct, quantity));
        return false;
    }

    public void subtractGeneralProduct(String productName, int quantity) {
        GeneralProduct generalProduct = findGeneralProduct(productName);
        generalProduct.subtractQuantity(quantity);
    }

    public void subtractPromotionProduct(String productName, int quantity) {
        PromotionProduct promotionProduct = findPromotionProduct(productName);
        promotionProduct.subtractQuantity(quantity);
    }

    public int subtractAllQuantity(String productName, int quantity, User user) {
        PromotionProduct promotionProduct = findPromotionProduct(productName);
        int promotionProductQuantity =
                promotionProduct.calculateGet(promotionProduct.getQuantity()) * promotionProduct.getBuyAndGet();
        promotionProduct.subtractQuantity(promotionProductQuantity);
        user.addPromotionPurchase(new PromotionPurchase(promotionProduct, promotionProductQuantity));
        return quantity - promotionProductQuantity;
    }

    public boolean useOnlyPromotion(String productName, int quantity) {
        for (PromotionProduct promotionProduct : promotionProducts) {
            if (promotionProduct.getName().equals(productName) && promotionProduct.getQuantity() >= quantity) {
                return true;
            }
        }
        return false;
    }

    public boolean isPromotionProduct(String productName) {
        for (PromotionProduct promotionProduct : promotionProducts) {
            if (promotionProduct.getName().equals(productName) && promotionProduct.checkDate(DateTimes.now())) {
                return true;
            }
        }
        return false;
    }

    private int findGeneralQuantity(String productName) {
        for (GeneralProduct generalProduct : generalProducts) {
            if (generalProduct.getName().equals(productName)) {
                return generalProduct.getQuantity();
            }
        }
        return 0;
    }

    public int findPromotionQuantity(String productName) {
        for (PromotionProduct promotionProduct : promotionProducts) {
            if (promotionProduct.getName().equals(productName)) {
                return promotionProduct.getQuantity();
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        promotionToString(stringBuilder);
        generalToString(stringBuilder);
        return stringBuilder.toString();
    }

    private void promotionToString(StringBuilder stringBuilder) {
        for (PromotionProduct promotionProduct : promotionProducts) {
            if (promotionProduct.getQuantity() == 0) {
                String product = String.format("- %s %,d원 재고 없음%n", promotionProduct.getName(),
                        promotionProduct.getPrice());
                stringBuilder.append(product);
                continue;
            }
            String product = String.format("- %s %,d원 %d개 %s%n", promotionProduct.getName(),
                    promotionProduct.getPrice(), promotionProduct.getQuantity(), promotionProduct.getPromotionName());
            stringBuilder.append(product);
        }
    }

    private void generalToString(StringBuilder stringBuilder) {
        for (GeneralProduct pr : generalProducts) {
            if (pr.getQuantity() == 0) {
                String product = String.format("- %s %,d원 재고 없음%n", pr.getName(), pr.getPrice());
                stringBuilder.append(product);
                continue;
            }
            String product = String.format("- %s %,d원 %d개%n", pr.getName(), pr.getPrice(), pr.getQuantity());
            stringBuilder.append(product);
        }
    }

    private PromotionProduct findPromotionProduct(String name) {
        return promotionProducts.stream().filter(promotionProduct -> promotionProduct.getName().equals(name))
                .findFirst().orElseThrow();
    }

    public GeneralProduct findGeneralProduct(String name) {
        return generalProducts.stream().filter(generalProduct -> generalProduct.getName().equals(name))
                .findFirst().orElseThrow();
    }
}
