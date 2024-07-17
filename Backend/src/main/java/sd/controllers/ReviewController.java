package sd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sd.entities.Review;
import sd.services.ReviewService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
@Transactional

public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{recipeId}/{userId}/{username}/{additionalPath}/addReview")
    public ResponseEntity<Review> addReviewToRecipe(@PathVariable("recipeId") int recipeId,
                                                    @PathVariable("userId") int userId,
                                                    @PathVariable("username") String username,
                                                    @PathVariable("additionalPath") String additionalPath,
                                                    @RequestBody Review review) {

        try {
            return reviewService.addReviewToRecipe(recipeId, userId, username, additionalPath, review);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getReview/{username}/{recipeId}/{additionalPath}")
    public ResponseEntity<List<Review>> getReviewByRecipeId(@PathVariable("username") String username, @PathVariable int recipeId, @PathVariable("additionalPath") String additionalPath) {
        List<Review> reviews = reviewService.getReviewByRecipeId(username, recipeId, additionalPath);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("checkReview/{userId}/{recipeId}/{additionalPath}")
    public ResponseEntity<Optional<Review>> getCheckReview(@PathVariable("userId") int userId, @PathVariable int recipeId, @PathVariable("additionalPath") String additionalPath) {
        Optional<Review> reviews = reviewService.getCheckReview(userId, recipeId, additionalPath);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping("/{recipeId}/{userId}/{username}/{additionalPath}/updateReview")
    public ResponseEntity<Review> updateReviewOfRecipe(@PathVariable("recipeId") int recipeId,
                                                       @PathVariable("userId") int userId,
                                                       @PathVariable("username") String username,
                                                       @PathVariable("additionalPath") String additionalPath,
                                                       @RequestBody Review updatedReview) {
        return reviewService.updateReviewOfRecipe(recipeId, userId, username, additionalPath, updatedReview);
    }


}

