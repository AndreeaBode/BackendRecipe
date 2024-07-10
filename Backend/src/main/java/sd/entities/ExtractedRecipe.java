package sd.entities;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "extracted_recipes")
public class ExtractedRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false, unique = true)
    private String title;

    @NotBlank(message = "Image URL is required")
    @Column(nullable = false)
    private String image;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Step> steps = new ArrayList<>();

    @OneToMany(mappedBy = "extractedRecipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "extractedRecipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Review> reviews = new ArrayList<>();


    @Column(nullable = false)
    private double percentProtein;

    @Column(nullable = false)
    private double percentFat;

    @Column(nullable = false)
    private double percentCarbs;

    @Column(nullable = false)
    private double weightPerServing;

    @Column(nullable = false)
    private String unitPerServing;

    @ElementCollection
    @CollectionTable(name = "dish_types", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "dish_type")
    private List<String> dishTypes = new ArrayList<>();


    @ElementCollection
    @CollectionTable(name = "diets", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "diets")
    private List<String> diets = new ArrayList<>();

    public ExtractedRecipe() {

    }

    public ExtractedRecipe(int id, String title, String image, List<String> dishTypes, List<String> diets) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.dishTypes = dishTypes;
        this.diets = diets;
    }
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
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

    public List<String> getDishTypes() {
        return dishTypes;
    }

    public void setDishTypes(List<String> dishTypes) {
        this.dishTypes = dishTypes;
    }

    @Override
    public String toString() {
        return "ExtractedRecipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
