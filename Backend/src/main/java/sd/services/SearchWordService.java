package sd.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.dtos.ExtractedRecipeDTO;
import sd.entities.ExtractedRecipe;
import sd.repositories.ExtractedRecipeRepository;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SearchWordService {

    @Autowired
    private ExtractedRecipeRepository extractedRecipeRepository;

    public List<ExtractedRecipeDTO> searchByDishType(String dishType) {
        List<ExtractedRecipe> recipes = extractedRecipeRepository.findByDishTypesContaining(dishType);
        return recipes.stream()
                .map(recipe -> new ExtractedRecipeDTO(recipe.getId(), recipe.getTitle(), recipe.getImage()))
                .collect(Collectors.toList());
    }

    public List<ExtractedRecipeDTO> searchByDiet(String diet) {
        List<ExtractedRecipe> recipes = extractedRecipeRepository.findByDietsContaining(diet);
        return recipes.stream()
                .map(recipe -> new ExtractedRecipeDTO(recipe.getId(), recipe.getTitle(), recipe.getImage()))
                .collect(Collectors.toList());
    }
}