package sd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sd.dtos.ExtractedRecipeDTO;
import sd.services.SearchWordService;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class SearchWordController {

    @Autowired
    private SearchWordService searchWordService;

    @GetMapping("/searchByDishType")
    public List<ExtractedRecipeDTO> searchRecipesByDishType(@RequestParam String dishType) {
        return searchWordService.searchByDishType(dishType);
    }

    @GetMapping("/searchByDiet")
    public List<ExtractedRecipeDTO> searchRecipesByDiet(@RequestParam String diet) {
        return searchWordService.searchByDiet(diet);
    }
}
