package ru.dictionary.dao;

import ru.dictionary.exception.DictionaryNotFoundException;
import ru.dictionary.model.Word;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class WordDAO {

//    public List<Word> findAll(String fileName) {
//        List<Word> listWord = new ArrayList<>();
//        File file = new File(System.getProperty("user.dir") + "/qqq.txt");
//        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
//            while (sc.hasNextLine()) {
//                listWord.add(sc.nextLine());
//            }
//        } catch (NullPointerException | NoSuchElementException | IllegalStateException | IOException e) {
//            throw new DictionaryNotFoundException("findAll");
//        }
//        return listRow;
//    }
}
