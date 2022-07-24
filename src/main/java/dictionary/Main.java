package dictionary;

import dictionary.config.AnnotationConfig;
import dictionary.view.ViewDictionary;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class Main {
    /**
     * run console application
     *
     * @param args null
     */
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AnnotationConfig.class);

        ViewDictionary viewDictionary = context.getBean(ViewDictionary.class);
        viewDictionary.runApp();
    }
}




