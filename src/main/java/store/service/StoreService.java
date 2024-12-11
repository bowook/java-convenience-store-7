package store.service;

import java.io.IOException;
import java.util.List;
import store.exception.ErrorMessage;
import store.utils.FileLoader;

public class StoreService {

    public StoreService() {
        List<String> products = loadFile("products.md");
        List<String> promotions = loadFile("promotions.md");
    }

    private List<String> loadFile(String fileName) {
        try {
            return FileLoader.fileReadLine(fileName);
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.NO_FILE.getMessage());
        }
    }
}
