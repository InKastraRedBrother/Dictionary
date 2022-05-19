package Dictionary;

import java.io.File;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class Dictionary.Dictionary provides 4 methods which are showAll(); search(); add(); deleteEntry().
 */
class Dictionary {

    IOStream ioStream;
    MaskVerification maskVerification = new MaskVerification();
    public static final String FILE_FORMAT = ".txt";
    public static final String TEMPORARY_FILE_NAME = "temp" + FILE_FORMAT;

    /** Message for error: the entered value does not meet the specified limits
     */
    public static final String ERR_MASK="Введенные ключ или значение не соотвестсвуют ограничениям";

    /** Full path to .txt files
     */
    private final String pathToDictionary = System.getProperty("user.dir") + File.separator + "resources" + File.separator;

    /** Contains file name
     */
    private final String fileName;

    /**
     * Mask to restrict character input
     */
    private final String pattern;
    CommunicationWithConsole communicationWithConsole = new CommunicationWithConsole();

    /**
     *  Initializes a newly created Dictionary.Dictionary object. Creates a file based on the passed parameter
     * @param fileName fileName
     * @param pattern pattern
     */
    Dictionary(String fileName, String pattern, IOStream ioStream)  {
        this.fileName = fileName + FILE_FORMAT;
        this.pattern = pattern;
        this.ioStream = ioStream;
        ioStream.createFile(this.fileName);
    }

    /** Show text to console from the file which specified in the constructor
     */
    public void showAll(){

        BufferedReader br = ioStream.getBufferedReader(fileName);
        String lineList;
        StringBuilder sf = new StringBuilder();
        while (true) {
            try {
                if ((lineList = br.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sf.append(lineList).append("\n");
        }
        System.out.println(sf);

        ioStream.closeBufferedReader();
    }

    /**
     * Search line which specified in console input from file
     */
    public void search() {

        String match = null;
        BufferedReader br = ioStream.getBufferedReader(fileName);

        communicationWithConsole.inputKey();
        String s = communicationWithConsole.inputInConsole();
        String line;
//        while ((line = br.readLine()) != null) {
//            if (line.contains(s + ":")) {
//                match = line;
//            }
//        }
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (line.contains(s + ":")) {
                match = line;
            }
        }

        if (match != null) {
            System.out.println("Строка с запрощенным ключём " + s + " найдена - " + match);
        } else {
            System.out.println("Строка с запрощенным ключём " + s + " НЕ найдена");
        }

        ioStream.closeBufferedReader();

    }

    /**
     * Add line which is entered into the console and matches pattern to the end of the file
     */
    public void add() {

        FileWriter writer = ioStream.getFileWriter(fileName);

        communicationWithConsole.inputKey();
        String key = communicationWithConsole.inputInConsole();
        communicationWithConsole.inputValue();
        String value = communicationWithConsole.inputInConsole();

        try {
            if(maskVerification.checkString(key, pattern)){
                writer.write("\n" + key + ":" + value);
                System.out.println("Запись: ключ - " + key + " значение - " + value + " добавлена");
            } else {
                communicationWithConsole.printErrMask();
            }
        } catch (Exception e) {
            communicationWithConsole.printErrMask();
        }
        ioStream.closeFileWriter();
    }

    /**
     * Create temporary file. Write to temporary file every line, excluding line that need to be deleted.
     * Deleting main file. Temporary file will be renamed to the name of the main file
     */

    public void deleteEntry() {
        File tempFile = ioStream.createFile(TEMPORARY_FILE_NAME);
        File file = new File(pathToDictionary + fileName);

        communicationWithConsole.inputKey();
        String s = communicationWithConsole.inputInConsole();
        if (s.matches(pattern)) {

            FileWriter fileWriter =  ioStream.getFileWriter(TEMPORARY_FILE_NAME);
            BufferedReader bufferedReader = ioStream.getBufferedReader(fileName);

            String line;
            int counter = 0;
            boolean isExist = false;
            while (true) {
                try {
                    if ((line = bufferedReader.readLine()) == null) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (line.contains(s)){
                    isExist = true;
                }
                if (!line.contains(s) && !line.isBlank()) {
                    try {
                        if (counter == 0) {
                                fileWriter.write(line);
                        } else {
                            fileWriter.write(System.lineSeparator());
                            fileWriter.write(line);
                        }
                    } catch (IOException e) {
                            throw new RuntimeException(e);
                    }
                    counter++;
                }
            }
            if (isExist){
                communicationWithConsole.printDeleteEntry(s);
            } else {
                communicationWithConsole.printErrKeyNotFound();
            }

            ioStream.closeBufferedReader();
            ioStream.closeFileWriter();

            file.delete();
            tempFile.renameTo(new File(pathToDictionary + fileName));

        } else {
            System.out.println(ERR_MASK);
        }

    }
}

