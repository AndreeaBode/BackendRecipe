package sd.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import sd.entities.AddedRecipe;
import sd.entities.ExtractedRecipe;
import sd.entities.Review;
import sd.repositories.AddedRecipeRepository;
import sd.repositories.ExtractedRecipeRepository;
import sd.repositories.ReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ExtractedRecipeRepository extractedRecipeRepository;

    @Mock
    private AddedRecipeRepository addedRecipeRepository;

    @InjectMocks
    private ReviewService reviewService;

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
        review.setId(1);
        review.setRating("5");

        ExtractedRecipe extractedRecipe = new ExtractedRecipe();
        extractedRecipe.setId(recipeId);

        when(extractedRecipeRepository.findById(eq(recipeId))).thenReturn(Optional.of(extractedRecipe));

        reviewService.addReviewToRecipe(recipeId, userId, username, additionalPath, review);

        assertEquals(review.getUserId(), userId);
        assertEquals(review.getUsername(), username);
        assertEquals(review.getExtractedRecipe(), extractedRecipe);
        verify(reviewRepository, times(1)).save(eq(review));
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

        ExtractedRecipe extractedRecipe = new ExtractedRecipe();
        extractedRecipe.setId(recipeId);
        extractedRecipe.setReviews(reviews);

        when(extractedRecipeRepository.findById(eq(recipeId))).thenReturn(Optional.of(extractedRecipe));

        List<Review> result = reviewService.getReviewByRecipeId(username, recipeId, additionalPath);

        assertEquals(reviews.size(), result.size());
        assertEquals(reviews.get(0), result.get(0));
    }

    @Test
    void testGetCheckReview_ExtractedRecipe() {
        int userId = 1;
        int recipeId = 1;
        String additionalPath = "dishgen";
        Review review = new Review();
        review.setId(1);
        review.setRating("5");

        ExtractedRecipe extractedRecipe = new ExtractedRecipe();
        extractedRecipe.setId(recipeId);

        when(extractedRecipeRepository.findById(eq(recipeId))).thenReturn(Optional.of(extractedRecipe));
        when(reviewRepository.findByExtractedRecipe_IdAndUserId(eq(recipeId), eq(userId))).thenReturn(Optional.of(review));

        Optional<Review> result = reviewService.getCheckReview(userId, recipeId, additionalPath);

        assertEquals(review, result.orElse(null));
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

        Review existingReview = new Review();
        existingReview.setId(1);
        existingReview.setRating("3");

        ExtractedRecipe extractedRecipe = new ExtractedRecipe();
        extractedRecipe.setId(recipeId);
        existingReview.setExtractedRecipe(extractedRecipe);

        when(extractedRecipeRepository.findById(eq(recipeId))).thenReturn(Optional.of(extractedRecipe));
        when(reviewRepository.findByExtractedRecipe_IdAndUserId(eq(recipeId), eq(userId))).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Review> result = reviewService.updateReviewOfRecipe(recipeId, userId, username, additionalPath, updatedReview);

        assertEquals(updatedReview.getRating(), result.getBody().getRating());
    }
}
