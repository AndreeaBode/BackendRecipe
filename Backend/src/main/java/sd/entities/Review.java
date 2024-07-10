package sd.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "User ID is required")
    @Column(nullable = false)
    private int userId;

    @NotBlank(message = "Username is required")
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "Rating is required")
    @Pattern(regexp = "[1-5]", message = "Rating must be between 1 and 5")
    @Column(nullable = false)
    private String rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "extracted_id")
    @JsonIgnore
    private ExtractedRecipe extractedRecipe;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_id")
    @JsonIgnore
    private AddedRecipe addedRecipe;


}