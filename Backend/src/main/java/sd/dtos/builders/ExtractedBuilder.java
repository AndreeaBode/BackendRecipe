package sd.dtos.builders;

import sd.dtos.ExtractedDTO;
import sd.dtos.ExtractedRecipeDTO;
import sd.entities.ExtractedRecipe;

public class ExtractedBuilder {

    public static ExtractedDTO buildDTO(ExtractedRecipe extractedRecipe) {
        return new ExtractedDTO(
                extractedRecipe.getId(),
                extractedRecipe.getTitle(),
                extractedRecipe.getImage(),
                extractedRecipe.getIngredients(),
                extractedRecipe.getSteps()
        );
    }
}
