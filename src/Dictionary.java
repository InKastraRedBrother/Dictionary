import java.io.*;

/**
 * Class Dictonary provides 4 methods which are showAll(); search(); add(); deleteEntry().
 */
class Dictionary {

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

    /**
     *  Сharacter input limit for a key
     */
    private final int limit;

    /** Initialization block that check existence of the folder "resources"
     * If not then it will be created
     */
    {
        File folder = new File(pathToDictionary);
        if(!folder.exists()) {
            try {
                if (folder.mkdir()) {
                    System.out.println("Directory Created");
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
     * @param limit
     */
    Dictionary(String fileName, String pattern, int limit) throws IOException {
        this.fileName = fileName;
        this.pattern = pattern;
        this.limit = limit;
        file = new File(pathToDictionary + fileName);
        if (!file.exists()){
            file.createNewFile();
        }
    }

    /** Show text to console from the file which specified in the constructor
     * @throws IOException
     */
    public void showAll() throws IOException {
        BufferedReader brList = new BufferedReader(new FileReader(pathToDictionary + File.separator + fileName));
        String lineList;
        while ((lineList = brList.readLine()) != null) {
            System.out.println(lineList);
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
        BufferedReader brSearch = new BufferedReader(new FileReader(pathToDictionary + File.separator + fileName));

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

        FileWriter writer = new FileWriter(pathToDictionary + fileName, true);
        BufferedWriter bw = new BufferedWriter(writer);

        BufferedReader brIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите формата: ключ:значение ");

        String[] end = brIn.readLine().split(":", 2);

        try {
            String key = end[0];
            String value  = end[1];
            if (key.matches(pattern) && key.length() == limit ) {
                bw.write("\n" + key + ":" + value);
                System.out.println("Запись: ключ - " + key + " значение - " + value + " добавлена");
            } else {
                System.out.println("Введенные ключ или значение не соотвестсвуют ограничениям");
            }
        } catch (Exception e) {
            System.out.println("Введенные ключ или значение не соотвестсвуют ограничениям");
        }

        bw.close();
        writer.close();
    }

    /**
     * Create temporary file. Write to temporary file every line, excluding line that need to be deleted.
     * Deleting main file. Temporary file will be renamed to the name of the main file
     * @throws IOException
     */
    public void deleteEntry() throws IOException {
        File temporaryFile = new File(pathToDictionary + "temp.txt");

        temporaryFile.delete();
        temporaryFile.createNewFile();

        BufferedReader brInForDelete = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите формата: ключ ");
        String s1 = brInForDelete.readLine();
        if (s1.length() == limit && s1.matches(pattern)) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(temporaryFile));
            BufferedReader br = new BufferedReader(new FileReader(pathToDictionary + File.separator + fileName));
            String line; int counter = 0;

            while ((line = br.readLine()) != null) {

                if (!line.contains(s1) && !line.isBlank()) {

                    if (counter == 0){
                        bw.write(line);
                    }   else {
                        bw.newLine();
                        bw.write(line);
                    }
                    counter++;
                } else {
                    System.out.println("Строка с ключом " + s1 + " удалена"); //проблема дублирование вывода при пустой строке между записями
                }

            }
            br.close();
            bw.close();

            file.delete();
            temporaryFile.renameTo(new File(pathToDictionary + fileName));
        }
        else {
            System.out.println("Превышен лимит длины ключа или формат ключа");
        }



    }


}

