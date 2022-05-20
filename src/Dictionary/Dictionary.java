package Dictionary;

import java.io.File;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class Dictionary.Dictionary provides 4 methods which are showAll(); search(); add(); deleteEntry().
 */
class Dictionary {
    private static final String KEY_VALUE_ADDED = "Запись: ключ:значение добавлена - ";
    private static final String KEY_VALUE_SEPARATOR = ":";
    private final static String FIND_ROW = "Строка с запрощенным ключём найдена - ";
    private final static String NOT_FIND_ROW = "Строка с запрощенным ключём НЕ найдена - ";

    /** Name of temporary file
     */
    public static final String TEMPORARY_FILE_NAME = "temp";

    /** Contains file name
     */
    private final String fileName;

    /**
     * Mask to restrict character input
     */
    private final String pattern;

    IOStream ioStream;
    CommunicationWithConsole communicationWithConsole;
    /**
     *  Initializes a newly created Dictionary.Dictionary object. Creates a file based on the passed parameter
     * @param fileName fileName
     * @param pattern pattern
     */
    Dictionary(String fileName, String pattern, IOStream ioStream, CommunicationWithConsole communicationWithConsole)  {
        this.fileName = fileName;
        this.pattern = pattern;
        this.ioStream = ioStream;
        ioStream.createFile(this.fileName);
        this.communicationWithConsole = communicationWithConsole;
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
        String key = communicationWithConsole.consoleChooser();
        String line;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (line.contains(key + KEY_VALUE_SEPARATOR)) {
                match = line;
            }
        }

        if (match != null) {
            System.out.println(FIND_ROW + key + " " + match);
        } else {
            System.out.println(NOT_FIND_ROW + key);
        }

        ioStream.closeBufferedReader();

    }

    /**
     * Add line which is entered into the console and matches pattern to the end of the file
     */
    public void add() {

        FileWriter writer = ioStream.getFileWriter(fileName);

        communicationWithConsole.inputKey();
        String key = communicationWithConsole.consoleChooser();
        communicationWithConsole.inputValue();
        String value = communicationWithConsole.consoleChooser();

        try {
            if(key.matches(pattern)){
                writer.write("\n" + key + KEY_VALUE_SEPARATOR + value);
                System.out.println(KEY_VALUE_ADDED + key + KEY_VALUE_SEPARATOR + value);
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
        File file = ioStream.createFile(fileName);

        communicationWithConsole.inputKey();
        String s = communicationWithConsole.consoleChooser();
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
            tempFile.renameTo(new File(ioStream.pathToDictionary + fileName + ioStream.FILE_FORMAT));

        } else {
            communicationWithConsole.printErrMask();
        }

    }
}

