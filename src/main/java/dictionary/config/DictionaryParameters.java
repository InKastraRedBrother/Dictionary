package dictionary.config;

/**
 * Create object with initialized mask and storage's file name
 */
public class DictionaryParameters {

    String fileName;
    String mask;

    public DictionaryParameters(String fileName, String mask) {
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
