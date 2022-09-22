package ru.dictionary.conrtoller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.dictionary.model.Language;
import ru.dictionary.service.ServiceLanguage;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/language")
@AllArgsConstructor
public class LanguageController {

    private final ServiceLanguage serviceLanguage;

    @GetMapping("/view-all")
    public String showAllLanguages(Model model) {
        List<Language> listLanguages = (serviceLanguage.findAllLanguages());
        model.addAttribute("listLanguages", listLanguages);
        return "language/view-all";
    }

    @GetMapping("/add")
    public String showAddLanguage(Model model) {
        model.addAttribute("language", new Language()); //без этого ошибка таймлифа при отображении страницы add-language
        return "language/add";
    }

    @PostMapping("/add")
    public String addLanguage(@ModelAttribute(name = "language") Language language) {
        serviceLanguage.addLanguage(language);
        return "redirect:/language/view-all";
    }

    @PostMapping("/delete-language/{languageUUID}") //TODO мб сделать касадное удаление всех строк, где есть слова удаляемого языка?
    public String deleteLanguage(@PathVariable("languageUUID") String languageUUID){
        System.out.println(languageUUID);
        return "redirect:/language/view-all";
    }

}
