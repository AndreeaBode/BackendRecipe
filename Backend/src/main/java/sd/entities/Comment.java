package sd.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "User ID is required")
    @Column(nullable = false)
    private int userId;

    @NotBlank(message = "Username is required")
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "Comment content is required")
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "extracted_id")
    @JsonIgnore
    private ExtractedRecipe extractedRecipe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "added_id")
    @JsonIgnore
    private AddedRecipe addedRecipe;


}