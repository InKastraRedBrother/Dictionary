package Dictionary;

/**
 * Compare String to mask
 */
public class MaskVerification {

    /**
     * Compare String to mask
     * @param s - Strig which one will be compared
     * @param pattern - mask with witch string will be compared
     * @return true if String matches. else return false
     */
    public boolean checkString(String s, String pattern) {
        return s.matches(pattern);
    }
}
