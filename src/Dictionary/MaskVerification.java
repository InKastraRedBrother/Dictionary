package Dictionary;

/**
 * Compare String to mask
 */
public class MaskVerification {

    /**
     * Compare String to mask
     * @param s - String which one will be compared
     * @param pattern - mask with witch string will be compared
     * @return true if String matches. else return false
     */
    public boolean checkString(String s, String pattern) {
        return s.matches(pattern);
    }

    public boolean checkDictionary(String s) {
        return (s.matches("^[1-2]{1}+$"));
    }
    public boolean checkOperation(String s) {
        return (s.matches("^[1-5]{1}+$"));
    }
    public boolean checkSymbolKey(String s){
        return (s.matches("^[a-z]{4}+$"));
    }
    public boolean checkNumberKey(String s){
        return (s.matches("^[0-9]{5}+$"));
    }

}
