package sd.dtos;

import lombok.Data;
import sd.entities.Comment;
import sd.entities.ExtractedRecipe;
import sd.entities.Review;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExtDTO {
    private int id;
    private String title;
    private String image;
    private List<CommentDTO> comments;
    private List<ReviewDTO> reviews;
    private double percentProtein;
    private double percentFat;
    private double percentCarbs;
    private double weightPerServing;
    private String unitPerServing;
    private List<String> dishTypes;
    private List<String> diets;

    public ExtDTO(ExtractedRecipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.image = recipe.getImage();
        this.comments = recipe.getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
        this.reviews = recipe.getReviews().stream().map(ReviewDTO::new).collect(Collectors.toList());
        this.percentProtein = recipe.getPercentProtein();
        this.percentFat = recipe.getPercentFat();
        this.percentCarbs = recipe.getPercentCarbs();
        this.weightPerServing = recipe.getWeightPerServing();
        this.unitPerServing = recipe.getUnitPerServing();
        this.dishTypes = recipe.getDishTypes();
        this.diets = recipe.getDiets();
    }
}
