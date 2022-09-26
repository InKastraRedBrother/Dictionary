package ru.dictionary.conrtoller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.dictionary.model.Language;
import ru.dictionary.model.SuccessMessage;
import ru.dictionary.model.dto.BuiltRow;
import ru.dictionary.model.dto.RequestAddPairWordsDTO;
import ru.dictionary.service.ServiceLanguage;
import ru.dictionary.service.ServiceRow;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {
    ServiceRow serviceRow;
    ServiceLanguage serviceLanguage;

    @GetMapping("/main")
    public String sayHello() {
        return "main";
    }

//    @GetMapping("/view-rows/{id}") //rest
//    public List<Row> showAllRows(@PathVariable("id") String id) {
//                model.addAttribute("listRow", listRows);
//        model.addAttribute("id", id);
//        return service.findAllRows(dictionaryConfiguration.getSelectedDictionary(id));
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
                                              @RequestParam(name = "dropDownListSourceLanguage", required = false) String languageSourceUUID,
                                              @RequestParam(name = "dropDownListTargetLanguage", required = false) String languageTargetUUID,
                                              Model model) {

        List<Language> listLanguage = serviceLanguage.findAllLanguages();
        model.addAttribute("listLanguage", listLanguage);

        List<BuiltRow> listBuiltRows = serviceRow.findAllBySelectedLanguageUUID(languageSourceUUID, languageTargetUUID);
        model.addAttribute("listBuiltRows", listBuiltRows);
        return "view-rows";
    }

    @GetMapping("/view-rows/search/result")
    public String home(@ModelAttribute BuiltRow builtRow, Model model,
                       @RequestParam(name = "wordValue") String wordValue) {
        List<BuiltRow> listBuiltRows = serviceRow.findRowsByWordValue(wordValue);
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
    public String saveRow(@ModelAttribute RequestAddPairWordsDTO requestAddPairWordsDTO, RedirectAttributes redirectAttributes) {
        SuccessMessage successMessage = serviceRow.addPair(requestAddPairWordsDTO);
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        if (successMessage.isSuccessful()) {
            return "redirect:/view-rows";
        } else {
            return "redirect:/add-row";
        }
    }

    @PostMapping("/delete-row/{rowUUID}")
    public String deleteRow(@PathVariable("rowUUID") String rowUUID) {
        serviceRow.deleteRowByKey(rowUUID);
        return "redirect:/view-rows";
    }
}
