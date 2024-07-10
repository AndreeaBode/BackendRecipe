package sd.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "recipe_under_review_steps")
public class RecipeUnderReviewStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NotBlank(message = "Step is required")
    private String instruction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    @JsonIgnore
    private RecipeUnderReview recipe;

    public RecipeUnderReviewStep(String instruction) {
        this.instruction = instruction;
    }

    public RecipeUnderReviewStep() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }


    public RecipeUnderReview getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeUnderReview recipe) {
        this.recipe = recipe;
    }
}


