package sd.dtos;

import sd.entities.ExtractedRecipe;
import sd.entities.Ingredient;
import sd.entities.Step;

import java.util.List;

public class ExtractedDTO {
    private int id;
    private String title;
    private String image;
    private List<Ingredient> ingredients;
    private List<Step> instructions;
    private List<String> dishTypes;
    private List<String> diets;
    private double percentProtein;
    private double percentFat;
    private double percentCarbs;
    private double weightPerServing;
    private String unitPerServing;

    public ExtractedDTO(ExtractedRecipe extractedRecipe) {
        this.id = extractedRecipe.getId();
        this.title = extractedRecipe.getTitle();
        this.image = extractedRecipe.getImage();
        this.ingredients = extractedRecipe.getIngredients();
        this.instructions = extractedRecipe.getSteps();
        this.dishTypes = extractedRecipe.getDishTypes();
        this.diets = extractedRecipe.getDiets();
        this.percentProtein = extractedRecipe.getPercentProtein();
        this.percentFat = extractedRecipe.getPercentFat();
        this.percentCarbs = extractedRecipe.getPercentCarbs();
        this.weightPerServing = extractedRecipe.getWeightPerServing();
        this.unitPerServing = extractedRecipe.getUnitPerServing();
    }


    public ExtractedDTO() {
    }


    public ExtractedDTO(int id, String title, String image, List<Ingredient> ingredients, List<Step> instructions) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.ingredients = ingredients;
        this.instructions = instructions;
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Step> instructions) {
        this.instructions = instructions;
    }

    public List<String> getDishTypes() {
        return dishTypes;
    }

    public void setDishTypes(List<String> dishTypes) {
        this.dishTypes = dishTypes;
    }

    public List<String> getDiets() {
        return diets;
    }

    public void setDiets(List<String> diets) {
        this.diets = diets;
    }

    public double getPercentProtein() {
        return percentProtein;
    }

    public void setPercentProtein(double percentProtein) {
        this.percentProtein = percentProtein;
    }

    public double getPercentFat() {
        return percentFat;
    }

    public void setPercentFat(double percentFat) {
        this.percentFat = percentFat;
    }

    public double getPercentCarbs() {
        return percentCarbs;
    }

    public void setPercentCarbs(double percentCarbs) {
        this.percentCarbs = percentCarbs;
    }

    public double getWeightPerServing() {
        return weightPerServing;
    }

    public void setWeightPerServing(double weightPerServing) {
        this.weightPerServing = weightPerServing;
    }

    public String getUnitPerServing() {
        return unitPerServing;
    }

    public void setUnitPerServing(String unitPerServing) {
        this.unitPerServing = unitPerServing;
    }

    // Metodă toString pentru a reprezenta obiectul ca un string
    @Override
    public String toString() {
        return "ExtractedDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", ingredients=" + ingredients +
                ", instructions=" + instructions +
                ", dishTypes=" + dishTypes +
                ", diets=" + diets +
                ", percentProtein=" + percentProtein +
                ", percentFat=" + percentFat +
                ", percentCarbs=" + percentCarbs +
                ", weightPerServing=" + weightPerServing +
                ", unitPerServing='" + unitPerServing + '\'' +
                '}';
    }
}
