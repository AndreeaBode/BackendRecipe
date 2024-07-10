package sd.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sd.entities.AddedRecipe;
import sd.entities.ExtractedRecipe;
import sd.entities.Review;
import sd.services.ReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReviewToRecipe_ExtractedRecipe() {
        int recipeId = 1;
        int userId = 1;
        String username = "testUser";
        String additionalPath = "dishgen";
        Review review = new Review();
        review.setRating("5");

        ResponseEntity<Review> expectedResponse = new ResponseEntity<>(HttpStatus.OK);

        when(reviewService.addReviewToRecipe(eq(recipeId), eq(userId), eq(username), eq(additionalPath), any(Review.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Review> actualResponse = reviewController.addReviewToRecipe(recipeId, userId, username, additionalPath, review);

        assertEquals(expectedResponse, actualResponse);
        verify(reviewService, times(1)).addReviewToRecipe(eq(recipeId), eq(userId), eq(username), eq(additionalPath), eq(review));
    }

    @Test
    void testGetReviewByRecipeId_ExtractedRecipe() {
        int recipeId = 1;
        String username = "testUser";
        String additionalPath = "dishgen";
        List<Review> reviews = new ArrayList<>();
        Review review = new Review();
        review.setId(1);
        review.setRating("5");
        reviews.add(review);

        when(reviewService.getReviewByRecipeId(eq(username), eq(recipeId), eq(additionalPath))).thenReturn(reviews);

        ResponseEntity<List<Review>> expectedResponse = new ResponseEntity<>(reviews, HttpStatus.OK);
        ResponseEntity<List<Review>> actualResponse = reviewController.getReviewByRecipeId(username, recipeId, additionalPath);

        assertEquals(expectedResponse, actualResponse);
        verify(reviewService, times(1)).getReviewByRecipeId(eq(username), eq(recipeId), eq(additionalPath));
    }

    @Test
    void testGetCheckReview_ExtractedRecipe() {
        int userId = 1;
        int recipeId = 1;
        String additionalPath = "dishgen";
        Review review = new Review();
        review.setId(1);
        review.setRating("5");

        Optional<Review> optionalReview = Optional.of(review);

        when(reviewService.getCheckReview(eq(userId), eq(recipeId), eq(additionalPath))).thenReturn(optionalReview);

        ResponseEntity<Optional<Review>> expectedResponse = new ResponseEntity<>(optionalReview, HttpStatus.OK);
        ResponseEntity<Optional<Review>> actualResponse = reviewController.getCheckReview(userId, recipeId, additionalPath);

        assertEquals(expectedResponse, actualResponse);
        verify(reviewService, times(1)).getCheckReview(eq(userId), eq(recipeId), eq(additionalPath));
    }

    @Test
    void testUpdateReviewOfRecipe_ExtractedRecipe() {
        int recipeId = 1;
        int userId = 1;
        String username = "testUser";
        String additionalPath = "dishgen";
        Review updatedReview = new Review();
        updatedReview.setId(1);
        updatedReview.setRating("5");

        ResponseEntity<Review> expectedResponse = new ResponseEntity<>(HttpStatus.OK);

        when(reviewService.updateReviewOfRecipe(eq(recipeId), eq(userId), eq(username), eq(additionalPath), any(Review.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<Review> actualResponse = reviewController.updateReviewOfRecipe(recipeId, userId, username, additionalPath, updatedReview);

        assertEquals(expectedResponse, actualResponse);
        verify(reviewService, times(1)).updateReviewOfRecipe(eq(recipeId), eq(userId), eq(username), eq(additionalPath), eq(updatedReview));
    }
}
