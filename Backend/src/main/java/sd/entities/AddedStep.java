package sd.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "added_steps")
public class AddedStep {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(nullable = false)
        @NotBlank(message = "Instruction is required")
        private String instruction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    @JsonIgnore
    private AddedRecipe recipe;
        public AddedStep(String instruction) {
            this.instruction = instruction;
        }

        public AddedStep() {
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

    public AddedRecipe getRecipe() {
        return recipe;
    }

    public void setRecipe(AddedRecipe recipe) {
        this.recipe = recipe;
    }


    }


