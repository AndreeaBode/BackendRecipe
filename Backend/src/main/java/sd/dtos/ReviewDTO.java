package sd.dtos;

import lombok.Data;
import sd.entities.Review;

@Data
public class ReviewDTO {
    private int id;
    private int userId;
    private String username;
    private String rating;

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.userId = review.getUserId();
        this.username = review.getUsername();
        this.rating = review.getRating();
    }
}
