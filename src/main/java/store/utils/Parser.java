package store.utils;

import java.util.ArrayList;
import java.util.List;
import store.domain.Products;
import store.exception.CustomException;
import store.exception.ErrorMessage;

public class Parser {
    private final static String COMMA = ",";
    private final static String HYPHEN = "-";

    public static List<String> checkSquareBracket(String input) {
        List<String> inputValues = List.of(input.split(COMMA));
        List<String> values = new ArrayList<>();
        for (String value : inputValues) {
            if (!(value.startsWith("[") && value.charAt(value.length() - 1) == ']')) {
                throw CustomException.from(ErrorMessage.FORMAT_ERROR);
            }
            values.add(value.substring(1, value.length() - 1));
        }
        return values;
    }

    public static void checkHyphen(List<String> values, Products products) {
        for (String value : values) {
            List<String> nameAndQuantity = List.of(value.split(HYPHEN));
            String name = nameAndQuantity.get(0);
            int quantity = Integer.parseInt(nameAndQuantity.get(1));
            products.checkQuantity(name, quantity);
        }
    }
}
