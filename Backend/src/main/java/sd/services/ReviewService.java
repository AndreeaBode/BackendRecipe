package sd.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sd.entities.AddedRecipe;
import sd.entities.Comment;
import sd.entities.ExtractedRecipe;
import sd.entities.Review;
import sd.repositories.AddedRecipeRepository;
import sd.repositories.ExtractedRecipeRepository;
import sd.repositories.ReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    private final ExtractedRecipeRepository extractedRecipeRepository;

    @Autowired
    private final AddedRecipeRepository addedRecipeRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ExtractedRecipeRepository extractedRecipeRepository, AddedRecipeRepository addedRecipeRepository) {
        this.reviewRepository = reviewRepository;
        this.extractedRecipeRepository = extractedRecipeRepository;
        this.addedRecipeRepository = addedRecipeRepository;
    }

    public ResponseEntity<Review> addReviewToRecipe(int recipeId, int userId, String username, String additionalPath , Review review) {
        System.out.println("rrrecipeId: " + recipeId);
        System.out.println("userId: " + userId);
        System.out.println("username: " + username);
        System.out.println("additionalPath: " + additionalPath);
        System.out.println("review: " + review);
        if(additionalPath.equals("dishgen")) {
            ExtractedRecipe extractedRecipe = extractedRecipeRepository.findById(recipeId).orElse(null);
            review.setAddedRecipe(null);
            review.setExtractedRecipe(extractedRecipe);
        } else if (additionalPath.equals("addedRecipe")) {
            AddedRecipe addedRecipe = addedRecipeRepository.findById(recipeId).orElse(null);
            review.setAddedRecipe(addedRecipe);
            review.setExtractedRecipe(null);
        } else {
            review.setAddedRecipe(null);
            review.setExtractedRecipe(null);
        }

       // review.setRecipeId(recipeId);
        review.setUserId(userId);
        review.setUsername(username);

        reviewRepository.save(review);

        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    public List<Review> getReviewByRecipeId(String username, int recipeId, String additionalPath) {
        List<Review> reviews;
        if (additionalPath.equals("dishgen")) {
            Optional<ExtractedRecipe> extractedRecipeOptional = extractedRecipeRepository.findById(recipeId);
            if (extractedRecipeOptional.isPresent()) {
                ExtractedRecipe extractedRecipe = extractedRecipeOptional.get();
                Hibernate.initialize(extractedRecipe.getReviews());
                reviews = extractedRecipe.getReviews();
            } else {
                return null;
            }
        } else if (additionalPath.equals("addedRecipe")) {
            Optional<AddedRecipe> addedRecipeOptional = addedRecipeRepository.findById(recipeId);
            if (addedRecipeOptional.isPresent()) {
                AddedRecipe addedRecipe = addedRecipeOptional.get();
                reviews = addedRecipe.getReviews();
            } else {
                return null;
            }
        } else {
            return null;
        }
        return reviews;
    }

    public Optional<Review> getCheckReview(int userId, int recipeId, String additionalPath) {
        Optional<Review> optionalReview = Optional.empty();
        if (additionalPath.equals("dishgen")) {
            Optional<ExtractedRecipe> extractedRecipeOptional = extractedRecipeRepository.findById(recipeId);
            if (extractedRecipeOptional.isPresent()) {
                ExtractedRecipe extractedRecipe = extractedRecipeOptional.get();
                optionalReview = reviewRepository.findByExtractedRecipe_IdAndUserId(extractedRecipe.getId(), userId);
            }
        } else if (additionalPath.equals("addedRecipe")) {
            Optional<AddedRecipe> addedRecipeOptional = addedRecipeRepository.findById(recipeId);
            if (addedRecipeOptional.isPresent()) {
                AddedRecipe addedRecipe = addedRecipeOptional.get();
                optionalReview = reviewRepository.findByAddedRecipe_IdAndUserId(addedRecipe.getId(), userId);
            }
        } else {
            return Optional.empty();
        }

        return optionalReview;
    }


    public ResponseEntity<Review> updateReviewOfRecipe(int recipeId, int userId, String username, String additionalPath, Review updatedReview) {
        Optional<Review> optionalReview;
        if (additionalPath.equals("dishgen")) {
            Optional<ExtractedRecipe> extractedRecipeOptional = extractedRecipeRepository.findById(recipeId);
            if (extractedRecipeOptional.isPresent()) {
                optionalReview = reviewRepository.findByExtractedRecipe_IdAndUserId(recipeId, userId);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else if (additionalPath.equals("addedRecipe")) {
            optionalReview = reviewRepository.findByAddedRecipe_IdAndUserId(recipeId, userId);
        } else {
            return ResponseEntity.badRequest().build();
        }

        if (optionalReview.isPresent()) {
            Review existingReview = optionalReview.get();
            existingReview.setRating(updatedReview.getRating());
            Review savedReview = reviewRepository.save(existingReview);
            return ResponseEntity.ok(savedReview);
        } else {
            return ResponseEntity.notFound().build();
        }
    }





}

