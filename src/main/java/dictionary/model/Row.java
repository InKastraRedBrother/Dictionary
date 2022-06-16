package dictionary.model;

/**
 * Model of row in application
 */
public class Row {
    private static final String KEY_VALUE_SEPARATOR = " ";
    Word key;
    Word value;

    public Row(Word key, Word value) {
        this.key = key;
        this.value = value;
    }
    public Row(){

    }

    public Word getKey() {
        return key;
    }

    public void setKey(Word key) {
        this.key = key;
    }

    public Word getValue() {
        return value;
    }

    public void setValue(Word value) {
        this.value = value;
    }
    @Override
    public String toString(){
        return this.key.getWord() + KEY_VALUE_SEPARATOR + this.value.getWord();
    }
}
