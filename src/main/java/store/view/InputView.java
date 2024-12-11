package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import store.domain.Products;
import store.domain.constant.Answer;
import store.utils.Parser;

public class InputView {
    private final static String INPUT_PURCHASE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private final static String INPUT_ONE_MORE = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private final static String INPUT_NO_DISCOUNT = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    private final static String INPUT_MEMBERSHIP = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private final static String INPUT_RETRY = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    public Answer readRetry() {
        System.out.println(INPUT_RETRY);
        return Answer.from(readConsole());
    }

    public Answer readMembership() {
        System.out.println(INPUT_MEMBERSHIP);
        return Answer.from(readConsole());
    }

    public Answer readNoDiscount(String name, int quantity) {
        System.out.printf(INPUT_NO_DISCOUNT, name, quantity);
        return Answer.from(readConsole());
    }

    public Answer readAnswer(String name) {
        System.out.printf(INPUT_ONE_MORE, name);
        return Answer.from(readConsole());
    }

    public List<String> readPurchase(Products products) {
        System.out.println(INPUT_PURCHASE);
        String purchaseProducts = readConsole();
        List<String> deletedBracket = Parser.checkSquareBracket(purchaseProducts);
        Parser.checkHyphen(deletedBracket, products);
        return deletedBracket;
    }

    public void closeConsole() {
        Console.close();
    }

    private String readConsole() {
        return Console.readLine();
    }
}
