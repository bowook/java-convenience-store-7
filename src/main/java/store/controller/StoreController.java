package store.controller;

import java.util.List;
import store.domain.Products;
import store.domain.User;
import store.domain.constant.Answer;
import store.exception.CustomException;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService storeService;
    private final User user;

    public StoreController(final InputView inputView, final OutputView outputView, final StoreService storeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storeService = storeService;
        this.user = new User();
    }

    public void start() {
        Answer answer;
        do {
            Products products = storeService.getProducts();
            outputView.writeWelcome(products);
            List<String> purchaseItems = readPurchaseItems(products);
            for (String purchaseItem : purchaseItems) {
                List<String> itemValues = List.of(purchaseItem.split("-"));
                String name = itemValues.get(0);
                int quantity = Integer.parseInt(itemValues.get(1));
                checkPromotion(name, quantity, products);
            }
            user.checkMembership(readMembership());
            outputView.writeReceipt(user);
            answer = readRetry();
        } while (answer.equals(Answer.YES));
    }

    private void checkPromotion(String name, int quantity, Products products) {
        if (products.isPromotionProduct(name) && products.useOnlyPromotion(name, quantity)) {
            if (products.checkOneMorePromotion(name, quantity, user) && readAnswer(name).equals(Answer.YES)) {
                products.subtractOneMore(name);
                user.addOneMore(name);
            }
            return;
        }
        if (products.isPromotionProduct(name) && !products.useOnlyPromotion(name, quantity)) {
            int remainQuantity = products.subtractAllQuantity(name, quantity, user);
            if (readRemainQuantity(name, remainQuantity).equals(Answer.YES)) {
                int remainPromotion = products.findPromotionQuantity(name);
                products.subtractPromotionProduct(name, remainPromotion);
                products.subtractGeneralProduct(name, remainQuantity - remainPromotion);
                user.addRemainProduct(products.findGeneralProduct(name), remainQuantity);
            }
            return;
        }
        products.subtractGeneralProduct(name, quantity);
        user.addRemainProduct(products.findGeneralProduct(name), quantity);
    }

    private Answer readMembership() {
        while (true) {
            try {
                return inputView.readMembership();
            } catch (CustomException customException) {
                outputView.writeErrorMessage(customException.getMessage());
            }
        }
    }

    private Answer readRemainQuantity(String name, int quantity) {
        while (true) {
            try {
                return inputView.readNoDiscount(name, quantity);
            } catch (CustomException customException) {
                outputView.writeErrorMessage(customException.getMessage());
            }
        }
    }

    private Answer readAnswer(String name) {
        while (true) {
            try {
                return inputView.readAnswer(name);
            } catch (CustomException customException) {
                outputView.writeErrorMessage(customException.getMessage());
            }
        }
    }

    private Answer readRetry() {
        while (true) {
            try {
                user.initUser();
                return inputView.readRetry();
            } catch (CustomException customException) {
                outputView.writeErrorMessage(customException.getMessage());
            }
        }
    }

    private List<String> readPurchaseItems(Products products) {
        while (true) {
            try {
                return inputView.readPurchase(products);
            } catch (CustomException customException) {
                outputView.writeErrorMessage(customException.getMessage());
            }
        }
    }

}
