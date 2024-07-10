package sd.entities;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "added_recipes")
public class AddedRecipe {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @NotBlank(message = "Title is required")
        @Column(nullable = false)
        private String title;

        @NotBlank(message = "Image URL is required")
        @Column(nullable = false)
        private String image;

        @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<AddedIngredient> ingredients = new ArrayList<>();

        @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<AddedStep> steps = new ArrayList<>();

        @OneToMany(mappedBy = "addedRecipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        private List<Comment> comments = new ArrayList<>();

        @OneToMany(mappedBy = "addedRecipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        private List<Review> reviews = new ArrayList<>();

        public void setIngredients(List<AddedIngredient> ingredients) {
                this.ingredients = (List<AddedIngredient>) ingredients;
        }

        @Override
        public String toString() {
                return "AddedRecipe{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", image='" + image + '\'' +
                        '}';
        }
}