package ru.dictionary.conrtoller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.dictionary.config.DictionaryConfiguration;
import ru.dictionary.model.Language;
import ru.dictionary.model.Row;
import ru.dictionary.model.dto.BuiltRow;
import ru.dictionary.model.dto.DeleteForm;
import ru.dictionary.model.dto.RequestAddPairWordsDTO;
import ru.dictionary.service.ServiceLanguage;
import ru.dictionary.service.ServiceRow;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class MainController {
    ServiceRow serviceRow;
    ServiceLanguage serviceLanguage;
    DictionaryConfiguration dictionaryConfiguration;

    @GetMapping("/main")
    public String sayHello(Model model) {
        model.addAttribute("nameDictionary");
        return "main";
    }

//    @GetMapping("/view-rows/{id}") //rest
//    public List<Row> showAllRows(@PathVariable("id") String id) {
//                model.addAttribute("listRow", listRows);
//        model.addAttribute("id", id);
//        return service.findAllRows(dictionaryConfiguration.getSelectedDictionary(id));
//    }

//    @GetMapping("/view-rows") //dto
//    public String showAllRows(Model model, @RequestParam("id") String id) {
//        List<Row> listRows = service.findAllRows(new DictionaryConfiguration().getSelectedDictionary(id));
//        model.addAttribute(new WordsDTO(id, listRows));
//        return "view-rows";
//    }


    @GetMapping("/view-rows")
    public String showAllRows(@ModelAttribute BuiltRow builtRow, Model model) {
        List<Language> listLanguage = serviceLanguage.findAllLanguages();
        model.addAttribute("listLanguage", listLanguage);

        List<BuiltRow> listBuiltRows = serviceRow.findAllRows();
        model.addAttribute("listBuiltRows", listBuiltRows);

        return "view-rows";
    }

    @GetMapping(value = "/view-rows/result", params = {"dropDownListSourceLanguage", "dropDownListSourceLanguage"})
    public String showAllRowsBySelectedOption(@ModelAttribute BuiltRow builtRow,
                                              @RequestParam(name = "dropDownListSourceLanguage", required = false) UUID languageSourceUUID,
                                              @RequestParam(name = "dropDownListTargetLanguage", required = false) UUID languageTargetUUID,
                                              Model model) {

        List<Language> listLanguage = serviceLanguage.findAllLanguages();
        model.addAttribute("listLanguage", listLanguage);

        List<BuiltRow> listBuiltRows = serviceRow.findAllBySelectedLanguageUUID(languageSourceUUID, languageTargetUUID);
        model.addAttribute("listBuiltRows", listBuiltRows);
        return "view-rows";
    }

    @GetMapping("/add-row")
    public String showSaveRowPage(Model model) {
        List<Language> listLanguage = serviceLanguage.findAllLanguages();
        model.addAttribute("listLanguage", listLanguage);
        model.addAttribute("requestAddPairWordsDTO", new RequestAddPairWordsDTO());
        return "add-row";
    }

    @PostMapping("/add-row")
    public String saveRow(@ModelAttribute RequestAddPairWordsDTO requestAddPairWordsDTO) {
        serviceRow.addPair(requestAddPairWordsDTO);
        return "redirect:/view-rows";
    }

    @PostMapping("/delete-row/{rowUUID}")
    public String deleteRow(@PathVariable("rowUUID") UUID rowUUID) {
        serviceRow.deleteRowByKey(rowUUID);
        return "redirect:/view-rows";
    }
//  /\ DELETE/\

//    @PostMapping("/delete_row")
//    public String deleteRow(@ModelAttribute Row row, @RequestParam("id") String id) {
//        service.deleteRowByKey(row.getKey(), dictionaryConfiguration.getSelectedDictionary(id));
//        return "redirect:/view-rows?id=" + id;
//    }

    @GetMapping("/search_row")
    public String showSearchRowPage(Model model, @RequestParam(name = "id") String id) {
        model.addAttribute("row", new Row());
        model.addAttribute("id", id);
        return "search_row";
    }

//    @GetMapping("/search_row/result")
//    public String search(@ModelAttribute Row row, @RequestParam(name = "id") String id, Model model) {
//        model.addAttribute("row", service.findRowByKey(row.getKey(), dictionaryConfiguration.getSelectedDictionary(id)));
//        model.addAttribute("id", id);
//        return "search_row";
//    }
}
