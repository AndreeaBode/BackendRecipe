package sd.dtos.builders;

import sd.dtos.FavoriteDTO;
import sd.entities.AddedRecipe;
import sd.entities.ExtractedRecipe;

public class Favorite {
    public static FavoriteDTO buildDTOAdded(AddedRecipe addedRecipe, String name) {
        FavoriteDTO addedRecipeDTO = new FavoriteDTO();
        addedRecipeDTO.setRecipeId(addedRecipe.getId());
        addedRecipeDTO.setTitle(addedRecipe.getTitle());
        addedRecipeDTO.setImage(addedRecipe.getImage());
        addedRecipeDTO.setName(name);
        System.out.println("Add " + addedRecipeDTO);
        return addedRecipeDTO;
    }

    public static FavoriteDTO buildDTOExtracted(ExtractedRecipe extractedRecipe, String name) {
        FavoriteDTO extractedRecipeDTO = new FavoriteDTO();
        extractedRecipeDTO.setRecipeId(extractedRecipe.getId());
        extractedRecipeDTO.setTitle(extractedRecipe.getTitle());
        extractedRecipeDTO.setImage(extractedRecipe.getImage());
        extractedRecipeDTO.setName(name);
        System.out.println("Ex " + extractedRecipeDTO);
        return extractedRecipeDTO;
    }
}
