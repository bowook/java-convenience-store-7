package store.exception;

public enum ErrorMessage {

    OVER_COUNT_ERROR("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    YES_OR_NO_ERROR("잘못된 입력입니다. 다시 입력해 주세요."),
    NO_FILE("파일이 존재하지 않습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}