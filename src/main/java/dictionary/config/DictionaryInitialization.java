package dictionary.config;

public class DictionaryInitialization {

    String fileName;
    String mask;

    public DictionaryInitialization(String fileName, String mask) {
        this.fileName = fileName;
        this.mask = mask;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMask() {
        return mask;
    }

}
