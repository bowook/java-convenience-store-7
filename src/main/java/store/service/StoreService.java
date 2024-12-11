package store.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import store.domain.GeneralProduct;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.domain.Promotions;
import store.exception.ErrorMessage;
import store.utils.FileLoader;

public class StoreService {
    private final Promotions promotions;
    private final Products products;
    private final static String COMMA = ",";
    private final static String HYPHEN = "-";

    public StoreService() {
        this.promotions = new Promotions();
        this.products = new Products();
        addPromotion(loadFile("promotions.md"));
        addProduct(loadFile("products.md"));
    }

    private void addProduct(List<String> products) {
        for (String product : products) {
            List<String> values = List.of(product.split(COMMA));
            String name = values.get(0);
            int price = Integer.parseInt(values.get(1));
            int quantity = Integer.parseInt(values.get(2));
            String promotionCase = values.get(3);
            addCaseProduct(name, price, quantity, promotionCase);
        }
        this.products.checkGeneralProduct();
    }

    private void addCaseProduct(String name, int price, int quantity, String value) {
        if (value.equals("null")) {
            this.products.addGeneralProduct(new GeneralProduct(name, price, quantity));
        }
        this.products.addPromotionProduct(new PromotionProduct(name, price, quantity, promotions.findPromotion(value)));
    }

    private void addPromotion(List<String> promotions) {
        for (String promotion : promotions) {
            List<String> values = List.of(promotion.split(COMMA));
            String name = values.get(0);
            int buy = Integer.parseInt(values.get(1));
            int get = Integer.parseInt(values.get(2));
            LocalDate startDate = generateDate(values.get(3));
            LocalDate endDate = generateDate(values.get(4));
            this.promotions.addPromotion(new Promotion(name, buy, get, startDate, endDate));
        }
    }

    private LocalDate generateDate(String value) {
        List<String> date = List.of(value.split(HYPHEN));
        return LocalDate.of(Integer.parseInt(date.get(0)), Integer.parseInt(date.get(1)),
                Integer.parseInt(date.get(2)));
    }

    private List<String> loadFile(String fileName) {
        try {
            return FileLoader.fileReadLine(fileName);
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.NO_FILE.getMessage());
        }
    }
}
