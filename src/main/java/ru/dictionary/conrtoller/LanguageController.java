package ru.dictionary.conrtoller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.dictionary.model.Language;
import ru.dictionary.service.ServiceLanguage;

import java.util.List;

@Controller
@RequestMapping("/language")
@AllArgsConstructor
public class LanguageController {

    private final ServiceLanguage serviceLanguage;

    @GetMapping("/view-all")
    public String showAllLanguages(Model model) {
        List<Language> listLanguages = (serviceLanguage.getAll());
        model.addAttribute("listLanguages", listLanguages);
        return "language/view-all";
    }

    @GetMapping("/add")
    public String showAddLanguage(Language language, Model model) { //TODO в чём разница если не кидать сюда Language в передаваемые и в model явно new Language() и этот вариант. Работают оба
        model.addAttribute("language", language); //без этого ошибка таймлифа при отображении страницы add-language
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
