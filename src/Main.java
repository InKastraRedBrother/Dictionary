import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

class Main {


    public static void main(String[] args) throws IOException {



        String pathToDictionary = System.getProperty("user.dir") + File.separator + "resources" + File.separator;
        String fileName = null;
        File file = null;
        String pattern = null;
        int limit;

        while (true){
            System.out.print("Choose dictionary type: 1 - Symbolic; 2 - Numeric : ");
            BufferedReader brInputForType = new BufferedReader(new InputStreamReader(System.in));
            String sInput = brInputForType.readLine();

            switch (sInput){
                case ("1"): {
                    fileName = "Sym.txt";
                    file = new File(pathToDictionary + fileName);
                    pattern = "^[a-z]+$";
                    limit = 4;
                    break;
                }
                case ("2"): {
                    fileName = "Nym.txt";
                    file = new File(pathToDictionary + fileName);
                    pattern = "^[0-9]+$";
                    limit = 5;
                    break;
                }
                default:
                    System.out.println("Недопустимая команда");
                    return;
            }

                if (!file.exists()) {
                    file.createNewFile();
                }

                System.out.print("Choose dictionary's destiny: 1 - Search; 2 - Show whole; 3 - Add to the end; 4 - Delete : ");
                BufferedReader brInputForOperation = new BufferedReader(new InputStreamReader(System.in));
                String sInputForOperation = brInputForOperation.readLine();
               switch (sInputForOperation) {
                    case ("1"): {
                        /*--------------------------------------------------------------------------------------------------------------------------------------------------*/
                        /*---------------------------------------------------------ПОИСК ПО КЛЮЧУ------------------------------------------------------------------------*/
                        /*--------------------------------------------------------------------------------------------------------------------------------------------------*/
                        try {
                            String match = null;

                            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                            BufferedReader brSearch = new BufferedReader(new FileReader(pathToDictionary + fileName));

                            System.out.println("Введите формата: ключ ");
                            String s = br.readLine();
                            String line;
                            while ((line = brSearch.readLine()) != null) {
                                if (line.contains(s + ":")) {
                                    match = line;
                                }

                            }
                            if (match != null) {
                                System.out.println("Строка с запрощенным ключём \"" + s + "\" найдена - " + match);
                            } else {
                                System.out.println("Строка с запрощенным ключём \"" + s + "\" НЕ найдена");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                    case ("2"): {
                        /*--------------------------------------------------------------------------------------------------------------------------------------------------*/
                        /*---------------------------------------------------------ВЫВОД ВСЕГО СЛОВАРЯ----------------------------------------------------------------------*/
                        /*--------------------------------------------------------------------------------------------------------------------------------------------------*/
                        try {
                            BufferedReader brList = null;
                            try {
                                brList = new BufferedReader(new FileReader(file));
                                String lineList;
                                while ((lineList = brList.readLine()) != null) {
                                    System.out.println(lineList);
                                }
                            } catch (IOException e) {
                            } finally {
                                try {
                                    brList.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        break;
                    }
                    case ("3"): {
                        /*-------------------------------------------------------------------------------------------------------------------------------------------------*/
                        /*----------------------------------------------------ДОБАВЛЕНИЕ 1 ЗАПИСИ В КОНЕЦ СЛОВАРЯ----------------------------------------------------------*/
                        /*-------------------------------------------------------------------------------------------------------------------------------------------------*/
                        try {
                            String inputString;
                            FileWriter writer = new FileWriter(file, true);
                            BufferedWriter bw = new BufferedWriter(writer);

                            BufferedReader brIn = new BufferedReader(new InputStreamReader(System.in));
                            System.out.println("Введите формата: ключ:значение ");

                            String[] end = brIn.readLine().split(":", 2);
                            String key = end[0];
                            String value  = end[1];

                            if (key.matches(pattern) && key.length() == limit ) {
                                bw.write("\n" + key + ":" + value);
                            }
                            else {
                                System.out.println("Введенные ключ или значение не соотвестсвуют ограничениям");
                                break;
                            }
                                bw.close();
                                writer.close();


                            //brIn.close(); //ошибка дальше по коду, если закрыть System.in перед последним использование
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                        break;
                    }
                    case ("4"): {
                        /*--------------------------------------------------------------------------------------------------------------------------------------------------*/
                        /*---------------------------------------------------------УДАЛЕНИЕ ПО КЛЮЧУ------------------------------------------------------------------------*/
                        /*--------------------------------------------------------------------------------------------------------------------------------------------------*/
                        try {
                            File temporaryFile = new File(pathToDictionary + "temp.txt");

                            if (temporaryFile.delete()) {
                                System.out.println("File was existing, but not anymore");
                            }

                            if (temporaryFile.createNewFile()) {
                                System.out.println("File is created!");
                            }

                            BufferedReader br = null;
                            BufferedReader brInForDelete = new BufferedReader(new InputStreamReader(System.in));
                            System.out.println("Введите формата: ключ ");
                            String s1 = brInForDelete.readLine();
                            if (s1.length() == limit) {
                                BufferedWriter bw = new BufferedWriter(new FileWriter(temporaryFile));
                                br = new BufferedReader(new FileReader(file));
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
                                temporaryFile.renameTo(file);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    default:
                        System.out.println("Недопустимая команда");
                }
            }

        }
    }




