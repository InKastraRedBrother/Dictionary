import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Class Dictonary provides 4 methods which are showAll(); search(); add(); deleteEntry().
 */
class Dictionary {

    Scanner in = new Scanner(System.in, StandardCharsets.UTF_8);

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

    /** Needed for working with methods which requires type File
     */
    private final File file;

    /**
     * Mask to restrict character input
     */
    private final String pattern;
    Console console = System.console();
    ComWithConsole cwc = new ComWithConsole();

    PrintStream printStream = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    /** Initialization block that check existence of the folder "resources"
     * If not then it will be created
     */
    {
        File folder = new File(pathToDictionary);
        if(!folder.exists()) {
            try {
                if (folder.mkdir()) {
                    System.out.println("Directory created");
                } else {
                    System.out.println("Directory is not created");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Initializes a newly created Dictionary object. Creates a file based on the passed parameter
     * @param fileName
     * @param pattern
     */
    Dictionary(String fileName, String pattern) throws IOException {
        this.fileName = fileName + FILE_FORMAT;
        this.pattern = pattern;
        file = new File(pathToDictionary + this.fileName);
        if (!file.exists()){
            file.createNewFile();
        }
    }

    /** Show text to console from the file which specified in the constructor
     * @throws IOException
     */
    public void showAll() throws IOException {
        BufferedReader brList = new BufferedReader(new FileReader(pathToDictionary + File.separator + fileName, StandardCharsets.UTF_8));
        String lineList;
        while ((lineList = brList.readLine()) != null) {
            printStream.println(lineList);
        }
        try {
            brList.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Search line which specified in console input from file
     * @throws IOException
     */
    public void search() throws IOException {
        String match = null;

        BufferedReader brSearch = new BufferedReader(new FileReader(pathToDictionary + File.separator + fileName, StandardCharsets.UTF_8));

        CommunicateMessage.inputKey();
        String s = cwc.inputInConsole();
        String line;
        while ((line = brSearch.readLine()) != null) {
            if (line.contains(s + ":")) {
                match = line;
            }

        }
        if (match != null) {
            System.out.println("Строка с запрощенным ключём " + s + " найдена - " + match);
        } else {
            System.out.println("Строка с запрощенным ключём " + s + " НЕ найдена");
        }
    }

    /**
     * Add line which is entered into the console and matches pattern to the end of the file
     * @throws IOException
     */
    public void add() throws IOException {

        FileWriter writer = new FileWriter(pathToDictionary + fileName, StandardCharsets.UTF_8, true);


        CommunicateMessage.inputKey();
        String key = cwc.inputInConsole();
        CommunicateMessage.inputValue();
        String value = cwc.inputInConsole();

        try {
            if (key.matches(pattern)) {
                writer.write("\n" + key + ":" + value);
                System.out.println("Запись: ключ - " + key + " значение - " + value + " добавлена");
            } else {
                CommunicateMessage.printErrMask();
            }
        } catch (Exception e) {
            CommunicateMessage.printErrMask();
        }

        writer.close();
    }

    /**
     * Create temporary file. Write to temporary file every line, excluding line that need to be deleted.
     * Deleting main file. Temporary file will be renamed to the name of the main file
     * @throws IOException
     */

    public void deleteEntry() throws IOException {
        File temporaryFile = new File(pathToDictionary + TEMPORARY_FILE_NAME);
        temporaryFile.delete();
        temporaryFile.createNewFile();

        CommunicateMessage.inputKey();
        String s = cwc.inputInConsole();
        if (s.matches(pattern)) {
            FileWriter bw = new FileWriter(temporaryFile, StandardCharsets.UTF_8, true);
            BufferedReader br = new BufferedReader(new FileReader(pathToDictionary + File.separator + fileName, StandardCharsets.UTF_8));
            String line;
            int counter = 0;
            boolean isExist = false;
            while ((line = br.readLine()) != null) {
                if (line.contains(s)){
                    isExist = true;
                }

                if (!line.contains(s) && !line.isBlank()) {

                    if (counter == 0) {
                        bw.write(line);
                    } else {
                        bw.write(System.lineSeparator());
                        bw.write(line);
                    }
                    counter++;
                }
            }
            if (isExist){
                CommunicateMessage.printDeleteEntry(s);
            } else {
                CommunicateMessage.printErrKeyNotFound();
            }

            br.close();
            bw.close();

            file.delete();
            temporaryFile.renameTo(new File(pathToDictionary + fileName));
        } else {
            System.out.println(ERR_MASK);
        }

    }
}

