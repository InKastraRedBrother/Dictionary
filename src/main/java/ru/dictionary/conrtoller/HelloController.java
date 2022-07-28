package ru.dictionary.conrtoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.dictionary.config.DictionaryConfiguration;
import ru.dictionary.model.Row;
import ru.dictionary.service.Service;

import java.util.List;

@Controller
public class HelloController {

    @Autowired
    Service service;

    @GetMapping("/main")
    public String sayHello(Model model) {
        model.addAttribute("nameDictionary");
        DictionaryConfiguration dc = new DictionaryConfiguration();
        return "main";
    }

    @GetMapping("/view_rows")
    public String showAllRows(Model model, @RequestParam("id") String id) {
        List<Row> listRows = service.findAllRows(new DictionaryConfiguration().getSelectedDictionary(id));
        model.addAttribute("listRow", listRows);
        model.addAttribute("id", id);
        return "view_rows";
    }

    @GetMapping("/add_row")
    public String showsaveRowPage(Model model, @RequestParam("id") String id) {
        model.addAttribute("row", new Row());
        model.addAttribute("id", id);
        return "add_row";
    }

    @PostMapping("/add_row")
    public String saveRow(@ModelAttribute Row row, @RequestParam("id") String id) {
        service.addRow(row, new DictionaryConfiguration().getSelectedDictionary(id));
        return "redirect:/view_rows?id=" + id;
    }

    @GetMapping("/delete_row")
    public String showDeleteRowPage(Model model, @RequestParam("id") String id) {
        model.addAttribute("row", new Row());
        model.addAttribute("id", id);
        return "delete_row";
    }

    @PostMapping("/delete_row")
    public String deleteRow(@ModelAttribute Row row, @RequestParam("id") String id) {
        service.deleteRowByKey(row.getKey(), new DictionaryConfiguration().getSelectedDictionary(id));
        return "redirect:/view_rows?id=" + id;
    }

    @GetMapping("/search_row")
    public String showSearchRowPage(Model model,@RequestParam(name = "id") String id) {
        model.addAttribute("row", new Row());
        model.addAttribute("id", id);
        return "search_row";
    }

    @GetMapping("/search_row/result")
    public String search(@ModelAttribute Row row, @RequestParam(name = "id") String id, Model model) {
        model.addAttribute("row", service.findRowByKey(row.getKey(), new DictionaryConfiguration().getSelectedDictionary(id)));
        model.addAttribute("id", id);
        return "search_row";
    }
}
