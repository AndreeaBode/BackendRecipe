package sd.entities;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "love_react")
public class LoveReact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "User ID is required")
    @Column(nullable = false)
    private int userId;

    @NotNull(message = "Recipe ID is required")
    @Column(nullable = false)
    private int recipeId;

    @NotBlank(message = "Recipe name is required")
    @Column(nullable = false)
    private String name;
}
