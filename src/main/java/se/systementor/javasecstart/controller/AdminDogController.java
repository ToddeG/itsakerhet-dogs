package se.systementor.javasecstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.systementor.javasecstart.model.Dog;
import se.systementor.javasecstart.model.DogRepository;
import se.systementor.javasecstart.services.DogService;

@Controller
public class AdminDogController {
    @Autowired
    private DogService dogService;
    @Autowired
    private DogRepository dogRepository;

    @GetMapping(path="/admin/dogs")
    String list(Model model, @RequestParam(defaultValue = "name") String sortCol,
                @RequestParam(defaultValue = "ASC") String sortOrder,
                @RequestParam(defaultValue = "") String searchQuery){
        model.addAttribute("activeFunction", "home");
        if(!(sortOrder.equalsIgnoreCase("ASC") || sortOrder.equalsIgnoreCase("DESC"))){
            sortOrder = "ASC";
        }
        searchQuery = searchQuery.trim();
        model.addAttribute("searchQuery", searchQuery);
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortCol);
        if (!searchQuery.isEmpty()){
            model.addAttribute("dogs",
                    dogService.findAllByStringQuery(searchQuery, sort));
        } else {
            model.addAttribute("dogs", dogService.findAllSorted(sort));
        }
        //model.addAttribute("dogs", dogService.getPublicDogs());
        return "admin/dogs/list";
    }

    @RequestMapping("/admin/editByView/{id}")
    public String createByForm(@PathVariable int id, Model model) {
        Dog d = dogService.findDogById(id);
        model.addAttribute("dog", d);
        return "/admin/dogs/editDog";
    }

    @RequestMapping("/update")
    public String kundEdit(Model model, Dog d) {
        try {
            dogService.updateDog(d);
            model.addAttribute("successMessage", "Hunden är uppdaterad.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ett fel inträffade vid uppdateringen.");
        }
        return "/admin/dogs/editDog";
    }
}
