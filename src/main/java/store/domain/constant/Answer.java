package store.domain.constant;

import java.util.Arrays;
import store.exception.CustomException;
import store.exception.ErrorMessage;

public enum Answer {
    YES("Y"),
    NO("N");

    private final String answer;

    Answer(final String answer) {
        this.answer = answer;
    }

    public static Answer from(String input) {
        return Arrays.stream(Answer.values())
                .filter(value -> value.answer.equals(input))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.YES_OR_NO_ERROR));
    }
}
