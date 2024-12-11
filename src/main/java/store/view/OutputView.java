package store.view;

import store.domain.Products;
import store.domain.User;

public class OutputView {
    private final static String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.";
    private final static String NEW_LINE = System.lineSeparator();

    public void writeWelcome(Products products) {
        System.out.println(WELCOME_MESSAGE);
        System.out.print(NEW_LINE);
        System.out.println(products.toString());
    }

    public void writeErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void writeReceipt(User user) {
        System.out.println(user.toString());
    }
}
