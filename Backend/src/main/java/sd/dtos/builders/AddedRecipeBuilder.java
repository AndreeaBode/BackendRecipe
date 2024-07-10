package sd.dtos.builders;

import sd.dtos.AddedRecipeDTO;
import sd.dtos.ExtractedRecipeDTO;
import sd.entities.AddedRecipe;
import sd.entities.ExtractedRecipe;

public class AddedRecipeBuilder {
    public static AddedRecipeDTO buildDTO(AddedRecipe addedRecipe) {
        return new AddedRecipeDTO(addedRecipe.getId(), addedRecipe.getTitle(), addedRecipe.getImage());
    }
}
