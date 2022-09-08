package ru.dictionary.conrtoller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String addLanguage(@ModelAttribute("language") Language language) {
        serviceLanguage.addLanguage(language);
        return "redirect:/language/view-all";
    }

}
