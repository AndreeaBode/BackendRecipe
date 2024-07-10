package sd.entities;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Image URL is required")
    @Column(nullable = false)
    private String image;

}
