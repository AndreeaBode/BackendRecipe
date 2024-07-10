package sd.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "recipe_under_review_ ingredients")
public class RecipeUnderReviewIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NotBlank(message = "Ingredient is required")
    private String ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    @JsonIgnore
    private RecipeUnderReview recipe;

    public RecipeUnderReviewIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public RecipeUnderReviewIngredient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public RecipeUnderReview getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeUnderReview recipe) {
        this.recipe = recipe;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


}

