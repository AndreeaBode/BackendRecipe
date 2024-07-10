package sd.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sd.entities.Comment;
import sd.entities.ExtractedRecipe;
import sd.repositories.AddedRecipeRepository;
import sd.repositories.CommentRepository;
import sd.repositories.ExtractedRecipeRepository;
import sd.services.CommentService;
import sd.services.ExtractedRecipeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ExtractedRecipeRepository extractedRecipeRepository;

    @Mock
    private AddedRecipeRepository addedRecipeRepository;

    @Mock
    private ExtractedRecipeService extractedRecipeService;

    @InjectMocks
    private CommentService commentService;

    @Test
    void testAddComment() {

        int recipeId = 1;
        int userId = 101;
        String username = "testUser";
        String additionalPath = "dishgen";
        Comment comment = new Comment();
        ExtractedRecipe extractedRecipe = new ExtractedRecipe();
        when(extractedRecipeRepository.findById(recipeId)).thenReturn(Optional.of(extractedRecipe));

        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> {
            Comment savedComment = invocation.getArgument(0);
            savedComment.setId(1);
            return savedComment;
        });


        ResponseEntity<Comment> response = commentService.addComment(recipeId, userId, username, additionalPath, comment);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, response.getBody().getId());

        verify(extractedRecipeRepository, times(1)).findById(recipeId);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void testGetCommentsByRecipeId() {

        String username = "testUser";
        int recipeId = 1;
        String additionalPath = "dishgen";
        List<Comment> expectedComments = new ArrayList<>();

        ExtractedRecipe extractedRecipe = new ExtractedRecipe();
        extractedRecipe.setId(recipeId);
        extractedRecipe.setComments(expectedComments);
        when(extractedRecipeRepository.findById(recipeId)).thenReturn(Optional.of(extractedRecipe));

        List<Comment> actualComments = commentService.getCommentsByRecipeId(username, recipeId, additionalPath);

        assertEquals(expectedComments.size(), actualComments.size());

        verify(extractedRecipeRepository, times(1)).findById(recipeId);
    }
}
