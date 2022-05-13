import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Class Dictonary provides 4 methods which are showAll(); search(); add(); deleteEntry().
 */
class Dictionary {

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

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader brSearch = new BufferedReader(new FileReader(pathToDictionary + File.separator + fileName, StandardCharsets.UTF_8));

        System.out.println("Введите формата: ключ ");
        String s = br.readLine();
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


//        Scanner in = new Scanner(System.in);
//        String key = in.nextLine().strip();
//        String value = in.nextLine();

        Console console = System.console();
        System.out.println("Введите ключ");
        String key = console.readLine();
        System.out.println("Введите значение");
        String value = console.readLine();

        try {
            if (key.matches(pattern)) {
                writer.write("\n" + key + ":" + value);
                System.out.println("Запись: ключ - " + key + " значение - " + value + " добавлена");
            } else {
                System.out.println(ERR_MASK);
            }
        } catch (Exception e) {
            System.out.println(ERR_MASK);
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

        BufferedReader brInForDelete = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input key: ");
        String s1 = brInForDelete.readLine();
        if (s1.matches(pattern)) {
            FileWriter bw = new FileWriter(temporaryFile, StandardCharsets.UTF_8, true);
            BufferedReader br = new BufferedReader(new FileReader(pathToDictionary + File.separator + fileName));
            String line;
            int counter = 0;

            while ((line = br.readLine()) != null) {

                if (!line.contains(s1) && !line.isBlank()) {

                    if (counter == 0) {
                        bw.write(line);
                    } else {
                        bw.write(System.lineSeparator());
                        bw.write(line);
                    }
                    counter++;
                } else {
                    System.out.println("String with key " + s1 + " has been deleted"); //проблема дублирование вывода при пустой строке между записями
                }

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

