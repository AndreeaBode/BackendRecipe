package sd.dtos.builders;

import sd.dtos.ExtractedDTO;
import sd.dtos.ExtractedRecipeDTO;
import sd.dtos.UserDTO;
import sd.entities.ExtractedRecipe;
import sd.entities.User;

public class ExtractedRecipeBuilder {

    public static ExtractedRecipeDTO buildDTO(ExtractedRecipe extractedRecipe) {
        return new ExtractedRecipeDTO(extractedRecipe.getId(), extractedRecipe.getTitle(), extractedRecipe.getImage());
    }

    public static ExtractedRecipe toExtractedRecipe(ExtractedDTO dto) {
        ExtractedRecipe recipe = new ExtractedRecipe();
        recipe.setId(dto.getId());
        recipe.setTitle(dto.getTitle());
        recipe.setImage(dto.getImage());
        return recipe;
    }

    public static ExtractedDTO toExtractedDTO(ExtractedRecipe recipe) {
        ExtractedDTO dto = new ExtractedDTO();
        dto.setId(recipe.getId());
        dto.setTitle(recipe.getTitle());
        dto.setImage(recipe.getImage());
        return dto;
    }
}
