package sd.dtos;

import sd.entities.*;

import java.util.ArrayList;
import java.util.List;

public class AddedRecipeDTO {
    private int id;
    private String title;
    private String image;



    private List<AddedIngredient> ingredients= new ArrayList<>();
    private List<AddedStep> instructions;

    public AddedRecipeDTO(AddedRecipe addedRecipe) {
        this.id = addedRecipe.getId();
        this.title = addedRecipe.getTitle();
        this.image = addedRecipe.getImage();
        this.ingredients = addedRecipe.getIngredients();
        this.instructions = addedRecipe.getSteps();
    }

    public AddedRecipeDTO() {

    }

    public AddedRecipeDTO(int id, String title, String image, List<AddedIngredient> ingredients, List<AddedStep> instructions) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }


    public AddedRecipeDTO(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<AddedIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<AddedIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<AddedStep> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<AddedStep> instructions) {
        this.instructions = instructions;
    }


}