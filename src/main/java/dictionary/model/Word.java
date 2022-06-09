package dictionary.model;

public class Word {
    String word;
    Language language;

    public Word(String word, Language language) {
        this.word = word;
        this.language = language;
    }
    public Word(String word) {
        this.word = word;
    }

    public Word(){
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

}
